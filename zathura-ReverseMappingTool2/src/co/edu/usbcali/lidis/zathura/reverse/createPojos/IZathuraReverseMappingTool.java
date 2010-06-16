package co.edu.usbcali.lidis.zathura.reverse.createPojos;

import java.util.List;
import java.util.Properties;

public interface IZathuraReverseMappingTool {
	public void makePojos(Properties connectionProperties, List<String> tables);
}
