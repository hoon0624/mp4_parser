import java.io.InputStream;
import java.util.ArrayList;

public class STBL extends Box {

	ArrayList<Box> childBoxes = new ArrayList<>();
	private Box STSD;
	
	STBL(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		System.out.println(this.endPos);
		while(position < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			Box box = constructBox(stream, boxSize, boxType, position);
			this.childBoxes.add(box);
			position = box.endPos;
		}
	}
	
	STBL(InputStream stream, int size, String type, int position, String hdlrType) throws Exception {
		super(stream, size, type, position, hdlrType);
		System.out.println(this.endPos);
		while(position < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			System.out.println("STBL: " + boxType);
			Box box = constructBox(stream, boxSize, boxType, position);
			this.childBoxes.add(box);
			position = box.endPos;
		}
	}
	
	private Box constructBox(InputStream stream, int size, String type, int position) throws Exception {
		switch(type) {
		case "stsd":
			this.STSD = new STSD(stream, size, type, position, this.hdlrType);
			return this.STSD;
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
