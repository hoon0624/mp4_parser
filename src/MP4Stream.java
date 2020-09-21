import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MP4Stream extends FileInputStream {
	
	private String sourceFilePath;
	
	public MP4Stream(String arg0) throws FileNotFoundException {
		super(arg0);
		this.sourceFilePath = arg0;
	}
	
	public MP4Stream copyInstanceOfStream() throws Exception {
		MP4Stream mStream = new MP4Stream(this.sourceFilePath);
		mStream.skip(this.getPos());
		
		return mStream;
	}
	
	public int getPos() throws IOException {
		return (int) this.getChannel().position();
	}

}
