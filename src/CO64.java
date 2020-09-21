
/*
 * Chunk Offset Box that permits 64-bit offsets
 */
public class CO64 extends ChunkOffSetBox {
	
	CO64(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		for(int i=0; i<this.entry_count; i++) {
			this.chunkOffsets.add(this.readStreamAsInt(stream, 8));
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Entry count: " + this.entry_count + "\n");
		for(int i=0; i<this.entry_count; i++) {
			str.append("Chunk offset_" + i + ": " + this.chunkOffsets.get(i) + "\n");
		}
		
		return str.toString();
	}

}
