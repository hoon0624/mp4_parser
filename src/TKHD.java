/*
 * Track Header Box specifies characteristics of a single track
 */
public class TKHD extends FullBox {
	private long creationTime;		// (ver_1) 64 bits or (ver_0) 32 bits
	private long modificationTime;	// (ver_1) 64 bits or (ver_0) 32 bits
	private int trackID;			// 32 bits
	private int duration;			// (ver_1) 64 bits or (ver_0) 32 bits
	private double width;			// 32 bits
	private double height;			// 32 bits

	TKHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		int numByte = this.getNumOfBytes();
		this.creationTime = this.readTimeFields(stream, numByte);
		this.modificationTime = this.readTimeFields(stream, numByte);
		this.trackID = this.readStreamAsInt(stream, 4);
		stream.read(new byte[4]);	// reserved 32 bits
		this.duration = this.readStreamAsInt(stream, numByte);
		stream.read(new byte[52]);	// skip reserved, layer, alternate_group, volume, matrix
		this.width = this.readFixedPointVal(stream, 4);
		this.height = this.readFixedPointVal(stream, 4);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Creation time: " + this.convertToDate(this.creationTime) + "\n");
		str.append("modification time: " + this.convertToDate(this.modificationTime) + "\n");
		str.append("Track ID: " + this.trackID + "\n");
		str.append("Duration: " + this.duration + "\n");
		str.append("Width: " + this.width + "\n");
		str.append("Height: " + this.height + "\n");
		
		return str.toString();
	}
}
