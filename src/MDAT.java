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
	
	public void printMDATSamples(ArrayList<TRAK> Traks) throws Exception {
		for(TRAK trak : Traks) {
			ArrayList<Sample> samples = getSamples(trak.getStbl());
			this.Samples.add(samples);
			this.hdlrTypes.add(trak.getHdlrType());
		}
		printSamples();
		this.cpyStream.close();
	}
	
	// get and organize samples data
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
			
			samples.add(new Sample(size, duration, offset, isIframe));
			
			offset += size;
			if(i >= 20) {
				break;
			}
		}
		return samples;
	}
	
	// convert and print mdat data into hexString and ascii characters
	private void dataToHexnASCII(byte[] data) {
		ArrayList<String> str = new ArrayList<>();
		StringBuilder hexStr = new StringBuilder();
		StringBuilder asciiStr = new StringBuilder();
		int count = 0;
		for(int i=0; i<data.length; i++) {
			String hex = Integer.toHexString(data[i] & 0xFF);
			if(hex.length() == 1) {
				hex = "0" + hex;
			}
			hexStr.append(hex);
			char ascii = '.';
			if(data[i] > 31) {
				ascii = (char) data[i];
			}
			asciiStr.append(ascii);
			if(count%16 == 15) {
				str.add(hexStr.toString() + "\t\t" + asciiStr.toString() + "\n\t");
				hexStr = new StringBuilder();
				asciiStr = new StringBuilder();
			} 
			else if(count%4 == 3) {
				hexStr.append(" ");
				asciiStr.append(" ");
			}
			count++;
		}
		
		if(data.length < 48 && data.length%16 != 0) {
			int n = 35 - (hexStr.length()%35);
			for(int j=0; j<n; j++) {
				hexStr.append(" ");
			}
			str.add(hexStr.toString() + "\t\t" + asciiStr.toString() + "\n\t");
		}

		str.forEach(System.out::print);
	}
	
	private void printSamples() throws Exception {
		int hdlrN = 0;
		for(ArrayList<Sample> samples : this.Samples) {
			int i = 0;
			System.out.println("\n" + this.hdlrTypes.get(hdlrN)+ " Samples:\n");		// print handler type of samples
			for(Sample sample : samples) {
				// if sample position has passed, make another stream 
				if(sample.endPos < this.cpyStream.getPos()) {
					this.cpyStream.close();
					this.cpyStream = new MP4Stream(this.cpyStream.getSourceFilePath());
					this.cpyStream.skip(sample.endPos - sample.size);
				}
				byte[] data;
				System.out.println("Sample_" + i + ": " + sample);
				
				// read up to 48 bytes
				if(sample.size < 48) {
					data = new byte[sample.size];
				} else {
					data = new byte[48];
				}
				this.cpyStream.read(data);
				System.out.print("Data:\n\t");
				dataToHexnASCII(data);
				System.out.println();
				this.cpyStream.read(new byte[sample.endPos - this.cpyStream.getPos()]);
				i++;
			}
			hdlrN++;
		}
		this.cpyStream.close();
	}
	
	// nested class for Sample object
	private class Sample {
		public int size;
		public int duration;
		public int offSet;
		public boolean isIFrame = false;
		public int endPos;
		
		public Sample(int size, int duration, int offSet, boolean bool) throws IOException {
			this.size = size;
			this.duration = duration;
			this.offSet = offSet;
			this.isIFrame = bool;
			this.endPos = this.offSet + this.size;
		}
		
		public String toString() {
			String str = "{size: " + this.size + ", duration: " + this.duration 
					+ ", offset: " + this.offSet + "}";
			if(this.isIFrame) {
				str = "I-frame " + str;
			}			
			return str;
		}
	}
}
