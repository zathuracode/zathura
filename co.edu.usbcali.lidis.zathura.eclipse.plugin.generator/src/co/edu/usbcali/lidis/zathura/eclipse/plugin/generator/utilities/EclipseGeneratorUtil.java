package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui.WizardMain;
import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.generator.factory.ZathuraGeneratorFactory;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;
import co.edu.usbcali.lidis.zathura.reverse.engine.IZathuraJPAReverseEngineering;
import co.edu.usbcali.lidis.zathura.reverse.engine.ZathuraJPAReverseEngineering;
import co.edu.usbcali.lidis.zathura.reverse.utilities.ReverseEngineeringUtil;

public class EclipseGeneratorUtil {

	

	public static IProject project;
	public static String projectName;
	public static String javaEntityPackage;

	public static String workspaceFolderPath;
	public static String javaClassFolderPath;
	public static String javaSourceFolderPath;
	public static String webRootFolderPath;
	public static String libFolderPath;
	public static String fullPathProject;

	public static String zathuraGeneratorName;
	public static int metaDataReader;
	
	public static String connectionDriverClass;
	public static String connectionUrl;
	public static String defaultSchema;
	public static String connectionUsername;
	public static String connectionPassword;
	public static String companyDomainName;
	public static String connectionDriverJarPath;
	public static String destinationDirectory;
	public static String matchSchemaForTables;
	public static List<String> tablesList;
	public static Boolean makeItXml = false;
	private static MetaDataModel metaDataModel = null;
	
	

	public static WizardMain wizardMain;
	
	public static void reset(){
		project=null;
		projectName=null;

		javaEntityPackage=null;

		workspaceFolderPath=null;
		javaClassFolderPath=null;
		javaSourceFolderPath=null;
		webRootFolderPath=null;
		libFolderPath=null;
		fullPathProject=null;

		zathuraGeneratorName=null;
		metaDataReader=0;
		
		
		connectionDriverClass=null;
		connectionUrl=null;
		defaultSchema=null;
		connectionUsername=null;
		connectionPassword=null;
		companyDomainName=null;
		connectionDriverJarPath=null;
		destinationDirectory=null;
		matchSchemaForTables=null;
		tablesList=null;
		makeItXml = false;
	}

