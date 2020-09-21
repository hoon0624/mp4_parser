/*
 * Handler Reference Box
 */
public class HDLR extends FullBox {
	
	private int predefined = 0;		// 32 bits
	private String handlerType;		// 32 bits
	private int[] reserved = new int[3];	// 32 bits each
	private String name;	// until the end
	
	HDLR(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.predefined = this.readStreamAsInt(stream, 4);
		this.handlerType = this.readStreamAsString(stream, 4);
		for(int i=0; i<3; i++) {
			this.reserved[i] = this.readStreamAsInt(stream, 4);
		}
		this.name = this.readStreamAsString(stream, this.endPos - stream.getPos());
	}
	
	public String getHandlerType() {
		return this.handlerType;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Predefined: " + this.predefined + "\n");
		str.append("Handler type: " + this.handlerType + "\n");
		for(int i=0; i<3; i++) {
			str.append("Reserved" + i + " " + this.reserved[i] + "\n");
		}
		str.append("Name: " + this.name + "\n");
		
		return str.toString();
	}
}
