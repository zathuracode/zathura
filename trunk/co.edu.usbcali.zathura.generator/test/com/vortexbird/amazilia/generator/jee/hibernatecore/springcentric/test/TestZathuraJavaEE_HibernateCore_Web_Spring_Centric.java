package com.vortexbird.amazilia.generator.jee.hibernatecore.springcentric.test;

import java.util.Properties;

import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.generator.factory.ZathuraGeneratorFactory;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;

public class TestZathuraJavaEE_HibernateCore_Web_Spring_Centric {
	
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
			
			
			String jpaPath = "D:/Workspaces/runtime-EclipseApplication/demoBancoSpring/WebContent/WEB-INF/classes/";
			String jpaPckgName = "com.vortexbird.banco.modelo";
			String projectName = "demoBancoSpring";
			String folderProjectPath = "D:/Workspaces/runtime-EclipseApplication/demoBancoSpring/src/";
			String webRootFolderPath = "D:/Workspaces/runtime-EclipseApplication/demoBancoSpring/WebContent/";
			String libFolderPath = "D:/Workspaces/runtime-EclipseApplication/demoBancoSpring/WebContent/WEB-INF/lib/";
			String domainName = "com.vortexbird.banco";
			 
			///home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco
//			String jpaPath = "/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/build/classes/";
//			String jpaPckgName = "co.edu.usbcali.banco.modelo";			
//			String projectName = "demoBanco";
//			String folderProjectPath = "/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/src/";
//			String webRootFolderPath="/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/WebContent/";
//			String libFolderPath="/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/WebContent/WEB-INF/lib/";
			
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
			properties.put("domainName", domainName);
			
			IZathuraGenerator zathuraGenerator=ZathuraGeneratorFactory.createZathuraGenerator("ZathuraJavaEE_HibernateCore_Spring_Web_Centric");
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
