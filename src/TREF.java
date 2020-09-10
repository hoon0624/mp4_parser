import java.io.InputStream;

public class TREF extends Box{
	TREF(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		this.skipToNextBox(stream, this.size - position);
	}
}
