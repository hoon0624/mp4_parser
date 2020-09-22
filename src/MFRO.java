/*
 * Movie Fragment Random Access Offset Box provides a copy of length field from the
 * enclosing Movie Fragment random Access box.
 */
public class MFRO extends FullBox {

	private int size;	// 32 bits
	
	MFRO(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.size = this.readStreamAsInt(stream, 4);
	}
	
	@Override
	public String toString() {
		return super.toString() + "Size: " + this.size + "\n";
	}
}
