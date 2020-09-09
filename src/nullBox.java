import java.io.IOException;
import java.io.InputStream;

public class nullBox extends Box {

	nullBox(InputStream stream, int position) throws IOException {
		super(stream, position);
		System.out.println("SIZE null box " + (this.size-8));
		stream.skip(this.size-9);
	}

}
