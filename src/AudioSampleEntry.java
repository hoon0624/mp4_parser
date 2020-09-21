import java.util.ArrayList;

public class AudioSampleEntry extends SampleEntry {

	private int[] reserved = new int[2];	// 32 bits each
	private int channelcount = 2;			// 16 bits
	private int samplesize = 16;			// 16 bits
	private int predefined = 0;				// 16 bits
	private int reserved2 = 0;				// 16 bits
	private int samplerate;					// 32 bits 
	private ArrayList<Box> childBoxes = new ArrayList<>();
	
	AudioSampleEntry(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		for(int i=0; i<2; i++) {
			this.reserved[i] = this.readStreamAsInt(stream, 4);
		}
		this.channelcount = this.readStreamAsInt(stream, 2);
		this.samplesize = this.readStreamAsInt(stream, 2);
		this.predefined = this.readStreamAsInt(stream, 2);
		this.reserved2 = this.readStreamAsInt(stream, 2);
		this.samplerate = this.readStreamAsInt(stream, 2); 		// need to know when to read 4 bytes. Default samplerate << 16
		stream.read(new byte[2]);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			childBoxes.add(new nullBox(stream, boxSize, boxType));
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Channel count: " + this.channelcount + "\n");
		str.append("Sample size: " + this.samplesize + "\n");
		str.append("Sample rate: " + this.samplerate + "\n");
		str.append("\n\t");
		for(Box box : this.childBoxes) {
			str.append(box.toString().replaceAll("\n", "\n\t") + "\n\t");
		}
		
		return str.toString().replaceAll("\n\t+$", "");
	}

}
