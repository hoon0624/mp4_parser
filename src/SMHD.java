/*
 * Sound Media Header contains general presentation information for audo media
 */
public class SMHD extends FullBox {

	private int balance;	// 16 bits
	private int reserved;	// 16 bits
	
	SMHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.balance = this.readStreamAsInt(stream, 2);
		this.reserved = this.readStreamAsInt(stream, 2);
	}
	
	@Override 
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Balance: " + this.balance + "\n");
		str.append("Reserved: " + this.reserved + "\n");
		
		return str.toString();
	}
}
