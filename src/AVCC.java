import java.io.InputStream;

public class AVCC extends Box {

	private int configurationVersion = 1;
	private int AVCProfileIndication;
	private int profile_compatibility;
	private int AVCLevelIndication;
	private int lengthSizeMinusOne;		// 2 bit
	private String bitsReserved_1 = "111";
	private int numOfSequenceParameterSets;
	private int numOfPictureParamterSets;
	
	
	AVCC(MP4Stream stream, int size, String type) throws Exception {
		super(stream, size, type);
		this.configurationVersion = this.readStreamAsInt(stream, 1);
		this.AVCProfileIndication = this.readStreamAsInt(stream, 1);
		this.profile_compatibility = this.readStreamAsInt(stream, 1);
		this.AVCLevelIndication = this.readStreamAsInt(stream, 1);
		this.lengthSizeMinusOne = this.readStreamAsBits(stream, 3);
		this.numOfSequenceParameterSets = this.readStreamAsBits(stream, 31);
		readParameterSets(stream, this.numOfSequenceParameterSets);
		this.numOfPictureParamterSets = this.readStreamAsBits(stream, 255);
		readParameterSets(stream, this.numOfPictureParamterSets);
	}
	
	private void readParameterSets(InputStream stream, int numOfParamterSets) throws Exception {
		for(int i=0; i<numOfParamterSets; i++) {
			int parameterLength = this.readStreamAsInt(stream, 2);
			stream.read(new byte[parameterLength]);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append("Configuration version: " + this.configurationVersion + "\n");
		str.append("AVC profile indication: " + this.AVCProfileIndication + "\n");
		str.append("Profile compatibility: " + this.profile_compatibility + "\n");
		str.append("AVC level indication: " + this.AVCLevelIndication + "\n");
		str.append("LengthSizeMinusOne: " + this.lengthSizeMinusOne + "\n");
		str.append("Number of sequence parameter sets: " + this.numOfSequenceParameterSets + "\n");
		str.append("Number of picture parameter sets: " + this.numOfPictureParamterSets + "\n");
		
		return str.toString();
	}
}
