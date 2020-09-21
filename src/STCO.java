import java.util.ArrayList;
/*
 * Chunk Offset Box
 * Chunk offset table gives the index of each chunk into the containing file.
 */
public class STCO extends ChunkOffSetBox {
	
	STCO(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		for(int i=0; i<this.entry_count; i++) {
			this.chunkOffsets.add(this.readStreamAsInt(stream, 4));
		}
	}
	
	public ArrayList<Integer> getChunkOffSets() {
		return this.chunkOffsets;
	}
	
	@Override 
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Entry count: " + this.entry_count + "\n");
		for(int i=0; i<this.entry_count; i++) {
			str.append("Chunk offset_" + i + ": " + this.chunkOffsets.get(i) + "\n");
			if(i > 10) {
				str.append("...\n");
				break;
			}
		}
		
		return str.toString();
	}

}
