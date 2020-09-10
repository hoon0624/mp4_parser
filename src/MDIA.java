import java.io.InputStream;
import java.util.ArrayList;

public class MDIA extends Box {
	
	ArrayList<Box> childBoxes = new ArrayList<>();
	private Box MDHD;
	private Box HDRL;
	private Box MINF;
	
	MDIA(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		while(position < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 4;
			Box box = constructBox(stream, boxSize, boxType, position);
			childBoxes.add(box);
			position = box.endPos;
		}
	}
	
	private Box constructBox(InputStream stream, int size, String type, int position) {
		switch(type) {
		case "mdhd":
			return new MDHD(stream, size, type, position);
		case "hdrl":
			return new HDRL(stream, size, type, position);
		case "minf":
			return new MINF(stream, size, type, position);
		default:
			return new nullBox(stream, size, type, position);
		}
	}
}
