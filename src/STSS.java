import java.util.ArrayList;
/* 
 * Sync Sample Box provides a compact marking of the random access points within stream.
 */
public class STSS extends FullBox {
	
	private int entry_count;
	private ArrayList<Integer> entries = new ArrayList<>(); 

	STSS(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.entry_count = this.readStreamAsInt(stream, 4);
		for(int i=0; i<this.entry_count; i++) {
			this.entries.add(this.readStreamAsInt(stream, 4));
		}
	}
	
	public ArrayList<Integer> getKeyFrames() {
		return this.entries;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		for(int i=0; i<this.entry_count; i++) {
			str.append("Sample_" + i + ": " + this.entries.get(i) + "\n");
			if(i > 10) {
				str.append("...\n");
				break;
			}
		}
		
		return str.toString();
	}

}

