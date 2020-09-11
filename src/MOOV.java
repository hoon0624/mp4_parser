import java.io.InputStream;
import java.util.ArrayList;

public class MOOV extends Box {
	
	private ArrayList<Box> childBoxes = new ArrayList<>();
	private Box MVHD;
	private Box IODS;
	private ArrayList<TRAK> TRAKS = new ArrayList<>();
	
	MOOV(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		while(position < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			Box box = constructBox(stream, boxSize, boxType, position);
			this.childBoxes.add(box);
			position = box.endPos;
			System.out.println("MOOV: " + box.endPos);
			System.out.println("MOOV size: " + boxSize);
		}
	}
	
	private Box constructBox(InputStream stream, int size, String type, int pos) throws Exception {
		switch(type) {
		case "mvhd":
			this.MVHD = new MVHD(stream, size, type, pos);
			return this.MVHD;
		case "iods":
			this.IODS = new IODS(stream, size, type, pos);
			return this.IODS;
		case "trak":
			TRAK trak = new TRAK(stream, size, type, pos);
			TRAKS.add(trak);
			return trak;
		default:
			return new nullBox(stream, size, type, pos);
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
