import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

public class Box {
	protected int size;
	protected String type;
	protected int startPos;
	protected int endPos;
	protected String hdlrType = "";
	
	Box(InputStream stream, int size, String type, int position) {
		this.size = size;
		this.type = type;
		this.startPos = position - 8;
		this.endPos = this.startPos + size;
	}
	
	Box(InputStream stream, int size, String type, int position, String hdlrType) {
		this.size = size;
		this.type = type;
		this.startPos = position - 8;
		this.endPos = this.startPos + size;
		this.hdlrType = hdlrType;
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
	
	protected long readTimeFields(InputStream stream, int numByte) throws Exception {
		byte[] data = new byte[numByte];
		StringBuilder str = new StringBuilder();
		stream.read(data);
		for(int i=0; i<numByte; i++) {
			String tmp = Integer.toHexString(data[i] & 0xFF);
			if(tmp.length() == 1) {
				tmp = "0"+tmp;
			}
			str.append(tmp);
		}
		return Long.parseLong(str.toString(), 16);
	}
	
	protected double readFixedPointVal(InputStream stream, int numByte) throws Exception {
		byte[] data = new byte[numByte];
		StringBuilder hex1 = new StringBuilder();
		StringBuilder hex2 = new StringBuilder();
		stream.read(data);
		int half = numByte/2;
		for(int i=0; i<numByte; i++) {
			String str = Integer.toHexString(data[i] & 0xFF);
			if(str.length() == 1) {
				str = "0" + str;
			}
			if(i < half) {
				hex1.append(str);
			} else {
				hex2.append(str);
			}
		}
		String s = Integer.parseInt(hex1.toString(), 16) + "." + Integer.parseInt(hex2.toString(), 16);
		return Double.parseDouble(s);
	}
	
	protected void skipToNextBox(InputStream stream, int numByte) throws Exception {
		int byteToSkip = this.size - numByte;
		byte[] data = new byte[byteToSkip];
		try {
			stream.read(data);
		} catch(Exception e) {
			throw e;
		}
	}
	
	protected String convertToDate(long seconds) {
		// Seconds passed since 1904.01.01 12:00AM to 1970.01.01 12:00AM
		long diff = 2082844800;
		
		Date date = new Date((seconds-diff) * 1000L);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		return format.format(date);
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Box Type: " + this.type + "\n");
		str.append("Box size: " + this.size + "\n");
		str.append("Start position: " + this.startPos + "\n");
		str.append("End position: " + this.endPos + "\n");
		
		return str.toString();
	}
	
	public int getEndPos() {
		return this.endPos;
	}
}
