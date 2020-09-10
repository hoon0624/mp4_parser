import java.io.InputStream;

public class HDRL extends FullBox {
	
	private int predefined = 0;
	private String handlerType;
	private int[] reserved = new int[3];
	private String name;
	
	HDRL(InputStream stream, int size, String type, int position) {
		super(stream, size, type, position);
		position += 4;
		this.predefined = this.readStreamAsInt(stream, 4);
		this.handlerType = this.readStreamAsString(stream, 4);
		for(int i=0; i<3; i++) {
			this.reserved[i] = this.readStreamAsInt(stream, 4);
		}
		position += 20;
		this.name = this.readStreamAsString(stream, this.endPos - position);
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
