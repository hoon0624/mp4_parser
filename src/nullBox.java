
public class nullBox extends Box {
	
	nullBox(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
//		this.skipToNextBox(stream, 8);
		this.skipToNextBox(stream);
	}
}
