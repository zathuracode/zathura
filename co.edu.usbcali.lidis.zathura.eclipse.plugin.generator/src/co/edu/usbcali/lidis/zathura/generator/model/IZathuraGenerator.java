package co.edu.usbcali.lidis.zathura.generator.model;

import java.util.Properties;

import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
/**
 * 
 * @author Diego Armando Gomez Mosquera - Diego Armando Gomez Mosquera
 *
 */
public interface IZathuraGenerator {
	public void toGenerate(MetaDataModel metaDataModel, String projectName,String folderProjectPath,Properties propiedades);	
}
