import java.io.InputStream;

public class MDHD extends FullBox {

	private long creationTime;
	private long modificationTime;
	private int timeScale;
	private int duration;
	private int language;
	private int quality;

	MDHD(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		position += 4;
		this.creationTime = this.readTimeFields(stream, 4);
		this.modificationTime = this.readTimeFields(stream, 4);
		this.timeScale = this.readStreamAsInt(stream, 4);
		this.duration = this.readStreamAsInt(stream, 4);
		this.language = this.readStreamAsInt(stream, 2);
		this.quality = this.readStreamAsInt(stream, 4);
	}
	
	@Override 
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Creation time: " + this.convertToDate(this.creationTime) + "\n");
		str.append("Modification time:" + this.convertToDate(this.modificationTime) + "\n");
		str.append("Time scale: " + this.timeScale + "\n");
		str.append("Duration: " + this.duration + "\n");
		str.append("Language: " + this.language + "\n");
		str.append("Quality: " + this.quality + "\n");
		
		return str.toString();
	}
	
	
}
