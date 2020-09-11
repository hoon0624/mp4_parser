import java.io.InputStream;

public class DataEntryBox extends FullBox {

	private String data;
	
	DataEntryBox(InputStream stream, int size, String type, int position) {
		super(stream, size, type, position);
		position += 4;
		if(this.flags != 0) {
			this.data = this.readStreamAsString(stream, this.endPos - position);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		if(data != null && data.length() != 0) {
			str.append("Location: " + this.data + "\n");
		}
		
		return str.toString();
	}
}
