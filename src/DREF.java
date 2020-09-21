import java.util.ArrayList;
public class DREF extends FullBox {

	private ArrayList<DataEntryBox> childBoxes = new ArrayList<>();
	private int entry_count;	// 32 bits
	
	DREF(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.entry_count = this.readStreamAsInt(stream, 4);
		for(int i=0; i<this.entry_count; i++) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.childBoxes.add(new DataEntryBox(stream, boxSize, boxType));
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
