
public class HintSampleEntry extends SampleEntry {

	private byte[] data;
	
	HintSampleEntry(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		data = new byte[this.endPos - stream.getPos()];
		stream.read(data);
	}
	
	private String readDataAsHex() {
		StringBuilder str = new StringBuilder();
		for(int i=0; i<this.data.length; i++) {
			String tmp = Integer.toHexString(this.data[i] & 0xFF);
			if(tmp.length() == 1) {
				tmp = "0" + tmp;
			}
			str.append(tmp);
			if(i % 2 == 1) {
				str.append(" ");
			}
		}
		
		return str.toString();
	}
	
	@Override
	public String toString() {
		return super.toString() + "Data: " + this.readDataAsHex();
	}

}
