import java.util.ArrayList;
/*
 * Sample Table Box contains all the time and data indexing of the media samples in a track
 */
public class STBL extends Box {

	ArrayList<Box> childBoxes = new ArrayList<>();
	private STSD STSD;
	private STTS STTS;
	private STSS STSS;
	private STSZ STSZ;
	private STZ2 STZ2;
	private SDTP SDTP;
	private STSC STSC;
	private ChunkOffSetBox ChunkOffSetBox;
	private ArrayList<SGPD> SGPDs = new ArrayList<>();
	private ArrayList<SBGP> SBGPs = new ArrayList<>();
	private MP4Stream cpyStream;
	
	STBL(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.childBoxes.add(constructBox((MP4Stream) stream, boxSize, boxType));
		}
	}
	
	STBL(MP4Stream stream, int size, String type, String hdlrType) throws Exception {
		super(stream, size, type, hdlrType);
		while(stream.getPos() < this.endPos) {
			int boxSize = this.readStreamAsInt(stream, 4);
			String boxType = this.readStreamAsString(stream, 4);
			this.childBoxes.add(constructBox((MP4Stream)stream, boxSize, boxType));
		}
	}
	
	private Box constructBox(MP4Stream stream, int size, String type) throws Exception {
		switch(type) {
		case "stsd":
			this.STSD = new STSD(stream, size, type, this.hdlrType);
			return this.STSD;
		case "stts":
			this.STTS = new STTS(stream, size, type);
			return this.STTS;
		case "stss":
			this.STSS = new STSS(stream, size, type);
			return this.STSS;
		case "stsz":
			this.STSZ = new STSZ(stream, size, type);
			if(this.SDTP != null && this.SDTP.needUpdate()) {
				this.SDTP.updateBox(this.cpyStream, this.STSZ.getSampleCount());
				this.cpyStream.close();
			}
			return this.STSZ;
		case "stz2":
			this.STZ2 = new STZ2(stream, size, type);
			if(this.SDTP != null && this.SDTP.needUpdate()) {
				this.SDTP.updateBox(this.cpyStream, this.STZ2.getSampleCount());
				this.cpyStream.close();
			}
		case "sdtp":
			if(this.STSZ != null) {
				this.SDTP = new SDTP(stream, size, type, this.STSZ.getSampleCount());
			} else if(this.STZ2 != null) {
				this.SDTP = new SDTP(stream, size, type, this.STZ2.getSampleCount());
			} else {
				this.cpyStream = stream.copyInstanceOfStream();
				this.SDTP = new SDTP(stream, size, type);
			}
			return this.SDTP;
		case "stsc":
			this.STSC = new STSC(stream, size, type);
			return this.STSC;
		case "stco":
			this.ChunkOffSetBox = new STCO(stream, size, type);
			return this.ChunkOffSetBox;
		case "co64":
			this.ChunkOffSetBox = new CO64(stream, size, type);
			return this.ChunkOffSetBox;
		case "sgpd":
			SGPD sgpd = new SGPD(stream, size, type, this.hdlrType);
			this.SGPDs.add(sgpd);
			return sgpd;
		case "sbgp":
			SBGP sbgp = new SBGP(stream, size, type);
			this.SBGPs.add(sbgp);
			return sbgp;
		default:
			return new nullBox(stream, size, type);
		}
	}
	
	public STTS getStts() {
		return this.STTS;
	}
	public STSS getStss() {
		return this.STSS;
	}
	public STSC getStsc() {
		return this.STSC;
	}
	public STSZ getStsz() {
		return this.STSZ;
	}
	public ChunkOffSetBox getChunkOffSetBox() {
		return this.ChunkOffSetBox;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("\n\t");
		for(Box box : this.childBoxes) {
			str.append(box.toString().replaceAll("\n", "\n\t") + "\n\t");
		}
		return str.toString().replaceAll("\t*\n\t+$", "");
	}
}
