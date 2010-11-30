package co.edu.usbcali.lidis.zathura.generator.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Zathura Generator
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 */
public class GeneratorUtil {
	
	
	private static Logger log = Logger.getLogger(GeneratorUtil.class);
	
	public static String slash = System.getProperty("file.separator");
	public static String FileName="zathura-generator-factory-config.xml";
	
	private static String fullPath="";
	private static String xmlConfigFactoryPath ="config" + slash + FileName;
	
	
	//JavaEEJPAWebCentric
	private static String webCentricTemplates = "generatorTemplates"+ GeneratorUtil.slash + "zathura-JavaEE-Web-Centric"+ GeneratorUtil.slash;
	private static String generatorLibrariesZathuraJavaEEWebCentric="generatorLibraries"+ GeneratorUtil.slash + "zathura-JavaEE-Web-Centric"+ GeneratorUtil.slash;
	private static String generatorExtZathuraJavaEEWebCentric="generatorExt"+ GeneratorUtil.slash + "zathura-JavaEE-Web-Centric"+ GeneratorUtil.slash;
	
	//JavaEE-HibernateCore-Spring-WebCentric
	private static String springCentricTemplates = "generatorTemplates"+ GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Spring-Centric"+ GeneratorUtil.slash;
	private static String generatorLibrariesZathuraJavaEESpringWebCentric="generatorLibraries"+ GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Spring-Centric"+ GeneratorUtil.slash;
	private static String generatorExtZathuraJavaEESpringWebCentric="generatorExt"+ GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Spring-Centric"+ GeneratorUtil.slash;	
	
	//JavaEE-HibernateCore-WebCentric
	private static String templatesZathuraJavaEEHibernateCoreWebCentric = "generatorTemplates"+ GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Web-Centric"+ GeneratorUtil.slash;
	private static String generatorLibrariesZathuraJavaEEHibernateCoreWebCentric = "generatorLibraries"+ GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Web-Centric"+ GeneratorUtil.slash;
	private static String generatorExtZathuraJavaEEHibernateCoreWebCentric = "generatorExt"+ GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Web-Centric"+ GeneratorUtil.slash;
	
	//JavaEEGwtCentric
	private static String gwtCentricTemplates = "generatorTemplates"+ GeneratorUtil.slash + "zathura-JavaEE-GWT-Centric"+ GeneratorUtil.slash;
	private static String generatorLibrariesZathuraJavaEEGwtCentric="generatorLibraries"+ GeneratorUtil.slash + "zathura-JavaEE-GWT-Centric"+ GeneratorUtil.slash;
	private static String generatorExtZathuraJavaEEGwtCentric="generatorExt"+ GeneratorUtil.slash + "zathura-JavaEE-GWT-Centric"+ GeneratorUtil.slash;
		
	
	