	/**
	 * Se usa para generar cuando termina el wizard
	 * 
	 * @throws MetaDataReaderNotFoundException
	 * @throws GeneratorNotFoundException
	 */
	public static void generate() throws MetaDataReaderNotFoundException,
			GeneratorNotFoundException {


		EclipseGeneratorUtil.metaDataReader=MetaDataReaderFactory.JPAEntityLoaderEngine;
		String jpaPath = EclipseGeneratorUtil.javaClassFolderPath;
		String jpaPckgName = EclipseGeneratorUtil.javaEntityPackage;
		String projectName = EclipseGeneratorUtil.projectName;
		String folderProjectPath = EclipseGeneratorUtil.javaSourceFolderPath;
		String webRootFolderPath = EclipseGeneratorUtil.webRootFolderPath;
		String libFolderPath = EclipseGeneratorUtil.libFolderPath;

		//Para que no corte los nombres de los paquetes
		int specificityLevel = 1;

		
		if(metaDataModel==null){
			IMetaDataReader entityLoader = null;
			entityLoader = MetaDataReaderFactory.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
			metaDataModel = entityLoader.loadMetaDataModel(jpaPath, jpaPckgName);
		}
		

		// Variables para el properties
		Properties properties = new Properties();
		properties.put("jpaPath", jpaPath);
		properties.put("jpaPckgName", jpaPckgName);
		properties.put("specificityLevel", new Integer(specificityLevel));
		properties.put("webRootFolderPath", webRootFolderPath);
		properties.put("libFolderPath", libFolderPath);
		properties.put("folderProjectPath", folderProjectPath);

		IZathuraGenerator zathuraGenerator = ZathuraGeneratorFactory
				.createZathuraGenerator(EclipseGeneratorUtil.zathuraGeneratorName);
		zathuraGenerator.toGenerate(metaDataModel, projectName,
				folderProjectPath, properties);

	}
	public static void validarPackage(String packageName)throws Exception{
		 if(packageName.startsWith(".") || packageName.endsWith(".")){			 
			throw new Exception("A package name cannot start or end with a dot"); 
		 }
	}
	public static void generateJPAReverseEngineering(){
		
		Properties connectionProperties = new Properties();
		
		destinationDirectory=workspaceFolderPath+destinationDirectory+File.separatorChar;
		
		connectionProperties.put("connectionDriverClass", connectionDriverClass);
		connectionProperties.put("connectionUrl", connectionUrl);
		connectionProperties.put("defaultSchema", defaultSchema == null ? "" : defaultSchema);
		connectionProperties.put("connectionUsername", connectionUsername);
		connectionProperties.put("connectionPassword", connectionPassword);
		connectionProperties.put("companyDomainName", companyDomainName);
		connectionProperties.put("matchSchemaForTables", matchSchemaForTables == null ? "" : matchSchemaForTables);
		connectionProperties.put("connectionDriverJarPath",	connectionDriverJarPath);	
		connectionProperties.put("destinationDirectory",destinationDirectory);	
		connectionProperties.put("makeItXml", makeItXml == true ? "True": false);

		//Borrar carpeta de temporales
		GeneratorUtil.deleteFiles(destinationDirectory);
		//Crea carpeta de temporales
		GeneratorUtil.createFolder(destinationDirectory);
		
		IZathuraJPAReverseEngineering mappingTool = new ZathuraJPAReverseEngineering();
		mappingTool.makePojosJPA_V1_0(connectionProperties, tablesList);
		
		
	}
	public static void generateJPAReverseEngineeringTMP(){
		
		
		Properties connectionProperties = new Properties();		
		
		destinationDirectory=ReverseEngineeringUtil.getTempFilesPath();
		
		connectionProperties.put("connectionDriverClass", connectionDriverClass);
		connectionProperties.put("connectionUrl", connectionUrl);
		connectionProperties.put("defaultSchema", defaultSchema == null ? "" : defaultSchema);
		connectionProperties.put("connectionUsername", connectionUsername);
		connectionProperties.put("connectionPassword", connectionPassword);
		connectionProperties.put("companyDomainName", companyDomainName);
		connectionProperties.put("matchSchemaForTables", matchSchemaForTables == null ? "" : matchSchemaForTables);
		connectionProperties.put("connectionDriverJarPath",	connectionDriverJarPath);	
		connectionProperties.put("destinationDirectory",destinationDirectory);	
		connectionProperties.put("makeItXml",false);
		
		//Borrar carpeta de temporales
		GeneratorUtil.deleteFiles(destinationDirectory);
		//Crea carpeta de temporales
		GeneratorUtil.createFolder(destinationDirectory);

		
		IZathuraJPAReverseEngineering mappingTool = new ZathuraJPAReverseEngineering();
		mappingTool.makePojosJPA_V1_0(connectionProperties, tablesList);
		
		
		try {
			EclipseGeneratorUtil.javaClassFolderPath=destinationDirectory;
			IMetaDataReader entityLoader = null;
			entityLoader = MetaDataReaderFactory.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
			metaDataModel = entityLoader.loadMetaDataModel(destinationDirectory, companyDomainName);
			
		} catch (MetaDataReaderNotFoundException e) {
			//Ignore
		}	
	} 

	public static void loadJar(String jarLocation) throws Exception {
		
		//TODO Cargar con class loader del RPC
		
		Method addURL = URLClassLoader.class.getDeclaredMethod("addURL",new Class[] { URL.class });
		addURL.setAccessible(true);// you're telling the JVM to override the
		// default visibility
		File[] files = getExternalJars(jarLocation);// some method returning the
		// jars to add
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		//ClassLoader cl = Thread.currentThread().getContextClassLoader();
		for (int i = 0; i < files.length; i++) {
			URL url = files[i].toURL();
			addURL.invoke(cl, new Object[] { url });
			System.out.println("\n---------------------");
			System.out.println("Summary:");
			System.out.println("\tLoaded:\t" + files[i].getName());
		}
		// at this point, the default class loader has all the jars you
		// indicated
	}

	private static File[] getExternalJars(String jarLocation) {
		File[] files = new File[1];
		files[0] = new File(jarLocation);
		return files;
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
	
	private EclipseGeneratorUtil() {

	}

}
