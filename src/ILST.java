import java.util.ArrayList;

public class ILST extends Box {
	
	ArrayList<Box> childBoxes = new ArrayList<>();

	ILST(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			childBoxes.add(new nullBox(stream, boxSize, boxType));
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
