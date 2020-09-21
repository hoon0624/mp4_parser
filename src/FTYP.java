import java.util.ArrayList;
/*
 * File Type Box
 */
public class FTYP extends Box {

	private String majorBrand;		// 32 bits
	private int minorVersion;	// 32 bits
	private ArrayList<String> compatBrands = new ArrayList<>();		// 32 bits each until the end
	
	FTYP(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.majorBrand = this.readStreamAsString(stream, 4);
		this.minorVersion = this.readStreamAsInt(stream, 4);
		int numByteTilEnd = (size-16)/4;
		for(int i=0; i<numByteTilEnd; i++) {
			String str = this.readStreamAsString(stream, 4);
			this.compatBrands.add(str);
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
