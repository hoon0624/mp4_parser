import java.io.IOException;
import java.io.InputStream;
/*
 * Track Fragment Random Access Box
 * Each entry contains the location and the presentation time of the random accessible sample
 */
public class TFRA extends FullBox {
	
	private int track_ID;
	private int length_size_of_traf_num;
	private int length_size_of_trun_num;
	private int length_size_of_sample_num;
	private int number_of_entry;
	
	TFRA(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.track_ID = this.readStreamAsInt(stream, 4);
		stream.read(new byte[24]);		// reserved
		int[] lenSizeOf = readByteInBits(stream);
		this.length_size_of_traf_num = lenSizeOf[1];
		this.length_size_of_trun_num = lenSizeOf[2];
		this.length_size_of_sample_num = lenSizeOf[3];
		this.number_of_entry = this.readStreamAsInt(stream, 4);
		this.skipToNextBox(stream);
	}
	
	private int[] readByteInBits(InputStream stream) throws IOException {
		int data = stream.read();
		int[] tmp = new int[4];
		tmp[0] = data >> 6;
		tmp[1] = (data >> 4) & 3;
		tmp[2] = (data >> 2) & 3;
		tmp[3] = data & 3;

		return tmp;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Track ID: " + this.track_ID + "\n");
		str.append("Entry number: " + this.number_of_entry + "\n");
		str.append("length_size_of_traf_num: " + this.length_size_of_traf_num + "\n");
		str.append("length_size_of_trun_num: " + this.length_size_of_trun_num + "\n");
		str.append("length_size_of_sample_num: " + this.length_size_of_sample_num + "\n");
		
		return str.toString();
	}
}
