import java.util.ArrayList;
/*
 * Track Fragment Run Box
 * A track run documents a contiguous set of samples for a track 
 */
public class TRUN extends FullBox {
	
	private int sample_count;
	private int data_offset;
	private int first_sample_flags;
	private boolean data_offset_present = false;
	private boolean first_sample_flags_present = false;
	private boolean sample_duration_present = false;
	private boolean sample_size_present = false;
	private boolean sample_flags_present = false;
	private boolean sample_composition_time_offsets_present = false;
	private ArrayList<ArrayList<String>> sample_data = new ArrayList<>();

	TRUN(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.sample_count = this.readStreamAsInt(stream, 4);
		constructSample(stream);
	}
	
	private void constructSample(MP4Stream stream) throws Exception {
		int flag = this.flags;
		if(flag >= 2048) {
			this.sample_composition_time_offsets_present = true;
			flag -= 2048;
		}
		if(flag >= 1024) {
			this.sample_flags_present = true;
			flag -= 1024;
		}
		if(flag >= 512) {
			this.sample_size_present = true;
			flag -= 512;
		}
		if(flag >= 256) {
			this.sample_duration_present = true;
			flag -= 256;
		}
		if(flag >= 4) {
			this.first_sample_flags_present = true;
			flag -= 4;
		}
		if(flag >= 1) {
			this.data_offset_present = true;
			this.data_offset = this.readStreamAsInt(stream, 4);
		}
		if(this.first_sample_flags_present) {
			this.first_sample_flags = this.readStreamAsInt(stream, 4);
		}
		
		readSamples(stream);
	}

	private void readSamples(MP4Stream stream) throws Exception {
		while(stream.getPos() < this.endPos) {
			ArrayList<String> str = new ArrayList<>();
			if(this.sample_duration_present) {
				str.add("sample_duration: " + this.readStreamAsInt(stream, 4));
			}
			if(this.sample_size_present) {
				str.add("sample_size: " + this.readStreamAsInt(stream, 4));
			}
			if(this.sample_flags_present) {
				str.add("sample_flags:  " + this.readStreamAsInt(stream, 4));
			}
			if(this.sample_composition_time_offsets_present) {
				str.add("sample_composition_time_offset: " + this.readStreamAsInt(stream, 4));
			}
			this.sample_data.add(str);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Sample count: " + this.sample_count + "\n");
		if(this.data_offset_present) {
			str.append("Data offset: " + this.data_offset + "\n");
		}
		if(this.first_sample_flags_present) {
			str.append("First sample flag: " + this.first_sample_flags + "\n");
		}
		int i = 0;
		for(ArrayList<String> sample : this.sample_data) {
			str.append("entries_" + i + ": {");
			for(int j=0; j<sample.size(); j++) {
				str.append(sample.get(j));
				if(j == sample.size()-1) {
					str.append("}\n");
				} else {
					str.append(", ");
				}
			}
			if(i > 9) {
				str.append("...\n");
				break;
			}
			i++;
		}
		
		return str.toString();
	}
}
