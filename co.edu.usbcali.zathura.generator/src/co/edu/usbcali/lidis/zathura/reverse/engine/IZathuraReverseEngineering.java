package co.edu.usbcali.lidis.zathura.reverse.engine;

import java.util.List;
import java.util.Properties;

/**
 * Zathura Generator
 * 
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @author William Altuzarra Noriega Noriega (williamaltu@gmail.com)
 * @version 1.0
 */
public interface IZathuraReverseEngineering {
	public void makePojosJPA_V1_0(Properties connectionProperties, List<String> tables);
}
