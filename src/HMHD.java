/*
 * Hint Media Header contains general info for hint tracks
 */
public class HMHD extends FullBox {

	private int maxPDUsize;		// 16 bits
	private int avgPDUsize;		// 16 bits
	private int maxbitrate;		// 32 bits
	private int avgbitrate;		// 32 bits
	private int reserved = 0;	// 32 bits
	
	HMHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.maxPDUsize = this.readStreamAsInt(stream, 2);
		this.avgPDUsize = this.readStreamAsInt(stream, 2);
		this.maxbitrate = this.readStreamAsInt(stream, 4);
		this.avgbitrate = this.readStreamAsInt(stream, 4);
		this.reserved = this.readStreamAsInt(stream, 4);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Max PDU size: " + this.maxPDUsize + "\n");
		str.append("Avg PDU size: " + this.avgPDUsize + "\n");
		str.append("Max bitrate: " + this.maxbitrate + "\n");
		str.append("Avg bitrate: " + this.avgbitrate + "\n");
		str.append("Reserved: " + this.reserved + "\n");
		
		return str.toString();
	}
}
