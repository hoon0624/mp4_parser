import java.io.InputStream;

public class TKHD extends FullBox {
	private long creationTime;
	private long modificationTime;
	private int trackID;
	private int duration;
	private double width;
	private double height;

	TKHD(InputStream stream, int size, String type, int position) throws Exception {
		super(stream, size, type, position);
		position += 4;
		this.creationTime = this.readTimeFields(stream, 4);
		this.modificationTime = this.readTimeFields(stream, 4);
		this.trackID = this.readStreamAsInt(stream, 4);
		stream.read(new byte[4]);
		this.duration = this.readStreamAsInt(stream, 4);
		stream.read(new byte[52]);
		this.width = this.readFixedPointVal(stream, 4);
		this.height = this.readFixedPointVal(stream, 4);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Creation time: " + this.convertToDate(this.creationTime) + "\n");
		str.append("modification time: " + this.convertToDate(this.modificationTime) + "\n");
		str.append("Track ID: " + this.trackID + "\n");
		str.append("Duration: " + this.duration + "\n");
		str.append("Width: " + this.width + "\n");
		str.append("Height: " + this.height + "\n");
		
		return str.toString();
	}
}
