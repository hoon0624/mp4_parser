/*
 * Movie Fragment Header Box contains a sequence number, as a safety check.
 * Sequence number usually starts at 1 and must increase for each movie fragment.
 */
public class MFHD extends FullBox {

	private int sequence_number;	// 32 bits
	
	MFHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.sequence_number = this.readStreamAsInt(stream, 4);
	}
	
	@Override
	public String toString() {
		String str = "Sequence  number: " + this.sequence_number + "\n";
		return super.toString() + str;
	}
}
