import java.io.InputStream;

public class FTYP extends Box {

	private String majorBrand;
	private String minorVersion;
	private String[] compatBrands = new String[4];
	
	FTYP(InputStream stream, int size, String type, int position) {
		super(stream, size, type, position);
		this.majorBrand = this.readStreamAsString(stream, 4);
		this.minorVersion = this.readStreamAsString(stream, 4);
		for(int i=0; i<4; i++) {
			this.compatBrands[i] = this.readStreamAsString(stream, 4);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Major brand: " + this.majorBrand + "\n");
		str.append("Minor version: " + this.minorVersion + "\n");
		for(String cBrand : this.compatBrands) {
			str.append("Compatible brands: " + cBrand + "\n");
		}
		
		return str.toString();
	}
}
