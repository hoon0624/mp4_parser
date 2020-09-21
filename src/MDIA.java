import java.util.ArrayList;
/*
 * Media Box contains all objects that declare info about media data within a track
 */
public class MDIA extends Box {
	
	ArrayList<Box> childBoxes = new ArrayList<>();
	private MDHD MDHD;
	private HDLR HDLR;
	private MINF MINF;
	
	MDIA(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			childBoxes.add(constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "mdhd":
			this.MDHD = new MDHD(stream, size, type);
			return this.MDHD;
		case "hdlr":
			this.HDLR = new HDLR(stream, size, type);
			this.hdlrType = this.HDLR.getHandlerType();
			return this.HDLR;
		case "minf":
			this.MINF = new MINF(stream, size, type, this.hdlrType);
			return this.MINF;
		default:
			return new nullBox(stream, size, type);
		}
	}
	
	public STBL getStbl() {
		return this.MINF.getStbl();
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
