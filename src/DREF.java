import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DREF extends FullBox {

	private ArrayList<Box> childBoxes = new ArrayList<>();
	private int entry_count;
	
	DREF(InputStream stream, int size, String type, int position) throws IOException {
		super(stream, size, type, position);
		position += 4;
		this.entry_count = this.readStreamAsInt(stream, 4);
		for(int i=0; i<this.entry_count; i++) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			position += 8;
			this.childBoxes.add(new DataEntryBox(stream, boxSize, boxType, position));
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Entry_count: " + this.entry_count + "\n");
		str.append("\n\t");
		for(Box box : this.childBoxes) {
			str.append(box.toString().replaceAll("\n", "\n\t") + "\n\t");
		}
		return str.toString().replaceAll("\t*\n\t+$", "");
	}
}
