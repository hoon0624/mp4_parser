import java.util.ArrayList;
/*
 * Sample To Chunk Box: samples within the media data are grouped into chunks.
 */
public class STSC extends FullBox {

	private int entry_count;	// 32 bits
	ArrayList<int[]> sampleChunks = new ArrayList<>();
	
	STSC(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.entry_count = this.readStreamAsInt(stream, 4);
		for(int i=0; i<entry_count; i++) {
			int[] tmp = new int[3];
			tmp[0] = this.readStreamAsInt(stream, 4);
			tmp[1] = this.readStreamAsInt(stream, 4);
			tmp[2] = this.readStreamAsInt(stream, 4);
			this.sampleChunks.add(tmp);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Entry count: " + this.entry_count + "\n");
		for(int i=0; i<this.entry_count; i++) {
			int[] tmp = this.sampleChunks.get(i);
			str.append("Chunks_" + i
					+ ": {First chunk: " + tmp[0] + ", Sample per chunk: " + tmp[1] 
					+ ", Sample description index: " + tmp[2] + "}\n");
			if(i > 10) {
				str.append("...\n");
				break;
			}
		}
		
		return str.toString();
	}

}
