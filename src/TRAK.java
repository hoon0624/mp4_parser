import java.util.ArrayList;

public class TRAK extends Box {
	
	private ArrayList<Box> childBoxes = new ArrayList<>();
	private TKHD TKHD;
	private EDTS EDTS;
	private MDIA MDIA;
	
	TRAK(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.childBoxes.add(this.constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "tkhd":
			this.TKHD = new TKHD(stream, size, type);
			return this.TKHD;
		case "edts":
			this.EDTS = new EDTS(stream, size, type);
			return this.EDTS;
		case "mdia":
			this.MDIA = new MDIA(stream, size, type);
			return this.MDIA;
		default:
			return new nullBox(stream, size, type);
		}
	}
	
	public STBL getStbl() {
		return this.MDIA.getStbl();
	}
	public String getHdlrType() {
		return this.MDIA.hdlrType;
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
