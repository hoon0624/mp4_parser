
public class FullBox extends Box{
	
	protected int version;
	protected int flags;
	
	FullBox(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.version = this.readStreamAsInt(stream, 1);
		this.flags = this.readStreamAsInt(stream, 3);
	}
	
	FullBox(MP4Stream stream, int size, String type, String hdlrType) throws Exception {
		super(stream, size, type, hdlrType);
		this.version = this.readStreamAsInt(stream, 1);
		this.flags = this.readStreamAsInt(stream, 3);
	}
	
	protected int getNumOfBytes() {
		if(this.version == 0) {
			return 4;
		} else {
			return 8;
		}
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
