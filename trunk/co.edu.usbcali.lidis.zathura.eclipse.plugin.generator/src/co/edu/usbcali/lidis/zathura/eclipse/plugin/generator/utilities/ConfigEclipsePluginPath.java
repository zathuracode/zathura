package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.reverse.utilities.ReverseEngineeringUtil;

public class ConfigEclipsePluginPath {
	
	private String pluginPath = null;
	private static ConfigEclipsePluginPath me=null;
	private ConfigEclipsePluginPath() {		
		configPath();		
	}
	


	/**
	 * 
	 */
	private void configPath() {		
		URL bundleRootURL = ZathuraGeneratorActivator.getDefault().getBundle().getEntry("/");
		try {
			URL pluginUrl = FileLocator.resolve(bundleRootURL);
			pluginPath = pluginUrl.getPath();
			GeneratorUtil.setFullPath(pluginPath);
			ReverseEngineeringUtil.setFullPath(pluginPath);
			
			
		} catch (IOException e) { 
			ZathuraGeneratorLog.logError(e);
		}
	}
	
	public static ConfigEclipsePluginPath getInstance(){
		if(me==null){
			me=new ConfigEclipsePluginPath();
		}		
		return me;
		
	}

}
