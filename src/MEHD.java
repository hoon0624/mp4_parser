/*
 * Movie Extended Header Box
 */
public class MEHD extends FullBox {

	private int fragment_duration;		// (ver_1) 64 bits or (ver_0) 32 bits
	
	MEHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.fragment_duration = this.readStreamAsInt(stream, this.getNumOfBytes());
	}
	
	@Override
	public String toString() {
		return super.toString() + "Fragment duration: " + this.fragment_duration + "\n";
	}
}
