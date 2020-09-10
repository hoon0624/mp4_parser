import java.io.InputStream;

public class nullBox extends Box {

	nullBox(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		this.skipToNextBox(stream, 8);
	}

}
