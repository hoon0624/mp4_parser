import java.util.ArrayList;

/*
 * Movie Fragment Box extend the presentation in time providing info that would be in Movie box
 */
public class MOOF extends Box {

	private ArrayList<Box> childBoxes = new ArrayList<>();
	private MFHD MFHD;
	private ArrayList<TRAF> TRAFs = new ArrayList<>();
	
	MOOF(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			Box box = constructBox(stream, boxSize, boxType);
			childBoxes.add(box);
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "mfhd":
			this.MFHD = new MFHD(stream, size, type);
			return this.MFHD;
		case "traf":
			TRAF traf = new TRAF(stream, size, type);
			this.TRAFs.add(traf);
			return traf;
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
