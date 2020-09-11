import java.io.InputStream;

public class AudioSampleEntry extends SampleEntry {

	private int[] reserved = new int[3];
	private int channelcount = 2;
	private int samplesize = 16;
	private int predefined = 0;
	private int reserved2 = 0;
	private int samplerate;
	
	AudioSampleEntry(InputStream stream, int size, String type, int position) {
		super(stream, size, type, position);
		
	}

}
