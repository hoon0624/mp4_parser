import java.util.ArrayList;

/*
 * Track Fragment Header Box 
 * Each movie fragment can add zero or more fragment to each track, and a track fragment can add
 * zero or more contiguous runs of samples.
 * The track fragment header sets up info and default used for those.
 */
public class TFHD extends FullBox {

	private int track_ID;		// 32 bits
	private boolean base_data_offset;		
	private boolean sample_description_index;	
	private boolean default_sample_duration;	
	private boolean default_sample_size;		
	private boolean default_sample_flags;		
	private boolean duration_is_empty = false;
	private ArrayList<String> default_sample_data = new ArrayList<>();
	
	TFHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.track_ID = this.readStreamAsInt(stream, 4);
		constructDefaultSample(stream);
	}
	
	private void constructDefaultSample(MP4Stream stream) throws Exception {
		int flag = this.flags;
		if(flag >= 65536) {
			this.duration_is_empty = true;
			flag -= 65536;
		}
		if(flag >= 32) {
			this.default_sample_flags = true;
			flag -= 32;
		}
		if(flag >= 16) {
			this.default_sample_size = true;
			flag -= 16;
		}
		if(flag >= 8) {
			this.default_sample_duration = true;
			flag -= 8;
		}
		if(flag >= 2) {
			this.sample_description_index = true;
			flag -= 2;
		}
		if(flag >= 1) {
			this.base_data_offset = true;
		}
		
		readDefaultSample(stream);
	}
	
	private void readDefaultSample(MP4Stream stream) throws Exception {
			if(this.base_data_offset) {
				this.default_sample_data.add("base_data_offset: " + this.readStreamAsInt(stream, 8) + "\n");
			}
			if(this.sample_description_index) {
				this.default_sample_data.add("sample_description_index: " + this.readStreamAsInt(stream, 4) + "\n");
			}
			if(this.default_sample_duration) {
				this.default_sample_data.add("default_sample_duration: " + this.readStreamAsInt(stream, 4) + "\n");
			}
			if(this.default_sample_size) {
				this.default_sample_data.add("default_sample_size: " + this.readStreamAsInt(stream, 4) + "\n");
			}
			if(this.default_sample_flags) {
				this.default_sample_data.add("default_sample_flags: " + this.readStreamAsInt(stream, 4) + "\n");
			}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Track ID: " + this.track_ID + "\n");
		for(int i=0; i<this.default_sample_data.size(); i++) {
			str.append(this.default_sample_data.get(i));
		}
		str.append("duration_is_empty: " + this.duration_is_empty + "\n");
		
		return str.toString();
	}
}
