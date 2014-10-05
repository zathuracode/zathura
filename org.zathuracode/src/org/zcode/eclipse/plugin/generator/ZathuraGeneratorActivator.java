package org.zcode.eclipse.plugin.generator;

import java.util.Enumeration;
import java.util.Properties;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zcode.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;

import com.sun.tools.javac.resources.version;

// TODO: Auto-generated Javadoc
/**
 * The activator class controls the plug-in life cycle Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 */
public class ZathuraGeneratorActivator extends AbstractUIPlugin {
	
	private static final Logger log = LoggerFactory.getLogger(ZathuraGeneratorActivator.class);
	
	

	// The plug-in ID
	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "org.zathuracode.eclipse.plugin.generator";

	// The shared instance
	/** The plugin. */
	private static ZathuraGeneratorActivator plugin;

	/**
	 * The constructor.
	 */
	public ZathuraGeneratorActivator() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		plugin = this;
		
		
		Properties p = System.getProperties();
		Enumeration keys = p.keys();
		while (keys.hasMoreElements()) {
		  String key = (String)keys.nextElement();
		  String value = (String)p.get(key);
		  log.info(key + ": " + value);
		  if(key!=null && key.equalsIgnoreCase("java.version")==true){
			  EclipseGeneratorUtil.javaVersion=value.substring(0,3);
		  }
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static ZathuraGeneratorActivator getDefault() {
		
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
