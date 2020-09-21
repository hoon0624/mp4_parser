
public class DataEntryBox extends FullBox {

	private String data;
	
	DataEntryBox(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		if(this.flags != 0) {
			this.data = this.readStreamAsString(stream, this.endPos - stream.getPos());
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
