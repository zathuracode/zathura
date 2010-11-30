package co.edu.usbcali.lidis.zathura.generator.jee.hibernatecore.webcentric.test;

import java.util.Properties;

import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.generator.factory.ZathuraGeneratorFactory;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;


/**
 * Zathura Generator
 * @author William Altuzarra (williamaltu@gmail.com)
 * @version 1.0
 */
public class TestZathuraJavaEE_HibernateCore_Web_Centric {
	
	//private static Logger log = Logger.getLogger(TestZathuraJavaEE_Web_Centric.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		try {
			
		
//			String jpaPath = "E:/WORK(New)/hpcontactlist/WebRoot/WEB-INF/classes/";
//			String jpaPckgName = "org.cgiar.ciat.hpcontactlist.domain.pojos";			
//			String projectName = "hpcontactlist";
//			String folderProjectPath = "E:/WORK(New)/hpcontactlist/src/";
//			String webRootFolderPath="E:/WORK(New)/hpcontactlist/WebRoot/";
//			String libFolderPath="E:/WORK(New)/hpcontactlist/WebRoot/lib/";
			
//			String jpaPath = "E:/WORK(New)/tlpj/WebRoot/WEB-INF/classes/";
//			String jpaPckgName = "com.tlp.tlpj.domain.pojos";			
//			String projectName = "tlpj";
//			String folderProjectPath = "E:/WORK(New)/tlpj/src/";
//			String webRootFolderPath="E:/WORK(New)/tlpj/WebRoot/";
//			String libFolderPath="E:/WORK(New)/tlpj/WebRoot/lib/";
			
//			String jpaPath = "E:/WORK(New)/tlpj/WebRoot/WEB-INF/classes/";
//			String jpaPckgName = "com.tlp.tlpj.domain.pojos";			
//			String projectName = "tlpj";
//			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/tlpj/";
//			String webRootFolderPath="E:/ZATHURA EXAMPLE(landed)/tlpj/WebRoot/";
//			String libFolderPath="E:/ZATHURA EXAMPLE(landed)/tlpj/WebRoot/lib/";	
			
//			String jpaPath = "E:/WORK(New)/pa2/WebRoot/WEB-INF/classes/";
//			String jpaPckgName = "org.cgiar.ciat.pa.domain";			
//			String projectName = "pa";
//			String folderProjectPath = "E:/WORK(New)/pa/src/";
//			String webRootFolderPath="E:/WORK(New)/pa/WebRoot/";
//			String libFolderPath="E:/WORK(New)/pa/WebRoot/WEB-INF/lib/";	
			
			String jpaPath = "E:/WORK(New)/banco2/WebRoot/WEB-INF/classes/";
			String jpaPckgName = "co.edu.usb.banco.domain";			
			String projectName = "banco";
			String folderProjectPath = "E:/WORK(New)/banco/src/";
			String webRootFolderPath="E:/WORK(New)/banco/WebRoot/";
			String libFolderPath="E:/WORK(New)/banco/WebRoot/WEB-INF/lib/";				
			 
			///home/Diego Armando Gomez Mosquera/Workspaces/runtime-EclipseApplication/demoBanco
//			String jpaPath = "/home/Diego Armando Gomez Mosquera/Workspaces/runtime-EclipseApplication/demoBanco/build/classes/";
//			String jpaPckgName = "co.edu.usbcali.banco.modelo";			
//			String projectName = "demoBanco";
//			String folderProjectPath = "/home/Diego Armando Gomez Mosquera/Workspaces/runtime-EclipseApplication/demoBanco/src/";
//			String webRootFolderPath="/home/Diego Armando Gomez Mosquera/Workspaces/runtime-EclipseApplication/demoBanco/WebContent/";
//			String libFolderPath="/home/Diego Armando Gomez Mosquera/Workspaces/runtime-EclipseApplication/demoBanco/WebContent/WEB-INF/lib/";
			
			//
			
			int specificityLevel = 1;

			IMetaDataReader entityLoader=null;
			MetaDataModel metaDataModel=null;
			try {
				entityLoader = MetaDataReaderFactory.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
				metaDataModel = entityLoader.loadMetaDataModel(jpaPath,jpaPckgName);
			} catch (MetaDataReaderNotFoundException e) {
				e.printStackTrace();
			}

		
			//Variables para el properties
			Properties properties=new Properties();
			properties.put("jpaPath",jpaPath);
			properties.put("jpaPckgName",jpaPckgName);
			properties.put("specificityLevel", new Integer(specificityLevel));
			properties.put("webRootFolderPath", webRootFolderPath);
			properties.put("libFolderPath", libFolderPath);
			properties.put("folderProjectPath", folderProjectPath);
			
			
			
			IZathuraGenerator zathuraGenerator=ZathuraGeneratorFactory.createZathuraGenerator("ZathuraJavaEE_Web_Centric");
			zathuraGenerator.toGenerate(metaDataModel,projectName, folderProjectPath,properties);
		
			//Todos los generadores
			/*
			for(GeneratorModel generatorModel:ZathuraGeneratorFactory.getTheZathuraGenerators().values()){
				log.debug(generatorModel.getName());
				log.debug(generatorModel.getZathuraGenerator());
			}
			*/
			
		} catch (GeneratorNotFoundException e) {
			e.printStackTrace();
		}
	}

}
