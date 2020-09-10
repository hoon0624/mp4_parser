import java.io.InputStream;

public class EDTS extends Box {
	
	private ELST elst;
	
	EDTS(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		while(position < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			this.elst = new ELST(stream, boxSize, boxType, position);
			position = this.elst.endPos;
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
