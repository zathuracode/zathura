package co.edu.usbcali.lidis.zathura.metadata.reader;

import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;

/**
 * Zathura Generator
 * 
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 */
public interface IMetaDataReader {
	public MetaDataModel loadMetaDataModel(String path, String pckgName);
}
