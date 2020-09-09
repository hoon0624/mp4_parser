import java.io.IOException;
import java.io.InputStream;

public class FullBox extends Box{
	
	private int version;
	private int flags;
	
	FullBox(InputStream stream, int position) throws IOException {
		super(stream, position);
		this.version = this.readStreamAsInt(stream, 1);
		this.flags = this.readStreamAsInt(stream, 3);
	}

}
