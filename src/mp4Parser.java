import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class mp4Parser {
	public static void main(String[] args) {
		String sourceFilePath = "../mp4 Parser/file_example_MP4_480_1_5MG.mp4";
		
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFilePath));
//			System.out.println(inputStream.available());
//			for(int i=0; i<4; i++) {
//				int data = inputStream.read();
//			}
//			inputStream.mark(8);
//			System.out.println(inputStream.available());
//			for(int i=0; i<4; i++) {
//				int data = inputStream.read();
//			}
//			System.out.println(inputStream.available());
//			inputStream.reset();
//			String type = getType(inputStream);
//			System.out.println(type);
//			
//			System.out.println(inputStream.available());
			
			boolean isEndOfFile = false;
			int endOfFilePos = inputStream.available();
			int pos = 0;
			while(!isEndOfFile) {
				inputStream.mark(8);
				int size = getSize(inputStream);
				System.out.println("Size: " + size);
				String type = getType(inputStream);
				inputStream.reset();
				System.out.println(inputStream.available());
				Box box = constructBox(inputStream, type, pos);
				pos = box.getEndPos();
				if(pos == endOfFilePos) {
					isEndOfFile = true;
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static Box constructBox(InputStream stream, String type, int pos) throws IOException {
		switch(type) {
		case "ftyp":
			return new FTYP(stream, pos);
//		case "moov":
//			return new MOOV(stream, pos);
		default:
			return new nullBox(stream, pos);
		}
	}
	
	public static int getSize(InputStream stream) {
		
		byte[] b = new byte[4];
		String hex = "";
		try {
			int data = stream.read(b, 0, 4);
			if(data != -1) {
				for(int i=0; i<4; i++) {
					String str = Integer.toHexString(b[i] & 0xFF);
					if(str.length() == 1) {
						str = "0" + str;
					}
					hex += str;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(hex, 16);
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
}
