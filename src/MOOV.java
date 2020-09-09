import java.io.IOException;
import java.io.InputStream;

public class MOOV extends Box {

	private Box MVHD;
	private Box MDIA;
	private Box MDAT;
	
	MOOV(InputStream stream, int position) throws IOException {
		super(stream, position);
	}
	
}
