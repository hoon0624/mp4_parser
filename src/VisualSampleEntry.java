import java.io.InputStream;

public class VisualSampleEntry extends SampleEntry {
	
	private int pre_defined_0 = 0;
	private int reserved_1 = 0;
	private int[] pre_defined = new int[3];
	private double width;
	private double height;
	private int horizResolultion;
	private int vertResolution;
	private int reserved_2;
	private int frame_count;
	private String compressorName;
	private int depth;
	private int pre_defined_4 = -1;
	
	VisualSampleEntry(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		position += 2;
		this.pre_defined_0 = this.readStreamAsInt(stream, 2);
		this.reserved_1 = this.readStreamAsInt(stream, 2);
		for(int i=0; i<3; i++) { 
			this.pre_defined[i] = this.readStreamAsInt(stream, 4);
		}
		this.width = this.readFixedPointVal(stream, 2);
		this.height = this.readFixedPointVal(stream, 2);
		this.horizResolultion = this.readStreamAsInt(stream, 4);
		this.vertResolution = this.readStreamAsInt(stream, 4);
		this.reserved_2 = this.readStreamAsInt(stream, 4);
		this.frame_count = this.readStreamAsInt(stream, 2);
		this.compressorName = this.readStreamAsString(stream, 4);
		this.depth = this.readStreamAsInt(stream, 2);
		this.pre_defined_4 = this.readStreamAsInt(stream, 2);
	}
}
