import java.io.InputStream;
import java.util.ArrayList;

public class MINF extends Box {
	
	private ArrayList<Box> childBoxes = new ArrayList<>();
	private Box VMHD;
	private Box SMHD;
	private Box HMHD;
	private Box NMHD;
	private Box DINF;
	private Box STBL;

	MINF(InputStream stream, int size, String type, int position) throws Exception {
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
	
	MINF(InputStream stream, int size, String type, int position, String hdlerType) throws Exception {
		super(stream, size, type, position, hdlerType);
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
		case "vmhd":
			this.VMHD = new VMHD(stream, size, type, pos);
			return this.VMHD;
		case "dinf":
			this.DINF = new DINF(stream, size, type, pos);
			return this.DINF;
		case "stbl":
			this.STBL = new STBL(stream, size, type, pos, this.hdlrType);
			return this.STBL;
		}
		return new nullBox(stream, size, type, pos);
	}
	
	@Override 
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n\t");
		for(Box box : this.childBoxes) {
			str.append(box.toString().replaceAll("\n", "\n\t") + "\n\t");
		}
		return str.toString().replaceAll("\n\t+$", "");
	}
}
