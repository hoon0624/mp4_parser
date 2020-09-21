import java.util.ArrayList;

public class STZ2 extends FullBox {

	private int field_size;
	private int sample_count;
	private ArrayList<Integer> entrySizes = new ArrayList<>();
	
	STZ2(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		stream.read(new byte[3]);
		this.field_size = this.readStreamAsInt(stream, 1);
		this.sample_count = this.readStreamAsInt(stream, 4);
		int byteSize = this.field_size/8;
		for(int i=0; i<this.sample_count; i++) {
			this.entrySizes.add(this.readStreamAsInt(stream, byteSize));
		}
	}
	
	public int getSampleCount() {
		return this.sample_count;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Field size: " + this.field_size + "\n");
		str.append("Sample count: " + this.sample_count + "\n");
		
		return str.toString();
	}

}
