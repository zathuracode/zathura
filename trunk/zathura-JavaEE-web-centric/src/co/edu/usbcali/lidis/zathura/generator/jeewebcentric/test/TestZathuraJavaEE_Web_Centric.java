package co.edu.usbcali.lidis.zathura.generator.jeewebcentric.test;

import java.util.Properties;

import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.generator.factory.ZathuraGeneratorFactory;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;

public class TestZathuraJavaEE_Web_Centric {

	// private static Logger log =
	// Logger.getLogger(TestZathuraJavaEE_Web_Centric.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "co.edu.usbcali.factorypremium.modelo";
			// String projectName = "factorypremium1";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			// String webRootFolderPath = "E:/ZATHURA EXAMPLE(landed)/webRoot/";
			// String libFolderPath = "E:/ZATHURA EXAMPLE(landed)/lib/";

//			String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
//			String jpaPckgName = "org.cgiar.ciat.smta.model.pojos";
//			String projectName = "smta";
//			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
//			String webRootFolderPath = "E:/ZATHURA EXAMPLE(landed)/webRoot/";
//			String libFolderPath = "E:/ZATHURA EXAMPLE(landed)/lib/";
			
//			String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
//			String jpaPckgName = "org.cgiar.ciat.hpcontactlist.domain.pojos";
//			String projectName = "hpcontactlist";
//			String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/hpcontactlist/";
//			String webRootFolderPath = "E:/ZATHURA EXAMPLE(landed)/hpcontactlist/webRoot/";
//			String libFolderPath = "E:/ZATHURA EXAMPLE(landed)/hpcontactlist/webRoot/lib/";	
			
			String jpaPath = "E:/WORKSPACE/banco/WebRoot/WEB-INF/classes/";
			String jpaPckgName = "co.edu.usb.banco.domain";
			String projectName = "banco";
			String folderProjectPath = "E:/WORKSPACE/banco/src/";
			String webRootFolderPath = "E:/WORKSPACE/banco/WebRoot/";
			String libFolderPath = "E:/WORKSPACE/banco/WebRoot/lib/";	
			
//			String jpaPath = "E:/WORKSPACE/sigyweb1/WebRoot/WEB-INF/classes/";
//			String jpaPckgName = "org.cgiar.ciat.sigyweb1.domain.pojos";
//			String projectName = "sigyweb1";
//			String folderProjectPath = "E:/WORKSPACE/sigyweb1/src/";
//			String webRootFolderPath = "E:/WORKSPACE/sigyweb1/WebRoot/";
//			String libFolderPath = "E:/WORKSPACE/sigyweb1/WebRoot/lib/";					

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "org.cgiar.hplus.dominio.pojos";
			// String projectName = "students";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			// String webRootFolderPath="E:/ZATHURA EXAMPLE(landed)/webRoot/";
			// String libFolderPath="E:/ZATHURA EXAMPLE(landed)/lib/";

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "co.edu.usbcali.mascotas.domain";
			// String projectName = "mascotas";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			// String webRootFolderPath="E:/ZATHURA EXAMPLE(landed)/webRoot/";
			// String libFolderPath="E:/ZATHURA EXAMPLE(landed)/lib/";

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "org.cgiar.ciat.reb.model.pojos";
			// String projectName = "reb1";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			// String webRootFolderPath="E:/ZATHURA EXAMPLE(landed)/webRoot/";
			// String libFolderPath="E:/ZATHURA EXAMPLE(landed)/lib/";

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName =
			// "org.cgiar.ciat.cassavaregistry.domain.pojos";
			// String projectName = "cassavaregistry";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			// String webRootFolderPath="E:/ZATHURA EXAMPLE(landed)/webRoot/";
			// String libFolderPath="E:/ZATHURA EXAMPLE(landed)/lib/";

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "org.cgiar.ciat.page.domain.pojos";
			// String projectName = "page1";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";
			// String webRootFolderPath="E:/ZATHURA EXAMPLE(landed)/webRoot/";
			// String libFolderPath="E:/ZATHURA EXAMPLE(landed)/lib/";
			
			
			
			int specificityLevel = 1;

			IMetaDataReader entityLoader = null;
			MetaDataModel metaDataModel = null;
			try {
				entityLoader = MetaDataReaderFactory
						.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
				metaDataModel = entityLoader.loadMetaDataModel(jpaPath,
						jpaPckgName);
			} catch (MetaDataReaderNotFoundException e) {
				e.printStackTrace();
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
					.createZathuraGenerator("ZathuraJavaEE_Web_Centric");
			zathuraGenerator.toGenerate(metaDataModel, projectName,
					folderProjectPath, properties);

			// Todos los generadores
			/*
			 * for(GeneratorModel
			 * generatorModel:ZathuraGeneratorFactory.getTheZathuraGenerators().values()){
			 * log.debug(generatorModel.getName());
			 * log.debug(generatorModel.getZathuraGenerator()); }
			 */

		} catch (GeneratorNotFoundException e) {
			e.printStackTrace();
		}
	}

}
