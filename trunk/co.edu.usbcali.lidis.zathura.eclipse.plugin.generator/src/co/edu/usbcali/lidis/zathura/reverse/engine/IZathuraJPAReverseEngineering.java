package co.edu.usbcali.lidis.zathura.reverse.engine;

import java.util.List;
import java.util.Properties;

public interface IZathuraJPAReverseEngineering {
	public void makePojosJPA_V1_0(Properties connectionProperties, List<String> tables);
}
