/*
 * Object Descriptor Box
 */
public class IODS extends Box {
	IODS(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.skipToNextBox(stream);
	}
}
