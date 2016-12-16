package edu.stevens.canvas.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class decompress {
	public File hw = new File(
			"/Users/Steveisno1/Documents/16-17Fall/EE810Java/" + "Final Project/ZIPRULE/src/sample files.zip");
	public int stuID = 0; // get student information
	public String stuName = null;
	public String stuEmail = null;
	/* Please change the path to your own file path */
	public String inPath = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/"
			+ "Final Project/ZIPRULE/src/sample files.zip";
	public String outPath = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/" + "Final Project/ZIPRULE/src";

	public decompress(int id, String nm, String em, String hw, String fileRoot) throws Exception {
		/* These information are from other groups... */
		stuID = id;
		stuName = nm;
		stuEmail = em;
		inPath = fileRoot + "/" + hw + ".zip";
		outPath = fileRoot;
		this.hw = new File(inPath);
		// uncompress the zip file
		deCom(inPath, outPath);
	}

	// zip file decompression
	// problem about this part: cannot see file after decompression

	// Do we need to add a [Buffer_Size] here?
	public void deCom(String inPath, String outPath) {
		try {
			ZipInputStream zipInput = new ZipInputStream(new FileInputStream(inPath));
			BufferedInputStream bufInput = new BufferedInputStream(zipInput);
			BufferedOutputStream bufOutput = null;
			File outFile = null;
			FileOutputStream fileOut = null;
			ZipEntry zipEntry = null;
			int re = 0;
			try {
				while ((zipEntry = zipInput.getNextEntry()) != null) {
					if (zipEntry.isDirectory()) {

					} else {
						outFile = new File(outPath, zipEntry.getName());
						if (!outFile.exists())/* ?? */ 
							(new File(outFile.getParent())).mkdirs();
						fileOut = new FileOutputStream(outFile);
						bufOutput = new BufferedOutputStream(fileOut);
						while ((re = bufInput.read()) != -1)
							bufOutput.write(re);
						bufOutput.close();
					}
				}
				fileOut.close();
				zipInput.close();
				bufInput.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}