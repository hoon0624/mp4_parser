import java.io.InputStream;

public class SMHD extends FullBox {

	private int balance;
	private int reserved;
	
	SMHD(InputStream stream, int size, String type, int position) {
		super(stream, size, type, position);
		position += 4;
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
