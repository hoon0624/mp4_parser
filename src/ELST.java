import java.util.ArrayList;
/*
 * Edit List Box contains an explicit time-line map
 * Each entry defines part of the track time-line
 */
public class ELST extends FullBox {
	
	private int entry_count;	// 32 bits
	private ArrayList<int[]> entryData = new ArrayList<>();
	
	ELST(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		int numByte = this.getNumOfBytes();
		this.entry_count = this.readStreamAsInt(stream, 4);
		for(int i=0; i<this.entry_count; i++) {
			int data[] = new int[4];
			data[0] = this.readStreamAsInt(stream, numByte);		// segment_duration
			data[1] = this.readStreamAsInt(stream, numByte);		// media_time
			data[2] = this.readStreamAsInt(stream, 2);			// media_rate_integer
			data[3] = this.readStreamAsInt(stream, 2);			// media_rate_fraction
			this.entryData.add(data);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Entry count: " + this.entry_count + "\n");
		int i = 0;
		for(int[] data : this.entryData) {
			str.append("Segment_" + i + ":\n");
			str.append("\tSegment duration: " + data[0] + "\n");
			str.append("\tMedia time: " + data[1] + "\n");
			str.append("\tMedia rate integer: " + data[2] + "\n");
			str.append("\tMedia rate fraction: " + data[3] + "\n");
			i++;
		}
		
		return str.toString();
	}
}
