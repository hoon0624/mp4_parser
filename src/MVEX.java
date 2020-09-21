import java.util.ArrayList;
/*
 * Movie Extend Box warns readers that there might be Movie Fragment Boxes in this file
 * To know of all samples in the tracks, these Movie Fragment Boxes must be found and scanned
 */
public class MVEX extends Box{
	
	private MEHD MEHD;
	private TREX TREX;
	private ArrayList<Box> childBoxes = new ArrayList<>();
	
	MVEX(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			childBoxes.add(constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "mehd":
			this.MEHD = new MEHD(stream, size, type);
			return this.MEHD;
		case "trex":
			this.TREX = new TREX(stream, size, type);
			return this.TREX;
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
