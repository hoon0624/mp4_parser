/*
 * Sample to Group Box can be used to find the group that a sample belongs to and 
 * the associated description of that sample group
 */
public class SBGP extends FullBox {

	private String grouping_type;
	private int entry_count;
	
	SBGP(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.grouping_type = this.readStreamAsString(stream, 4);
		this.entry_count = this.readStreamAsInt(stream, 4);
//		this.skipToNextBox(stream, 20);
		this.skipToNextBox(stream);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Grouping type: " + this.grouping_type + "\n");
		str.append("Entry count: " + this.entry_count + "\n");
		
		return str.toString();
	}
}
