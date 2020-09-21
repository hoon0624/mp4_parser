import java.util.ArrayList;
/*
 * Media Information Box contains all the objects that declare characteristic info of the media in the track
 */
public class MINF extends Box {
	
	private ArrayList<Box> childBoxes = new ArrayList<>();
	private VMHD VMHD;
	private SMHD SMHD;
	private DINF DINF;
	private STBL STBL;
	private HMHD HMHD;

	MINF(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.childBoxes.add(this.constructBox(stream, boxSize, boxType));
		}
	}
	
	MINF(MP4Stream stream, int size, String type, int position, String hdlerType) throws Exception {
		super(stream, size, type, hdlerType);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.childBoxes.add(this.constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "vmhd":
			this.VMHD = new VMHD(stream, size, type);
			return this.VMHD;
		case "dinf":
			this.DINF = new DINF(stream, size, type);
			return this.DINF;
		case "stbl":
			this.STBL = new STBL(stream, size, type, this.hdlrType);
			return this.STBL;
		case "smhd":
			this.SMHD = new SMHD(stream, size, type);
			return this.SMHD;
		case "hmhd":
			this.HMHD = new HMHD(stream, size, type);
			return this.HMHD;
		}
		return new nullBox(stream, size, type);
	}
	
	public STBL getStbl() {
		return this.STBL;
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
