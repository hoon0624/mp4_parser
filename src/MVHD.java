import java.io.InputStream;
import java.util.Arrays;

public class MVHD extends FullBox {

	private long creationTime;
	private long modificationTime;
	private int timeScale;
	private int duration;
	
	MVHD(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		position += 4;
		this.creationTime = this.readTimeFields(stream, 4);
		this.modificationTime = this.readTimeFields(stream, 4);
		this.timeScale = this.readStreamAsInt(stream, 4);
		this.duration = this.readStreamAsInt(stream, 4);
		position += 12;
		this.skipToNextBox(stream, 28);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Creation Time: " + this.convertToDate(this.creationTime) + "\n");
		str.append("Modification Time: " + this.convertToDate(this.modificationTime) + "\n");
		str.append("Time scale: " + this.timeScale + "\n");
		str.append("Duration: " + this.duration + "\n");
		
		return str.toString();
	}
}
