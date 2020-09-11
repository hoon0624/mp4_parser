import java.io.InputStream;

public abstract class SampleEntry extends Box {
	
	private int[] reserved = new int[6];
	private int data_reference_index;
	
	SampleEntry(InputStream stream, int size, String type, int position) {
		super(stream, size, type, position);
		this.data_reference_index = this.readStreamAsInt(stream, 2);
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		str += "Data reference index: " + this.data_reference_index + "\n";
		
		return str;
	}
}
