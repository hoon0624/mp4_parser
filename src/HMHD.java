import java.io.InputStream;

public class HMHD extends FullBox {

	private int maxPDUsize;
	private int avgPDUsize;
	private int maxbitrate;
	private int avgbitrate;
	private int reserved = 0;
	
	HMHD(InputStream stream, int size, String type, int position) {
		super(stream, size, type, position);
		position += 4;
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
