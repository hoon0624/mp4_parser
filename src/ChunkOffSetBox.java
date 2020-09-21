import java.util.ArrayList;

public class ChunkOffSetBox extends FullBox {

	protected int entry_count;	// 32 bits
	protected ArrayList<Integer> chunkOffsets = new ArrayList<>();
	
	ChunkOffSetBox(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.entry_count = this.readStreamAsInt(stream, 4);
	}
	
	public ArrayList<Integer> getChunkOffSets() {
		return this.chunkOffsets;
	}
}
