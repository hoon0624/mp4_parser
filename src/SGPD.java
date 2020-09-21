/*
 * Sample Group Description Box gives information about the characteristics of sample groups.
 */
public class SGPD extends FullBox {
	
	private String grouping_type;	
	private int entry_count;
	
	
	SGPD(MP4Stream stream, int size, String type, String hdlrType) throws Exception {
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
