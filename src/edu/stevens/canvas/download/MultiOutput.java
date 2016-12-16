package Download;

import java.io.IOException;
import java.io.OutputStream;

/*The function of this class shows the content of console to a file of txt
 * and we can also see the content from the console at the same time
 * In this function,I override the write method
 * */
class MultiOutput extends OutputStream{
	
	 OutputStream output1;	 
	 OutputStream output2;	
	 public MultiOutput(OutputStream output1,OutputStream output2){
	        this.output1 = output1;
	        this.output2 = output2;
	     }
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		output1.write(b);
		output2.write(b);
	}
}