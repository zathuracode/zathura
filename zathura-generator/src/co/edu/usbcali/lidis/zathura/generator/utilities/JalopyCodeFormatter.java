package co.edu.usbcali.lidis.zathura.generator.utilities;

import java.io.File;

import org.apache.log4j.Logger;

import de.hunsicker.jalopy.Jalopy;

/**
 * 
 * @author Diego Armando Gomez Mosquera
 *
 */
public class JalopyCodeFormatter {
	
	private static Logger log=Logger.getLogger(JalopyCodeFormatter.class);

	private JalopyCodeFormatter() {
		
	}
	
	/**
	 * 
	 * @param pathFolder
	 */
	public static void formatJavaCodeFolder(String pathFolder) {
		log.info("formatJavaCodeFolder");
		try {
			Jalopy jalopy = new Jalopy();
			File directory = new File(pathFolder);
			String[] fileNames = directory.list();

			for (int i = 0; i < fileNames.length; i++) {
				if(fileNames[i].endsWith(".java")){				
					File in = new File(pathFolder + fileNames[i]);
					jalopy.setInput(in);
					jalopy.setOutput(in);
	
					jalopy.format();
					
					if (jalopy.getState() == Jalopy.State.OK) {
						log.info(in + " format OK");
					} else if (jalopy.getState() == Jalopy.State.WARN) {
						log.info(in + " format WARN");
					} else if (jalopy.getState() == Jalopy.State.ERROR) {
						log.info(in + " format ERROR");
					}
					
					in = null;
					System.gc();
				}
			}
		} catch (Exception e) {
			System.out.println(
				"Fallo Formateo de Codigo con Jalopy:" + e.toString());
		}
	}
	/**
	 * 
	 * @param pathFiles
	 */
	public static void formatJavaCodeFile(String pathFiles) {
		log.info("formatJavaCodeFile");
		Jalopy jalopy = new Jalopy();
		try {
			if(pathFiles.endsWith(".java")){			
				File in = new File(pathFiles);
				jalopy.setInput(in);
				jalopy.setOutput(in);
	
				jalopy.format();
	
				if (jalopy.getState() == Jalopy.State.OK) {
					log.info(in + " format OK");
				} else if (jalopy.getState() == Jalopy.State.WARN) {
					log.info(in + " format WARN");
				} else if (jalopy.getState() == Jalopy.State.ERROR) {
					log.info(in + " format ERROR");
				}
				
				jalopy = null;
				in = null;
				System.gc();
			}
		} catch (Exception e) {
			System.out.println(
				"Fallo Formateo de Codigo con Jalopy:" + e.toString());
		}
	}

	

}