	public static String getFullPath() {
		return fullPath;
	}
	public static void setFullPath(String fullPath) {
		//System.err.println(fullPath);
		if(fullPath!=null && fullPath.startsWith("/") && System.getProperty("os.name").toUpperCase().contains("WINDOWS")==true){
			fullPath=fullPath.substring(1, fullPath.length());
		}
		
		if(fullPath!=null){
			if(slash.equals("/")){
				fullPath=replaceAll(fullPath, "\\", slash);
				if(fullPath.endsWith("\\")==true){
					fullPath=fullPath.substring(0, fullPath.length()-1);
					fullPath=fullPath+slash;
				}
				
			}else if(slash.equals("\\")){
				fullPath=replaceAll(fullPath, "/", slash);
				if(fullPath.endsWith("/")==true){
					fullPath=fullPath.substring(0, fullPath.length()-1);
					fullPath=fullPath+slash;
				}
			}
		}
		//System.err.println(fullPath);
		
		GeneratorUtil.fullPath = fullPath;
	}
	public static String getXmlConfigFactoryPath() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+xmlConfigFactoryPath;
		}
		return xmlConfigFactoryPath;
	}
	
	
	
	//JavaEEWebCentric
	public static String getGeneratorLibrariesZathuraJavaEEWebCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+generatorLibrariesZathuraJavaEEWebCentric;
		}
		return generatorLibrariesZathuraJavaEEWebCentric;
	}
	public static String getGeneratorExtZathuraJavaEEWebCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+generatorExtZathuraJavaEEWebCentric;
		}
		return generatorExtZathuraJavaEEWebCentric;
	}	
	public static String getWebCentricTemplates() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+webCentricTemplates;
		}
		return webCentricTemplates;
	}
	
	//JavaEESpringWebCentric
	public static String getGeneratorLibrariesZathuraJavaEESpringWebCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+generatorLibrariesZathuraJavaEESpringWebCentric;
		}
		return generatorLibrariesZathuraJavaEESpringWebCentric;
	}
	public static String getGeneratorExtZathuraJavaEESpringWebCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+generatorExtZathuraJavaEESpringWebCentric;
		}
		return generatorExtZathuraJavaEESpringWebCentric;
	}	
	
	public static String getSpringWebCentricTemplates() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+springCentricTemplates;
		}
		return springCentricTemplates;
	}	
	
	//JavaEEWebCentric
	public static String getTemplatesZathuraJavaEEHibernateCoreWebCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+templatesZathuraJavaEEHibernateCoreWebCentric;
		}
		return templatesZathuraJavaEEHibernateCoreWebCentric;
	}
	public static String getGeneratorLibrariesZathuraJavaEEHibernateCoreWebCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+generatorLibrariesZathuraJavaEEHibernateCoreWebCentric;
		}
		return generatorLibrariesZathuraJavaEEHibernateCoreWebCentric;
	}
	public static String getGeneratorExtZathuraJavaEEHibernateCoreWebCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+generatorExtZathuraJavaEEHibernateCoreWebCentric;
		}
		return generatorExtZathuraJavaEEHibernateCoreWebCentric;
	}
	
	//JavaEEGwtCentric
	public static String getGeneratorLibrariesZathuraJavaEEGwtCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+generatorLibrariesZathuraJavaEEGwtCentric;
		}
		return generatorLibrariesZathuraJavaEEGwtCentric;
	}
	
	public static String getGeneratorExtZathuraJavaEEGwtCentric() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+generatorExtZathuraJavaEEGwtCentric;
		}
		return generatorExtZathuraJavaEEGwtCentric;
	}	
	public static String getGwtCentricTemplates() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+gwtCentricTemplates;
		}
		return gwtCentricTemplates;
	}
	
	/**
	 * 
	 * @param path
	 * @param nameFolder
	 * @return
	 */
	public static File createFolder(String path){	 	
		File aFile  = new File(path);
		aFile.mkdirs();
		return aFile;
	}
	/**
	 * 
	 * @param source
	 * @param Target
	 */
	public static void copy(String source, String Target) {
		FileInputStream fIn = null;
		FileOutputStream fOut = null;
		byte[] b;
		int l = 0;
		try {
			fIn = new FileInputStream(source);
			fOut = new FileOutputStream(Target);
			b = new byte[1024];
			while ((l = fIn.read(b)) > 0) {
				fOut.write(b, 0, l);
			}
			fOut.close();
			fIn.close();
		} catch (FileNotFoundException fnfe) {
			log.error(fnfe.toString());
		} catch (IOException ioe) {
			log.error(ioe.toString());
		}
	}
	/**
	 * 
	 * @param path
	 */
	public static void deleteFiles(String path) {
		File file=new File(path);
		deleteFiles(file);
	}
	/**
	 * 
	 * @param file
	 */
	public static void deleteFiles(File file) {
		File fileAux = null;
		File listFiles[] = null;
		int iPos = -1;

		listFiles = file.listFiles();
		for(iPos = 0; iPos < listFiles.length; iPos++){
			fileAux = listFiles[iPos];
			if(fileAux.isDirectory())
				deleteFiles(listFiles[iPos]);
			listFiles[iPos].delete();
		}
		if(file.listFiles().length == 0)
			file.delete();		
	}
	/**
	 * 
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file=new File(path);
		deleteFile(file);		
	}
	/**
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		file.delete();		
	}
	
	/**
	 * public static List<String> copyFolder(String source,String target){
	 	List<String> filesLib =new ArrayList<String>();
	 	try {
			File dir = new File(source);
			String[] fileNames = dir.list();			
			for (int i = 0; i < fileNames.length; i++) {
				String s=fileNames[i];
				copy(source+s,target+s);
				filesLib.add(s);		
			}
			return filesLib;			
		} catch (Exception e) {
			//TODO Poner log4j
			System.out.println("Error copy all files of folder:"+e.toString());
		}
		return null;
	 }
	 * @param source
	 * @param target
	 */
	public static void copyFolder(String source,String target){
	 	try {
			File dir = new File(source);
			File[] listFiles = dir.listFiles();
			File fileSource=null;
			for (int i = 0; i < listFiles.length; i++) {
				fileSource=listFiles[i];				
				if(fileSource.isDirectory()){
					log.debug(fileSource.getName());					
					log.debug("Source:"+fileSource.getAbsolutePath());
					log.debug("Target:"+target+fileSource.getName());
					createFolder(target+fileSource.getName());
					copyFolder(fileSource.getAbsolutePath()+slash, target+fileSource.getName()+slash);
				}else{
					copy(fileSource.getAbsolutePath(),target+fileSource.getName());
					log.debug(fileSource);
				}					
			}
						
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	 }
	/**
	 * File fileAux = null;
		File listFiles[] = null;
		int iPos = -1;

		listFiles = file.listFiles();
		for(iPos = 0; iPos < listFiles.length; iPos++){
			fileAux = listFiles[iPos];
			if(fileAux.isDirectory())
				deleteFiles(listFiles[iPos]);
			listFiles[iPos].delete();
		}
		if(file.listFiles().length == 0)
			file.delete();	
	 */
	
	
	public static String createFolderOfPackage(String path,String pck){
		try {
			path=path+replaceAll(pck,".",File.separator);
			path=path+File.separator;
			File myDirectory=new File(path);
			myDirectory.mkdirs();
			return path;
		} catch (Exception e) {
			log.error("Fallo creacion de carpetas para los paquetes:"+e.toString());
		}	 	
		return null;
	}
	
	
	/**
	 * 
	 * @param packageName
	 * @param location
	 * @return
	 * @throws IOException
	 */
	public static boolean validateDirectory(String packageName, String location) 
	throws IOException{

		if(location==null || location.equals("")==true)
			return false;
		
		File dirComm = new File(location);

		if (dirComm.exists()){
			String dirsToCreate[] = packageName.split("_");

			for (int i = 0; i < dirsToCreate.length; i++) {
				location = location+slash+dirsToCreate[i];
				dirComm = new File(location);

				if (!dirComm.exists()){
					dirComm.mkdir();
				}
			}
		}
		return true;
	}	
	
	/**
	 * 
	 * @param cadena
	 * @param old
	 * @param snew
	 * @return
	 */
	public static String replaceAll (String cadena,String old,String snew) {
		StringBuffer replace= new StringBuffer();
		String aux;
	
		for (int i = 0; i < cadena.length(); i++) {
			if ( (i+old.length()) <cadena.length() ){
				aux =  cadena.substring(i,i+old.length());
				if (aux.equals(old)){
					replace.append(snew);
					i+=old.length()-1;
				}else{
					replace.append(cadena.substring(i,i+1));
				}
			}else
				replace.append(cadena.substring(i,i+1));
		}
		return replace.toString();
}

}
