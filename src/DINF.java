import java.util.ArrayList;
/*
 * Data Information Box contains objects that declare the location of the media info in a track
 */
public class DINF extends Box {

	private ArrayList<Box> childBoxes = new ArrayList<>();
	private Box DREF;
	
	DINF(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			childBoxes.add(this.constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "dref":
			this.DREF = new DREF(stream, size, type);
			return this.DREF;
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
