import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {
	
	
	
	private static final int BUFFER_SIZE = 4096;

	public void unzip(String zipFilePath, String destDirectory) throws IOException{
		File destDir = new File(destDirectory);
		if(!destDir.exists()){
			destDir.mkdirs();
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		
		while(entry !=null){
			String filePath = destDirectory + File.separator + entry.getName();
			if(!entry.isDirectory()){
				extractFile(zipIn,filePath);
			}else{
				File dir = new File(filePath);
				dir.mkdirs();
			}
			zipIn.closeEntry();
			}
		zipIn.close();
		}

	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath)); 
		// TODO Auto-generated method stub
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while((read = zipIn.read(bytesIn)) !=-1){
			bos.write(bytesIn,0,read);
		}
		bos.close();
	}
}
	


