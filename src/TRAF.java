import java.util.ArrayList;
/*
 * Track Fragment Box 
 */
public class TRAF extends Box {

	private ArrayList<Box> childBoxes = new ArrayList<>();
	private TFHD TFHD;
	private ArrayList<TRUN> TRUNs = new ArrayList<>();
	
	TRAF(MP4Stream stream, int size, String type) throws Exception {
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
		case "tfhd":
			this.TFHD = new TFHD(stream, size, type);
			return this.TFHD;
		case "trun":
			TRUN trun = new TRUN(stream, size, type);
			this.TRUNs.add(trun); 
			return trun;
		default: 
			return new nullBox(stream, size, type);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n\t");
		int i = 0;
		for(Box box : this.childBoxes) {
			str.append(box.toString().replaceAll("\n", "\n\t") + "\n\t");
			if(i > 4) {
				str.append("...\n");
				break;
			}
			i++;
		}
		return str.toString().replaceAll("\t*\n\t+$", "");
	}
}
