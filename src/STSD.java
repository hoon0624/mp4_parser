import java.util.ArrayList;
/*
 * Sample Description Box
 */
public class STSD extends FullBox {

	ArrayList<Box> childBoxes = new ArrayList<>();
	private int entry_count;
	
	STSD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.entry_count = this.readStreamAsInt(stream, 4);
		for(int i=0; i<this.entry_count; i++) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			childBoxes.add(constructBox(stream, boxSize, boxType));
		}
	}
	
	STSD(MP4Stream stream, int size, String type, String hdlrType) throws Exception {
		super(stream, size, type, hdlrType);
		this.entry_count = this.readStreamAsInt(stream, 4);
		for(int i=0; i<this.entry_count; i++) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			childBoxes.add(constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(this.hdlrType) {
		case "soun":
			return new AudioSampleEntry(stream, size, type);
		case "vide":
			return new VisualSampleEntry(stream, size, type);
		case "hint":
			return new HintSampleEntry(stream, size, type);
		default:
			return new nullBox(stream, size, type);
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
