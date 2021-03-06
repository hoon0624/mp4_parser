/*
 * Video Media Header Box
 */
public class VMHD extends FullBox {
	
	private int grpahicsmode = 0;
	private int[] opcolor = new int[3];
	
	VMHD(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.grpahicsmode = this.readStreamAsInt(stream, 2);
		for(int i=0; i<3; i++) {
			this.opcolor[i] = this.readStreamAsInt(stream, 2);
		}
	}
	
	@Override 
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Graphics mode: " + this.grpahicsmode + "\n");
		str.append("Opcolor_r: " + this.opcolor[0] + "\n");
		str.append("Opcolor_g: " + this.opcolor[1] + "\n");
		str.append("opcolor_b: " + this.opcolor[2] + "\n");
		
		return str.toString();
	}
	
}
