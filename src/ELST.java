import java.io.InputStream;

public class ELST extends Box {
	ELST(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		this.skipToNextBox(stream, 8);
	}
}
