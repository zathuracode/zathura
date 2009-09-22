package co.edu.usbcali.lidis.zathura.eclipse.utilities;

import java.util.Properties;

import org.eclipse.core.resources.IProject;

import co.edu.usbcali.lidis.zathura.eclipse.gui.WizardMain;
import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.generator.factory.ZathuraGeneratorFactory;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;


public class EclipseUtil {
	
	private EclipseUtil() {

	}
	
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
	
	public static WizardMain wizardMain;
	
	
	/**
	 * Se usa para generar cuando termina el wizard
	 * @throws MetaDataReaderNotFoundException
	 * @throws GeneratorNotFoundException
	 */
	public static void generate() throws MetaDataReaderNotFoundException, GeneratorNotFoundException{
		
			/*
			String jpaPath = "/home/diegomez/workspaces/runtime-EclipseApplication/demoJpa/bin/";
			String jpaPckgName = "co.edu.usbcali.lidis.banco.modelo";			
			String projectName = "bancoUSB";
			String folderProjectPath = "/home/diegomez/workspaces/runtime-EclipseApplication/demoJpa/src/";	
			*/
			
			String jpaPath = EclipseUtil.javaClassFolderPath;
			String jpaPckgName = EclipseUtil.javaEntityPackage;		
			String projectName = EclipseUtil.projectName;
			String folderProjectPath = EclipseUtil.javaSourceFolderPath;
			String webRootFolderPath=EclipseUtil.webRootFolderPath;
			String libFolderPath=EclipseUtil.libFolderPath;
		
			
			int specificityLevel = 2;
	
			IMetaDataReader entityLoader=null;
			MetaDataModel metaDataModel=null;
			
				entityLoader = MetaDataReaderFactory.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
				metaDataModel = entityLoader.loadMetaDataModel(jpaPath,jpaPckgName);
			
			
			//Variables para el properties
			Properties properties=new Properties();
			properties.put("jpaPath",jpaPath);
			properties.put("jpaPckgName",jpaPckgName);
			properties.put("specificityLevel", new Integer(specificityLevel));
			properties.put("webRootFolderPath", webRootFolderPath);
			properties.put("libFolderPath", libFolderPath);
			properties.put("folderProjectPath", folderProjectPath);
			
			
			IZathuraGenerator zathuraGenerator=ZathuraGeneratorFactory.createZathuraGenerator(EclipseUtil.zathuraGeneratorName);
			zathuraGenerator.toGenerate(metaDataModel,projectName, folderProjectPath,properties);
			
			
		
	}

}
