package co.edu.usbcali.lidis.zathura.generator.model;

import java.util.Properties;

import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;

/**
 * Zathura Generator
 * 
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 */
public interface IZathuraGenerator {
	public void toGenerate(MetaDataModel metaDataModel, String projectName, String folderProjectPath, Properties propiedades);
}
