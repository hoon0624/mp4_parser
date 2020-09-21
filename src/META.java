import java.util.ArrayList;
/*
 * Meta Box contains descriptive or annotative metadata
 */
public class META extends FullBox {
	
	ArrayList<Box> childBoxes = new ArrayList<>();
	private HDLR HDLR;
	
	META(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.childBoxes.add(constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "hdlr":
			this.HDLR = new HDLR(stream, size, type);
			return this.HDLR;
		case "ilst":
			return new ILST(stream, size, type);
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
