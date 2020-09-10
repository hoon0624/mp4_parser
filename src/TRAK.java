import java.io.InputStream;
import java.util.ArrayList;

public class TRAK extends Box {
	
	private ArrayList<Box> childBoxes = new ArrayList<>();
	private Box TKHD;
	private Box EDTS;
	private Box TREF;
	private Box MDIA;
	
	TRAK(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		
		while(position < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			Box box = this.constructBox(stream, boxSize, boxType, position);
			this.childBoxes.add(box);
			position = box.endPos;
		}
	}
	
	private Box constructBox(InputStream stream, int size, String type, int pos) throws Exception {
		switch(type) {
		case "tkhd":
			this.TKHD = new TKHD(stream, size, type, pos);
			return this.TKHD;
		case "edts":
			this.EDTS = new EDTS(stream, size, type, pos);
			return this.EDTS;
		case "mdia":
			this.MDIA = new MDIA(stream, size, type, pos);
			return this.MDIA;
		case "tref":
			this.TREF = new TREF(stream, size, type, pos);
			return this.TREF;
		default:
			return new nullBox(stream, size, type, pos);
		}
	}
	
	@Override 
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
//		for(Box child : this.childBoxes) {
//			str.append(child.toString());
//		}
		str.append("\n\t");
		for(Box box : this.childBoxes) {
			str.append(box.toString().replaceAll("\n", "\n\t") + "\n\t");
		}
		
		return str.toString().replaceAll("\n\t+$", "");
	}
}
