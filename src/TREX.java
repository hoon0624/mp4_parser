
public class TREX extends FullBox {
	
	private int track_ID;		// 32 bits
	private int default_sample_description_index;	// 32 bits
	private int default_sample_duration;			// 32 bits
	private int default_sample_size;				// 32 bits
	private int default_sample_flags;				// 32 bits
	
	TREX(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.track_ID = this.readStreamAsInt(stream, 4);
		this.default_sample_description_index = this.readStreamAsInt(stream, 4);
		this.default_sample_duration = this.readStreamAsInt(stream, 4);
		this.default_sample_size = this.readStreamAsInt(stream, 4);
		this.default_sample_flags = this.readStreamAsInt(stream, 4);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Track ID: " + this.track_ID + "\n");
		str.append("default_sample_description_index: " + this.default_sample_description_index + "\n");
		str.append("default_sample_duration: " + this.default_sample_duration + "\n");
		str.append("default_sample_flags: " + this.default_sample_flags	+ "\n");
		
		return str.toString();
	}

}
