import java.io.IOException;
import java.io.InputStream;

public class Box {
	protected int size;
	protected String type;
	protected int startPos;
	protected int endPos;
	
	Box(InputStream stream, int position) throws IOException {
		this.size = readStreamAsInt(stream, 4);
		this.type = readStreamAsString(stream, 4);
		this.startPos = position;
		this.endPos = position + size;
	}
	
	protected String readStreamAsString(InputStream stream, int numByte) {
		try {
			byte[] b = new byte[numByte];
			char[] c = new char[numByte];
			int data = stream.read(b, 0, numByte);
			if(data != -1) {
				for(int i=0; i<numByte; i++) {
					c[i] = (char) b[i];
				}
				return new String(c);
			} else {
				return "";
			}
		} catch(Exception e) {
			System.out.println("ERRORRRR");
			e.printStackTrace();
		}
		return "";
	}
	
	protected int readStreamAsInt(InputStream stream, int numByte) {
		byte[] b = new byte[numByte];
		String hex = "";
		try {
			int data = stream.read(b, 0, numByte);
			if(data != -1) {
				for(int i=0; i<numByte; i++) {
					String str = Integer.toHexString(b[i] & 0xFF);
					if(str.length() == 1) {
						str = "0"+ str;
					}
					hex += str;
				}
				return Integer.parseInt(hex, 16);
			} else {
				return 0;
			}
		} catch(Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		return Integer.parseInt(hex, 16);
		
	}
	
	public int getEndPos() {
		return this.endPos;
	}
}
