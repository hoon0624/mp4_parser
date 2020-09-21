import java.util.ArrayList;

public class MOOV extends Box {
	
	private ArrayList<Box> childBoxes = new ArrayList<>();
	private MVHD MVHD;
	private IODS IODS;
	private MVEX MVEX;
	private ArrayList<TRAK> TRAKS = new ArrayList<>();
	
	MOOV(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.childBoxes.add(constructBox(stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "mvhd":
			this.MVHD = new MVHD(stream, size, type);
			return this.MVHD;
		case "iods":
			this.IODS = new IODS(stream, size, type);
			return this.IODS;
		case "trak":
			TRAK trak = new TRAK(stream, size, type);
			TRAKS.add(trak);
			return trak;
		case "udta":
			return new UDTA(stream, size, type);
		case "mvex":
			this.MVEX = new MVEX(stream, size, type);
			return this.MVEX;
		default:
			return new nullBox(stream, size, type);
		}
	}
	
	public ArrayList<TRAK> getTraks() {
		return this.TRAKS;
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
