import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*
 * Segment Index Box provides a compact index of the media segment to which it applies 
 */
public class SIDX extends FullBox {

	private int reference_ID;	// 32 bits
	private int timescale;		// 32 bits
	private int earliest_presentation_time;		// (ver_1) 64 bits or (ver_0) 32 bits
	private int first_offset;		// (ver_1) 64 bits or (ver_0) 32 bits
	private int reference_count;	// 16 bits
	private ArrayList<int[]> reference_data = new ArrayList<>();
	
	SIDX(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		int numByte = this.getNumOfBytes();
		this.reference_ID = this.readStreamAsInt(stream, 4);
		this.timescale = this.readStreamAsInt(stream, 4);
		this.earliest_presentation_time = this.readStreamAsInt(stream, numByte);
		this.first_offset = this.readStreamAsInt(stream, numByte);
		stream.read(new byte[2]);
		this.reference_count = this.readStreamAsInt(stream, 2);
		for(int i=0; i<this.reference_count; i++) {
			readReference(stream);
		}
	}
	
	private void readReference(MP4Stream stream) throws Exception {
		int[] data = new int[5];
		int[] tmp = readByteInBits(stream);
		data[0] = tmp[0];
		data[1] = tmp[1] + this.readStreamAsInt(stream, 3);
		data[2] = this.readStreamAsInt(stream, 4);
		tmp = readByteInBits(stream);
		data[3] = tmp[0];
		data[4] = tmp[1] + this.readStreamAsInt(stream, 3);
		
		this.reference_data.add(data);
	}
	
	private int[] readByteInBits(InputStream stream) throws IOException {
		int data = stream.read();
		int[] tmp = new int[2];
		tmp[0] = data >> 7;		// shift right 7 bits
		tmp[1] = (data & 127) << 24;		// data & 01111111 and shift left 24 bits
		
		return tmp;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Reference ID: " + this.reference_ID + "\n");
		str.append("Time scale: " + this.timescale + "\n");
		str.append("earliest_presentation_time: " + this.earliest_presentation_time + "\n");
		str.append("first_offset: " + this.first_offset + "\n");
		str.append("reference_count: " + this.reference_count + "\n");
		for(int i=0; i<this.reference_data.size(); i++) {
			int[] data = this.reference_data.get(i);
			str.append("{reference_type: " + data[0]);
			str.append(", referenced_size: " + data[1]);
			str.append(", subsegment_duration: " + data[2]);
			str.append(", contains_RAP: " + data[3]);
			str.append(", RAP_delta_time: " + data[4] + "}\n");
			if(i > 9) {
				str.append("...\n");
				break;
			}
		}
		
		return str.toString();
	}

}
