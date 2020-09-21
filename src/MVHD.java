/*
 * Movie Header Box defines overall info which is media-independent, and relevant to the entire presentation
 */
public class MVHD extends FullBox {

	private long creationTime;		// (ver_1) 64 bits or (ver_0) 32 bits
	private long modificationTime;	// (ver_1) 64 bits or (ver_0) 32 bits
	private int timeScale;			// 32 bits
	private int duration;			// (ver_1) 64 bits or (ver_0) 32 bits
	
	MVHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		int numByte;
		if(this.version == 0) {
			numByte = 4;
		} else {
			numByte= 8;
		}
		this.creationTime = this.readTimeFields(stream, numByte);
		this.modificationTime = this.readTimeFields(stream, numByte);
		this.timeScale = this.readStreamAsInt(stream, 4);
		this.duration = this.readStreamAsInt(stream, numByte);
		this.skipToNextBox(stream);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Creation Time: " + this.convertToDate(this.creationTime) + "\n");
		str.append("Modification Time: " + this.convertToDate(this.modificationTime) + "\n");
		str.append("Time scale: " + this.timeScale + "\n");
		str.append("Duration: " + this.duration + "\n");
		
		return str.toString();
	}
}
