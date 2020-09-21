
public abstract class SampleEntry extends Box {
	
	protected int[] reserved = new int[6];		// 6 byte
	protected int data_reference_index;			//2 byte
	
	SampleEntry(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		stream.read(new byte[6]);		// skip reserved
		this.data_reference_index = this.readStreamAsInt(stream, 2);
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		str += "Data reference index: " + this.data_reference_index + "\n";
		
		return str;
	}
}
