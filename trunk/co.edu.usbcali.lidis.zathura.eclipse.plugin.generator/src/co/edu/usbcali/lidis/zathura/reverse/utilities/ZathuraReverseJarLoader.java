package co.edu.usbcali.lidis.zathura.reverse.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ZathuraReverseJarLoader {

	public static void loadJar(String jarLocation)
			throws FileNotFoundException, IOException {
		
		String jarName = jarLocation;

		URLClassLoader urlLoader = getURLClassLoader(new URL("file", null,
				jarName));

		JarInputStream jis = new JarInputStream(new FileInputStream(jarName));
		JarEntry entry = jis.getNextJarEntry();
		int loadedCount = 0, totalCount = 0;

		while (entry != null) {
			String name = entry.getName();
			if (name.endsWith(".class")) {
				totalCount++;
				name = name.substring(0, name.length() - 6);
				name = name.replace('/', '.');
				System.out.print("> " + name);

				try {
					urlLoader.loadClass(name);
					System.out.println("\t- loaded");
					loadedCount++;
				} catch (Throwable e) {
					System.out.println("\t- not loaded");
					System.out.println("\t " + e.getClass().getName() + ": "
							+ e.getMessage());
				}

			}
			entry = jis.getNextJarEntry();
		}

		System.out.println("\n---------------------");
		System.out.println("Summary:");
		System.out.println("\tLoaded:\t" + loadedCount);
		System.out.println("\tFailed:\t" + (totalCount - loadedCount));
		System.out.println("\tTotal:\t" + totalCount);
		// ZathuraReverseMappingTool.callAntProcess();
	}

	private static URLClassLoader getURLClassLoader(URL jarURL) {
		return new URLClassLoader(new URL[] { jarURL });
	}

	public static void loadJar2(String jarLocation) throws Exception {
		Method addURL = URLClassLoader.class.getDeclaredMethod("addURL",
				new Class[] { URL.class });
		addURL.setAccessible(true);// you're telling the JVM to override the
		// default visibility
		File[] files = getExternalJars(jarLocation);// some method returning the
		// jars to add
		ClassLoader cl = ClassLoader.getSystemClassLoader();
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