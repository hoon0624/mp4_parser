import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/* 
 * Sample Dependency Type Box tells you if 
 * 	1. this sample depend on others (i.e. I frame)
 * 	2. no other samples depend on this one
 * 	3. this sample contain multiple encodings of the data at this time-instant
 */
public class SDTP extends FullBox {
	
	private int entry_count;
	private boolean needUpdate = false;
	ArrayList<int[]> sampleDependencies = new ArrayList<>();
	
	SDTP(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.needUpdate = true;
		int i = 0;
		while(stream.getPos() < this.endPos) {
			sampleDependencies.add(readByteInBits(stream));
			i++;
		}
		this.entry_count = i;
		this.skipToNextBox(stream);
	}
	
	SDTP(MP4Stream stream, int size, String type, int sampleCount) throws Exception {
		super(stream, size, type);
		this.entry_count = sampleCount;
		for(int i=0; i < sampleCount; i++) {
			sampleDependencies.add(readByteInBits(stream));
		}
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
	
	public void updateBox(InputStream stream, int sampleCount) throws IOException {
		stream.read(new byte[12]);
		this.entry_count = sampleCount;
		for(int i=0; i<sampleCount; i++) {
			this.sampleDependencies.add(readByteInBits(stream));
		}
		this.needUpdate = false;
	}
	
	public boolean needUpdate() {
		return this.needUpdate;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Entry count: " + this.entry_count + "\n");
		int[] sampleDepn = new int[4];
		for(int i=0; i<this.entry_count; i++) {
			sampleDepn = this.sampleDependencies.get(i);
			str.append("Chunks_" + i + ": " + "{Reserved: " + sampleDepn[0]
					+ ", Sample_depends_on: " + sampleDepn[1]
					+ ", Sample_is_depended_on: " + sampleDepn[2]
					+ ", Sample_has_redundancy: " + sampleDepn[3] + "}\n");
			if(i > 10) {
				str.append("...\n");
				break;
			}
		}
		
		return str.toString();
	}
}
