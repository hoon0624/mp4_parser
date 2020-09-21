
public class TFDT extends FullBox {

	private int base_media_decode_time;
	
	TFDT(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.base_media_decode_time = this.readStreamAsInt(stream, 8);
	}
	
	@Override
	public String toString() {
		return super.toString() + "base_media_decode_time: " + this.base_media_decode_time + "\n";
	}
}
