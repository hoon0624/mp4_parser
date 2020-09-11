import java.io.InputStream;

public class FullBox extends Box{
	
	protected int version;
	protected int flags;
	
	FullBox(InputStream stream, int size, String type, int position) {
		super(stream, size, type, position);
		this.version = this.readStreamAsInt(stream, 1);
		this.flags = this.readStreamAsInt(stream, 3);
	}
	
	FullBox(InputStream stream, int size, String type, int position, String hdlrType) {
		super(stream, size, type, position, hdlrType);
		this.version = this.readStreamAsInt(stream, 1);
		this.flags = this.readStreamAsInt(stream, 3);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Version: " + this.version + "\n");
		str.append("Flags: " + this.flags + "\n");
		
		return str.toString();
	}

}
