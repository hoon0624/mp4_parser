import java.util.ArrayList;
/*
 * Movie Fragment Random Access Box provides a table which may assist readers in finding 
 * random access points in a file using movie fragments
 */
public class MFRA extends Box {
	
	private ArrayList<Box> childBoxes = new ArrayList<>();
	
	MFRA(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			childBoxes.add(constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int boxSize, String boxType) throws Exception {
		switch(boxType) {
		case "tfra":
			return new TFRA(stream, boxSize, boxType);
		case "mfro":
			return new MFRO(stream, boxSize, boxType);
		default:
			return new nullBox(stream, boxSize, boxType);
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
