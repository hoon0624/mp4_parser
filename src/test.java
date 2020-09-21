import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class test {
	
	public static void main(String[] args) throws Exception {
		String str = "d1ea271e";
		
//		long n = Long.parseLong(str, 16);
		
		String sourceFilePath = "../mp4 Parser/file_example_MP4_480_1_5MG.mp4";
		
		FileInputStream inputStream = new FileInputStream(sourceFilePath);
		inputStream.read(new byte[4]);
//		System.out.println(Integer.toHexString(inputStream.read() & 0xFF));
//		System.out.println(Integer.toHexString(inputStream.read()));
		
//		byte[] b = new byte[1];
//		inputStream.read(b);
//		System.out.println(b[0] & 0xFF);
//		System.out.println(Integer.toBinaryString(b[0]& 0xFF));
		
//		MP4Stream mStream = new MP4Stream(sourceFilePath);
//		MP4Stream cpyStream = mStream.copyInstanceOfStream();
//		mStream.read(new byte[682]);
//		System.out.println(cpyStream.read());
//		System.out.println(mStream.getChannel().position());
//		System.out.println(cpyStream.getChannel().position());
//		
//		mStream.read(new byte[8]);
//		STSS stss = new STSS(mStream, 60, "stss");
//		System.out.println(stss);
//		
//		ArrayList<Integer> list = stss.getKeyFrames();
//		System.out.println(list);
//		
//		cpyStream.read(new byte[491]);
//		STBL stbl = new STBL(cpyStream, 5023, "stbl");
//		System.out.println(stbl);
//		
//		System.out.println(stbl.getStss().getKeyFrames());
		
		ArrayList<String[]> strarry = new ArrayList<>();
		int flag = 1;
		switch(flag) {
		case 1: 
			System.out.println("TEST WORKS");
		case 8:
			System.out.println("WTF");
		}
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
