/*
 * Edit Box maps the presentation time-line to the media time-line as it is stored in the file
 */
public class EDTS extends Box {
	
	private ELST elst;
	
	EDTS(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.elst = new ELST(stream, boxSize, boxType);
		}
	}
	
	@Override
	public String toString() {
		if(this.elst != null) {
			StringBuilder str = new StringBuilder();
			str.append(super.toString());
			str.append("\n\t");
			str.append(this.elst.toString().replaceAll("\n", "\n\t"));
			return str.toString();
		} else {
			return super.toString();
		}
	}
}
