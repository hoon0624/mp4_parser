import java.util.ArrayList;
/* 
 * Sample Size Box contains the sample count and a table giving the size in bytes of each sample.
 */
public class STSZ extends FullBox {

	private int sample_size;	// 32 bits
	private int sample_count;	// 32 bits
	private ArrayList<Integer> sampleSizes = new ArrayList<>();
	
	STSZ(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.sample_size = this.readStreamAsInt(stream, 4);
		this.sample_count = this.readStreamAsInt(stream, 4);
		if(this.sample_size == 0) {
			for(int i=0; i < this.sample_count; i++) {
				this.sampleSizes.add(this.readStreamAsInt(stream, 4));
			}
		}
	}
	
	public int getSampleCount() {
		return this.sample_count;
	}
	
	public ArrayList<Integer> getSampleSizes() {
		return this.sampleSizes;
	}
	
	public int getSampleSize() {
		return this.sample_size;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Sample size: " + this.sample_size + "\n");
		str.append("Sample count: " + this.sample_count + "\n");
		for(int i=0; i< sampleSizes.size(); i++) {
			str.append("Sample size_" + i + ": " + sampleSizes.get(i) + "\n");
			if(i > 10) {
				str.append("...\n");
				break;
			}
		}
		
		return str.toString();
	}
}
