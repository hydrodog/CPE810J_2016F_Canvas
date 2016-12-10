package compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class RuntimeTest {
	public static void main(String[] args) {
		
		try {
			//String[] s = {"vcvarsall.bat","e:","cl/EHscHelloWorld.cpp"};
			Runtime.getRuntime().exec("vcvarsall.bat").waitFor();
			
			Runtime.getRuntime().exec("cl/EHscHelloWorld.cpp").waitFor();
		;
			
			String line;
			System.out.println("yes");
			
			/*BufferedReader br = new BufferedReader(new InputStreamReader(p1.getErrorStream()));
			BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			while ( (line = br.readLine()) != null)
			     System.out.println(line);
			System.out.println("fail");
			
			while ( (line = br1.readLine()) != null)
			     System.out.println(line);
			System.out.println("fail");
			br.close();*/
         System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
