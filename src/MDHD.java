/*
 * Media Header Box declares overall info that is media-independent, and relevant to characteristics of media in a track
 */
public class MDHD extends FullBox {

	private long creationTime;		// (ver_1) 64 bits or (ver_0) 32 bits
	private long modificationTime;	// (ver_1) 64 bits or (ver_0) 32 bits
	private int timeScale;			// 32 bits
	private int duration;			// (ver_1) 64 bits or (ver_0) 32 bits
	private int language;			// 15 bits
	private int quality;			// (pre_defined) 16 bits

	MDHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		int numByte = this.getNumOfBytes();
		this.creationTime = this.readTimeFields(stream, numByte);
		this.modificationTime = this.readTimeFields(stream, numByte);
		this.timeScale = this.readStreamAsInt(stream, 4);
		this.duration = this.readStreamAsInt(stream, numByte);
		this.language = this.readStreamAsInt(stream, 2);
		this.quality = this.readStreamAsInt(stream, 2);
	}
	
	@Override 
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Creation time: " + this.convertToDate(this.creationTime) + "\n");
		str.append("Modification time:" + this.convertToDate(this.modificationTime) + "\n");
		str.append("Time scale: " + this.timeScale + "\n");
		str.append("Duration: " + this.duration + "\n");
		str.append("Language: " + this.language + "\n");
		str.append("Quality: " + this.quality + "\n");
		
		return str.toString();
	}
}
