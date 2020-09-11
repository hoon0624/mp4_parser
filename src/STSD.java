import java.io.InputStream;
import java.util.ArrayList;

public class STSD extends FullBox {

	ArrayList<Box> childBoxes = new ArrayList<>();
	private int entry_count;
	
	STSD(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		position += 4;
		this.entry_count = this.readStreamAsInt(stream, 4);
		position += 4;
		
		for(int i=0; i<this.entry_count; i++) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			Box box = constructBox(stream, boxSize, boxType, position);
			childBoxes.add(box);
			position = box.endPos;
		}
	}
	
	STSD(InputStream stream, int size, String type, int position, String hdlrType) throws Exception {
		super(stream, size, type, position, hdlrType);
		position += 4;
		this.entry_count = this.readStreamAsInt(stream, 4);
		position += 4;
		
		for(int i=0; i<this.entry_count; i++) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			Box box = constructBox(stream, boxSize, boxType, position);
			System.out.println(box);
			childBoxes.add(box);
			position = box.endPos;
		}
	}
	
	private Box constructBox(InputStream stream, int size, String type, int position) throws Exception {
		switch(this.hdlrType) {
		case "soun":
			return new AudioSampleEntry(stream, size, type, position);
		case "vide":
			return new VisualSampleEntry(stream, size, type, position);
//		case "hint":
//			return new HintSampleEntry(stream, size, type, position);
//		case "meta":
//			return new MetadataSampleEntry(stream, size, type, position);
		default:
			return new nullBox(stream, size, type, position);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n\t");
		for(Box box : this.childBoxes) {
			str.append(box.toString().replaceAll("\n", "\n\t") + "\n\t");
		}
		return str.toString().replaceAll("\t*\n\t+$", "");
	}
}
