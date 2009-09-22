package co.edu.usbcali.lidis.zathura.eclipse.utilities;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.Activator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;

public class ConfigEclipsePluginPath {
	
	private String pluginPath = null;
	private static ConfigEclipsePluginPath me=null;
	private ConfigEclipsePluginPath() {		
		configPath();
		//loadJars();		
	}
	


	/**
	 * 
	 */
	private void configPath() {		
		URL bundleRootURL = Activator.getDefault().getBundle().getEntry("/");
		try {
			URL pluginUrl = FileLocator.resolve(bundleRootURL);
			pluginPath = pluginUrl.getPath();
			GeneratorUtil.setFullPath(pluginPath);
		} catch (IOException e) { 
			System.out.println(e);
		}
	}
	
	public static ConfigEclipsePluginPath getInstance(){
		if(me==null){
			me=new ConfigEclipsePluginPath();
		}		
		return me;
		
	}

}
