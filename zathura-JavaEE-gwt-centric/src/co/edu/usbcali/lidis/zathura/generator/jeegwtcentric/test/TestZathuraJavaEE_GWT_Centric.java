package co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.test;

import java.util.Properties;

import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.generator.factory.ZathuraGeneratorFactory;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;

public class TestZathuraJavaEE_GWT_Centric {
	
	//private static Logger log = Logger.getLogger(TestZathuraJavaEE_Web_Centric.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		try {
			
		
//			String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
//			String jpaPckgName = "co.edu.usbcali.lidis.mascotas.model.pojos";
//			String projectName = "zathuramascotas";
//			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			
//			String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
//			String jpaPckgName = "org.cgiar.ciat.smta.model.pojos";
//			String projectName = "smtaLogic";
//			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			
//			String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
//			String jpaPckgName = "org.cgiar.ciat.reb.model.pojos";
//			String projectName = "reb1";
//			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			
//			String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
//			String jpaPckgName = "co.edu.usbcali.banco.modelo";
//			String projectName = "banco";
//			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			
//			String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
//			String jpaPckgName = "org.cgiar.ciat.sigy.model.pojos";
//			String projectName = "sigyweb1";
//			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			
			/*
			String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			String jpaPckgName = "org.cgiar.ciat.ceres.model.pojos";
			String projectName = "ceres1";
			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			*/
			/*
			String jpaPath = "C:\\workspaces\\workspaceGanymedeJEE\\demoJpa\\build\\classes\\";
			String jpaPckgName = "co.edu.usbcali.lidis.banco.modelo";			
			String projectName = "demoJpa";
			String folderProjectPath = "C:\\workspaces\\workspaceGanymedeJEE\\demoJpa\\src\\";
			String webRootFolderPath="C:\\workspaces\\workspaceGanymedeJEE\\demoJpa\\WebContent\\";
			String libFolderPath="C:\\workspaces\\workspaceGanymedeJEE\\demoJpa\\WebContent\\WEB-INF\\lib\\";
			 */
			///home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco
			String jpaPath = "/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/build/classes/";
			String jpaPckgName = "co.edu.usbcali.banco.modelo";			
			String projectName = "demoBanco";
			String folderProjectPath = "/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/src/";
			String webRootFolderPath="/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/WebContent/";
			String libFolderPath="/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/WebContent/WEB-INF/lib/";
			
			//
			
			int specificityLevel = 2;

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
