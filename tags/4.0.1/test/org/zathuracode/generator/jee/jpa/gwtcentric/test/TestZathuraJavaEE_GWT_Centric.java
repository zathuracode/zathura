package org.zathuracode.generator.jee.jpa.gwtcentric.test;

import java.util.Properties;

import org.zathuracode.generator.exceptions.GeneratorNotFoundException;
import org.zathuracode.generator.factory.ZathuraGeneratorFactory;
import org.zathuracode.generator.model.IZathuraGenerator;
import org.zathuracode.metadata.exceptions.MetaDataReaderNotFoundException;
import org.zathuracode.metadata.model.MetaDataModel;
import org.zathuracode.metadata.reader.IMetaDataReader;
import org.zathuracode.metadata.reader.MetaDataReaderFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class TestZathuraJavaEE_GWT_Centric.
 */
public class TestZathuraJavaEE_GWT_Centric {

	// private static Logger log =
	// Logger.getLogger(TestZathuraJavaEE_Web_Centric.class);

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		try {

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "co.edu.usbcali.lidis.mascotas.model.pojos";
			// String projectName = "zathuramascotas";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "org.cgiar.ciat.smta.model.pojos";
			// String projectName = "smtaLogic";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "org.cgiar.ciat.reb.model.pojos";
			// String projectName = "reb1";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "co.edu.usbcali.banco.modelo";
			// String projectName = "banco";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";

			// String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/";
			// String jpaPckgName = "org.cgiar.ciat.sigy.model.pojos";
			// String projectName = "sigyweb1";
			// String folderProjectPath = "E:/ZATHURA EXAMPLE(landed)/";

			/*
			 * String jpaPath = "E:/ZATHURA EXAMPLE(pojos)/"; String jpaPckgName
			 * = "org.cgiar.ciat.ceres.model.pojos"; String projectName =
			 * "ceres1"; String folderProjectPath =
			 * "E:/ZATHURA EXAMPLE(landed)/";
			 */
			// JUAN MANUEL
			// String jpaPath =
			// "E:\\Workspace\\Tesis\\workspace\\demoProyecto\\war\\WEB-INF\\classes\\";
			// String jpaPckgName = "co.edu.usbcali.cliente.model";
			// String projectName = "demoProyecto";
			// String folderProjectPath =
			// "E:\\Workspace\\Tesis\\workspace\\demoProyecto\\src\\";
			// String
			// webRootFolderPath="E:\\Workspace\\Tesis\\workspace\\demoProyecto\\war\\";
			// String
			// libFolderPath="E:\\Workspace\\Tesis\\workspace\\demoProyecto\\war\\WEB-INF\\lib\\";

			// RICARDO
			String jpaPath = "F:\\Tesis\\workspace\\demoClientesGWT\\war\\WEB-INF\\classes\\";
			String jpaPckgName = "co.edu.usbcali.banco.model";
			String projectName = "demoClientesGWT";
			String folderProjectPath = "F:\\Tesis\\workspace\\demoClientesGWT\\src\\";
			String webRootFolderPath = "F:\\Tesis\\workspace\\demoClientesGWT\\war\\";
			String libFolderPath = "F:\\Tesis\\workspace\\demoClientesGWT\\war\\WEB-INF\\lib\\";

			// /home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco
			// String jpaPath =
			// "/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/build/classes/";
			// String jpaPckgName = "co.edu.usbcali.banco.modelo";
			// String projectName = "demoBanco";
			// String folderProjectPath =
			// "/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/src/";
			// String
			// webRootFolderPath="/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/WebContent/";
			// String
			// libFolderPath="/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/WebContent/WEB-INF/lib/";

			//

			int specificityLevel = 1;

			IMetaDataReader entityLoader = null;
			MetaDataModel metaDataModel = null;
			try {
				entityLoader = MetaDataReaderFactory.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
				metaDataModel = entityLoader.loadMetaDataModel(jpaPath, jpaPckgName);
			} catch (MetaDataReaderNotFoundException e) {
				e.printStackTrace();
			}catch (Exception e) {
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

			IZathuraGenerator zathuraGenerator = ZathuraGeneratorFactory.createZathuraGenerator("ZathuraJavaEE_GWT_Centric");
			zathuraGenerator.toGenerate(metaDataModel, projectName, folderProjectPath, properties);

			// Todos los generadores
			/*
			 * for(GeneratorModel
			 * generatorModel:ZathuraGeneratorFactory.getTheZathuraGenerators
			 * ().values()){ log.debug(generatorModel.getName());
			 * log.debug(generatorModel.getZathuraGenerator()); }
			 */

		} catch (GeneratorNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
