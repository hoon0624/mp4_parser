import java.io.IOException;
import java.util.ArrayList;
/*
 * Media Data Box contains media data (e.g. in video tracks, it will contain
 * video frame
 */
public class MDAT extends Box {

	private MP4Stream cpyStream;
	private ArrayList<ArrayList<Sample>> Samples = new ArrayList<>();
	private ArrayList<String> hdlrTypes = new ArrayList<>();
	
	MDAT(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.cpyStream = stream.copyInstanceOfStream(); 
		stream.read(new byte[this.endPos - stream.getPos()]);
	}
	
	public void update(ArrayList<TRAK> Traks) throws Exception {
		for(TRAK trak : Traks) {
			ArrayList<Sample> samples = getSamples(trak.getStbl());
			this.Samples.add(samples);
			this.hdlrTypes.add(trak.getHdlrType());
		}
		this.cpyStream.close();
	}
	
	private ArrayList<Sample> getSamples(STBL stbl) throws Exception {
		STSZ stsz = stbl.getStsz();
		STTS stts = stbl.getStts();
		STSS stss = stbl.getStss();
		ChunkOffSetBox chunkOffSetBox = stbl.getChunkOffSetBox();
	
		ArrayList<Integer> sampleSizes = stsz.getSampleSizes();
		ArrayList<int[]> entries = stts.getEntries();
		ArrayList<Sample> samples = new ArrayList<>();
		ArrayList<Integer> keyFrames = new ArrayList<>();
		if(stss != null) {
			keyFrames = stss.getKeyFrames();
		}
		 
		int sample_size = stsz.getSampleSize();
		int size = sample_size;
		int duration = entries.get(0)[1];
		int offset = chunkOffSetBox.getChunkOffSets().get(0);
		boolean isIframe = false;

		for(int i=0; i<stsz.getSampleCount(); i++) {
			if(sample_size == 0) {
				size = sampleSizes.get(i);
			}
			if(stts.getEntryCount() != 1) {
				int index = 0;
				for(int[] entry : entries) {
					if(index + entry[0] > i) {
						duration = entry[1];
					}
					index += entry[0];
				}
			}
			if(keyFrames.contains(i+1)) {
				isIframe = true;
			} else {
				isIframe = false;
			}
			
			samples.add(new Sample(size, duration, offset, isIframe, this.cpyStream));
			
			offset += size;
			if(i >= 20) {
				break;
			}
		}
		return samples;
	}
	
	
	
	@Override 
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n");
		for(int i=0; i<this.Samples.size(); i++) {
			int[] j = {0};
			str.append(this.hdlrTypes.get(i) + " Samples:\n\n");
			this.Samples.get(i).forEach(s -> {
				str.append("Sample_" + j[0] + ": " + s);
				j[0]++;
			});
			str.append("\n");
		}
		
		return str.toString();
	}
	
	private class Sample {
		public int size;
		public int duration;
		public int offSet;
		public byte[] data;
		public boolean isIFrame = false;
		public int endPos;
		
		public Sample(int size, int duration, int offSet, boolean bool, MP4Stream stream) throws IOException {
			this.size = size;
			this.duration = duration;
			this.offSet = offSet;
			this.isIFrame = bool;
			if(size < 48) {
				this.data = new byte[size];
			} else {
				this.data = new byte[48];
			}
			this.endPos = this.offSet + this.size;
			stream.read(data);
			int streamPos = (int) stream.getPos();
			if(streamPos < this.endPos) {
				stream.read(new byte[this.endPos - streamPos]);
			}
		}
		
		private String dataToHexString() {
			StringBuilder str = new StringBuilder();
			int count = 0;
			for(int i=0; i<this.data.length; i++) {
				String hex = Integer.toHexString(this.data[i] & 0xFF);
				if(hex.length() == 1) {
					hex = "0" + hex;
				}
				str.append(hex);
				
				if(count%16 == 15) {
					str.append("\n\t");
				} 
				else if(count%4 == 3) {
					str.append(" ");
				}
				count++;
			}
			
			return str.toString();
		}
		
		public String toString() {
			String str = "{size: " + this.size + ", duration: " + this.duration 
					+ ", offset: " + this.offSet + "}\n";
			if(this.isIFrame) {
				str = "I-frame " + str;
			}
			str += "Data:\n\t" + dataToHexString() + "\n";
			
			return str;
		}
	}
}
