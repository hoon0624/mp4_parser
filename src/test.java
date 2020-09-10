import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class test {
	public static void main(String[] args) throws Exception {
		String str = "d1ea271e";
		
//		long n = Long.parseLong(str, 16);
		
		String sourceFilePath = "../mp4 Parser/file_example_MP4_480_1_5MG.mp4";
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFilePath));
		inputStream.read(new byte[285]);
//		System.out.println(Integer.toHexString(inputStream.read() & 0xFF));
		System.out.println(Integer.toHexString(inputStream.read()));
	}
	
	public static long readTimeFields(InputStream stream, int numByte) {
		byte[] data = new byte[numByte];
		StringBuilder str = new StringBuilder();
		for(int i=0; i<numByte; i++) {
			String tmp = Integer.toHexString(data[i] & 0xFF);
			if(tmp.length() == 1) {
				tmp = "0"+tmp;
			}
			str.append(tmp);
		}
		return Long.parseLong(str.toString(), 16);
	}
}
