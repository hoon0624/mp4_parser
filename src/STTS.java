import java.util.ArrayList;
/* 
 * Time to sample box contains a compact version of a table that allows indexing from
 * decoding time to sample number.
 * Time-to-sample box contains duration information for a media's sample
 */
public class STTS extends FullBox {
	
	private int entry_count;	// 32 bits
	private ArrayList<int[]> entries = new ArrayList<>();
	
	STTS(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.entry_count = this.readStreamAsInt(stream, 4);
		for(int i=0; i<this.entry_count; i++) {
			int[] tmp = new int[2];
			tmp[0] = this.readStreamAsInt(stream, 4);
			tmp[1] = this.readStreamAsInt(stream, 4);
			this.entries.add(tmp);
		}
	}
	
	public ArrayList<int[]> getEntries() {
		return this.entries;
	}
	public int getEntryCount() {
		return this.entry_count;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Entry count: " + this.entry_count + "\n");
		for(int i=0; i<this.entry_count; i++) {
			int[] sample = this.entries.get(i);
			str.append("Sample_" + i + ": " + "{Sample count: " + sample[0] + ", Sample delta: " + sample[1] + "}\n");
		}
		
		return str.toString();
	}
}

