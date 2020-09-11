import java.io.InputStream;
import java.util.ArrayList;

public class DINF extends Box {

	private ArrayList<Box> childBoxes = new ArrayList<>();
	private Box DREF;
	
	DINF(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		while(position < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			Box box = this.constructBox(stream, boxSize, boxType, position);
			childBoxes.add(box);
			position = box.endPos;
		}
	}
	
	private Box constructBox(InputStream stream, int size, String type, int position) throws Exception {
		switch(type) {
		case "dref":
			this.DREF = new DREF(stream, size, type, position);
			return this.DREF;
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
