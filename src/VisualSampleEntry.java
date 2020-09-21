import java.util.ArrayList;

public class VisualSampleEntry extends SampleEntry {
	
	private int pre_defined_0 = 0;		// 16 bits
	private int reserved_1 = 0;			// 16 bits
	private int[] pre_defined = new int[3];		// 32 bits each
	private int width;		// 16 bits
	private int height;		// 16 bits
	private double horizResolultion;	// 32 bits
	private double vertResolution;		// 32 bits
	private int reserved_2;				// 32 bits
	private int frame_count = 1;		// 16 bits
	private String compressorName;		// 32 bytes
	private int depth;					// 16 bits
	private int pre_defined_4 = -1;		// 16 bits
	private ArrayList<Box> childBoxes = new ArrayList<>();
	
	VisualSampleEntry(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.pre_defined_0 = this.readStreamAsInt(stream, 2);
		this.reserved_1 = this.readStreamAsInt(stream, 2);
		for(int i=0; i<3; i++) { 
			this.pre_defined[i] = this.readStreamAsInt(stream, 4);
		}
		this.width = this.readStreamAsInt(stream, 2);
		this.height = this.readStreamAsInt(stream, 2);
		this.horizResolultion = this.readFixedPointVal(stream, 4);
		this.vertResolution = this.readFixedPointVal(stream, 4);
		this.reserved_2 = this.readStreamAsInt(stream, 4);
		this.frame_count = this.readStreamAsInt(stream, 2);
		this.compressorName = this.readStreamAsString(stream, 32);
		this.depth = this.readStreamAsInt(stream, 2);
		this.pre_defined_4 = this.readStreamAsInt(stream, 2);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			Box box = constructBox(stream, boxSize, boxType);
			this.childBoxes.add(box);
		}
	}
	
	private Box constructBox(MP4Stream stream, int boxSize, String boxType) throws Exception {
		switch(boxType) {
		case "avcC": 
			return new AVCC(stream, boxSize, boxType);
		default: 
			return new nullBox(stream, boxSize, boxType);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Width: " + this.width + "\n");
		str.append("Height: " + this.height + "\n" );
		str.append("Horizontal resolution: " + this.horizResolultion + "\n");
		str.append("Vertical resolution: " + this.vertResolution + "\n");
		str.append("Frame count: " + this.frame_count + "\n");
		str.append("Compressor name: " + this.compressorName.trim() + "\n");
		str.append("Depth: " + this.depth + "\n");
		str.append("\n\t");
		for(Box box : this.childBoxes) {
			str.append(box.toString().replaceAll("\n", "\n\t") + "\n\t");
		}
		
		return str.toString();
	}
}
