import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Box {
	protected int size;
	protected String type;
	protected int startPos;
	protected int endPos;
	protected String hdlrType = "";
	
	Box(MP4Stream stream, int size, String type) throws Exception {
		this.size = size;
		this.type = type;
		this.startPos = stream.getPos() - 8;
		this.endPos = this.startPos + size;
	}
	
	Box(MP4Stream stream, int size, String type, String hdlrType) throws IOException {
		this.size = size;
		this.type = type;
		this.startPos = stream.getPos() - 8;
		this.endPos = this.startPos + size;
		this.hdlrType = hdlrType;
	}
	
	protected String readStreamAsString(InputStream stream, int numByte) throws Exception {
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
	}
	
	protected int readStreamAsInt(InputStream stream, int numByte) throws Exception {
		byte[] b = new byte[numByte];
		int data = stream.read(b, 0, numByte);
		int res = 0;
		if(data != -1) {
			for(int i=0; i<numByte; i++) {
				int num = b[i] &0xFF;
				res += (num << 8*(numByte-1-i));
			}
		}
		return res;
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
		String hex1 = "";
		String hex2 = "";
		stream.read(data);
		int half = numByte/2;
		for(int i=0; i<numByte; i++) {
			String str = Integer.toHexString(data[i] & 0xFF);
			if(str.length() == 1) {
				str = "0" + str;
			}
			if(i < half) {
				hex1 += str;
			} else {
				hex2 += str;
			}
		}
		String s = Integer.parseInt(hex1, 16) + "." + Integer.parseInt(hex2, 16);
		return Double.parseDouble(s);
	}

	protected void skipToNextBox(MP4Stream stream) throws IOException {
		int byteToSkip = this.endPos - (int) stream.getPos();
		byte[] data = new byte[byteToSkip];
		stream.read(data);
	}
	
	protected String convertToDate(long seconds) {
		// Seconds passed since 1904.01.01 12:00AM to 1970.01.01 12:00AM
		long diff = 2082844800;
		
		Date date = new Date((seconds-diff) * 1000L);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		return format.format(date);
	}
	
	// read stream as binary
	protected int readStreamAsBits(InputStream stream, int bit) throws Exception{
		byte data[] = new byte[1];
		stream.read(data);
		int bin = data[0] & 0xFF;
		
		return bin & bit;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Box Type: " + this.type + "\n");
		str.append("Box size: " + this.size + "\n");
		str.append("Start position: " + this.startPos + "\n");
		str.append("End position: " + this.endPos + "\n");
		
		return str.toString();
	}
}
