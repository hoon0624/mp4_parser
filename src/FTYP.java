import java.io.IOException;
import java.io.InputStream;

public class FTYP extends Box {

	private String majorBrand;
	private String minorVersion;
	private String[] compatBrands = new String[4];
	
	FTYP(InputStream stream, int position) throws IOException {
		super(stream, position);
		this.majorBrand = this.readStreamAsString(stream, 4);
		this.minorVersion = this.readStreamAsString(stream, 4);
		for(int i=0; i<4; i++) {
			this.compatBrands[i] = this.readStreamAsString(stream, 4);
		}
	}
}
