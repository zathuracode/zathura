package org.zathuracode.generator.jee.jpa.springcentric.test;

import java.util.Properties;

import org.zathuracode.generator.factory.ZathuraGeneratorFactory;
import org.zathuracode.generator.model.IZathuraGenerator;
import org.zathuracode.metadata.exceptions.MetaDataReaderNotFoundException;
import org.zathuracode.metadata.model.MetaDataModel;
import org.zathuracode.metadata.reader.IMetaDataReader;
import org.zathuracode.metadata.reader.MetaDataReaderFactory;


public class TestZathuraJavaEE_JpaCore_Web_Centric {

	
	public static void main(String[] args) {
		
		
		try {
			
			
			String jpaPath = "/home/mauricio/Workspaces/runtime-New_configuration(1)/Demo/WebRoot/WEB-INF/classes/";
			String jpaPckgName = "entity";
			String projectName = "Demo";
			String folderProjectPath = "/home/mauricio/Workspaces/runtime-New_configuration(1)/Demo/src";
			String webRootFolderPath = "/home/mauricio/Workspaces/runtime-New_configuration(1)/Demo/WebRoot";
			String libFolderPath = "/home/mauricio/Workspaces/runtime-New_configuration(1)/Demo/WebRoot/WEB-INF/lib";

			int specificityLevel = 1;

			IMetaDataReader entityLoader = null;
			MetaDataModel metaDataModel = null;
			
			try {
				entityLoader = MetaDataReaderFactory.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
				metaDataModel = entityLoader.loadMetaDataModel(jpaPath, jpaPckgName);
			} catch (MetaDataReaderNotFoundException e) {
				e.printStackTrace();
			}

			
			Properties properties = new Properties();
			properties.put("jpaPath", jpaPath);
			properties.put("jpaPckgName", jpaPckgName);
			properties.put("specificityLevel", new Integer(specificityLevel));
			properties.put("webRootFolderPath", webRootFolderPath);
			properties.put("libFolderPath", libFolderPath);
			properties.put("folderProjectPath", folderProjectPath);

			IZathuraGenerator zathuraGenerator = ZathuraGeneratorFactory.createZathuraGenerator("ZathuraJavaEE__Jpa_Spring_Web_Centric");
			zathuraGenerator.toGenerate(metaDataModel, projectName, folderProjectPath, properties);

			
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			
		}
		
		
	}
	
	
	
}
