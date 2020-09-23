import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class mp4Parser {
	public static void main(String[] args) {
		try {
			String sourceFilePath = args[0];
			MP4Stream inputStream = new MP4Stream(sourceFilePath);
			int endOfFilePos = inputStream.available();
			ContainerBox container = new ContainerBox();
			int size;
			String type;
			
			// read and create each box until it the end of file
			while(inputStream.getPos() < endOfFilePos) {
				size = getSize(inputStream);
				type = getType(inputStream);
				Box box = constructBox(inputStream, size, type);
				container.add(box);
			}
			
			// if the file is fmp4, do not update mdat file
			if(container.isFragmented) {
				container.printBoxes();
			} else {
				MOOV moov = container.moov;
				container.printBoxes();
				container.mdat.printMDATSamples(moov.getTraks());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "ftyp":
			return new FTYP(stream, size, type);
		case "moov":
			return new MOOV(stream, size, type);
		case "mdat":
			return new MDAT(stream, size, type);
		case "udta":
			return new UDTA(stream, size, type);
		case "moof":
			return new MOOF(stream, size , type);
		case "mfra":
			return new MFRA(stream, size, type);
		case "sidx":
			return new SIDX(stream, size, type);
		default:
			return new nullBox(stream,size, type);
		}
	}
	
	public static int getSize(MP4Stream stream) throws Exception {
		byte[] b = new byte[4];
		int data = stream.read(b, 0, 4);
		int res = 0;
		if(data != -1) {
			for(int i=0; i<4; i++) {
				int num = b[i] & 0xFF;
				res += (num << 8*(3-i));
			}
		}
		return res;
	}
	
	public static String getType(InputStream stream) throws IOException {
		byte[] b = new byte[4];
		char[] c = new char[4];
		int data = stream.read(b, 0, 4);
		if(data != -1) {
			for(int i=0; i<4; i++) {
				c[i] = (char) b[i];
			}
			return new String(c);
		} else {
			return "";
		}
	}
	
	// static nested class to store boxes
	static class ContainerBox {
		public boolean isFragmented = false;
		public FTYP ftyp;
		public MOOV moov;
		public MDAT mdat;
		public ArrayList<Box> boxes = new ArrayList<>();
		
		public void add(Box box) {
			if(box.type.equals("fytp")) {
				this.ftyp = (FTYP) box;
				boxes.add(this.ftyp);
			} else if(box.type.equals("moov")) {
				this.moov = (MOOV) box;
				boxes.add(this.moov);
			} else if(box.type.equals("mdat")) {
				this.mdat = (MDAT) box;
				boxes.add(this.mdat);
			} else if(box.type.equals("moof")) {
				this.isFragmented = true;
				boxes.add((MOOF) box);
			} else if(box.type.equals("udta")) {
				boxes.add((UDTA) box);
			} else if(box.type.equals("mfra")) {
				boxes.add((MFRA) box);
			} else if(box.type.equals("sidx")) {
				boxes.add((SIDX) box);
			} else {
				boxes.add(box);
			}
		}
		
		public void printBoxes() {
			this.boxes.forEach(System.out::println);
		}
	}
}
