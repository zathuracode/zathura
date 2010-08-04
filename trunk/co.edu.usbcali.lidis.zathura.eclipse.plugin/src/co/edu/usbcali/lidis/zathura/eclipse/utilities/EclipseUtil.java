package co.edu.usbcali.lidis.zathura.eclipse.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.eclipse.core.resources.IProject;

import co.edu.usbcali.lidis.zathura.eclipse.gui.WizardMain;
import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.generator.factory.ZathuraGeneratorFactory;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;

public class EclipseUtil {

	private EclipseUtil() {

	}

	public static IProject project;
	public static String projectName;

	public static String javaEntityPackage;

	public static String workspaceFolderPath;
	public static String javaClassFolderPath;
	public static String javaSourceFolderPath;
	public static String webRootFolderPath;
	public static String libFolderPath;
	public static String fullPathProject;

	public static String zathuraGeneratorName;
	public static int metaDataReader;

	public static WizardMain wizardMain;

	/**
	 * Se usa para generar cuando termina el wizard
	 * 
	 * @throws MetaDataReaderNotFoundException
	 * @throws GeneratorNotFoundException
	 */
	public static void generate() throws MetaDataReaderNotFoundException,
			GeneratorNotFoundException {

		/*
		 * String jpaPath =
		 * "/home/diegomez/workspaces/runtime-EclipseApplication/demoJpa/bin/";
		 * String jpaPckgName = "co.edu.usbcali.lidis.banco.modelo"; String
		 * projectName = "bancoUSB"; String folderProjectPath =
		 * "/home/diegomez/workspaces/runtime-EclipseApplication/demoJpa/src/";
		 */

		String jpaPath = EclipseUtil.javaClassFolderPath;
		String jpaPckgName = EclipseUtil.javaEntityPackage;
		String projectName = EclipseUtil.projectName;
		String folderProjectPath = EclipseUtil.javaSourceFolderPath;
		String webRootFolderPath = EclipseUtil.webRootFolderPath;
		String libFolderPath = EclipseUtil.libFolderPath;

		int specificityLevel = 2;

		IMetaDataReader entityLoader = null;
		MetaDataModel metaDataModel = null;

		entityLoader = MetaDataReaderFactory
				.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
		metaDataModel = entityLoader.loadMetaDataModel(jpaPath, jpaPckgName);

		// Variables para el properties
		Properties properties = new Properties();
		properties.put("jpaPath", jpaPath);
		properties.put("jpaPckgName", jpaPckgName);
		properties.put("specificityLevel", new Integer(specificityLevel));
		properties.put("webRootFolderPath", webRootFolderPath);
		properties.put("libFolderPath", libFolderPath);
		properties.put("folderProjectPath", folderProjectPath);

		IZathuraGenerator zathuraGenerator = ZathuraGeneratorFactory
				.createZathuraGenerator(EclipseUtil.zathuraGeneratorName);
		zathuraGenerator.toGenerate(metaDataModel, projectName,
				folderProjectPath, properties);

	}

	public static void loadJar(String jarLocation) throws Exception {
		
		//TODO Cargar con class loader del RPC
		
		Method addURL = URLClassLoader.class.getDeclaredMethod("addURL",new Class[] { URL.class });
		addURL.setAccessible(true);// you're telling the JVM to override the
		// default visibility
		File[] files = getExternalJars(jarLocation);// some method returning the
		// jars to add
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		//ClassLoader cl = Thread.currentThread().getContextClassLoader();
		for (int i = 0; i < files.length; i++) {
			URL url = files[i].toURL();
			addURL.invoke(cl, new Object[] { url });
			System.out.println("\n---------------------");
			System.out.println("Summary:");
			System.out.println("\tLoaded:\t" + files[i].getName());
		}
		// at this point, the default class loader has all the jars you
		// indicated
	}

	private static File[] getExternalJars(String jarLocation) {
		File[] files = new File[1];
		files[0] = new File(jarLocation);
		return files;
	}



}
