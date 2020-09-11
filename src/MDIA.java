import java.io.InputStream;
import java.util.ArrayList;

public class MDIA extends Box {
	
	ArrayList<Box> childBoxes = new ArrayList<>();
	private Box MDHD;
	private HDLR HDLR;
	private Box MINF;
	
	MDIA(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		while(position < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			Box box = constructBox(stream, boxSize, boxType, position);
			childBoxes.add(box);
			position = box.endPos;
			System.out.println("MDIA: " + box.endPos);
			System.out.println("MDIA available: " + stream.available());
		}
	}
	
	private Box constructBox(InputStream stream, int size, String type, int position) throws Exception {
		switch(type) {
		case "mdhd":
			return new MDHD(stream, size, type, position);
		case "hdlr":
			this.HDLR = new HDLR(stream, size, type, position);
			this.hdlrType = this.HDLR.getHandlerType();
			return this.HDLR;
		case "minf":
			return new MINF(stream, size, type, position, this.hdlrType);
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
