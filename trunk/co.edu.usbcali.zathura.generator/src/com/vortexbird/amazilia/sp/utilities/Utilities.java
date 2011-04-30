package com.vortexbird.amazilia.sp.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class Utilities.
 */
public class Utilities {
	
	/** The path project repositorio. */
	public static String pathProjectRepositorio = null;
	
	/** The path xsl. */
	public static String pathXsl = null;
	
	/** The path project. */
	public static String pathProject = null;
	
	/** The project name. */
	public static String projectName = null;
	
	/** The oracle version. */
	public String oracleVersion = null;

	/**
	 * Constructor of the class.
	 */
	public Utilities() {
		super();
	}

	/**
	 * copy the source file to the given path strTarget.
	 *
	 * @param source InputStream for the source file
	 * @param strTarget where the file will be copied
	 */
	public static void copy(java.io.InputStream source, String strTarget) {
		InputStream fIn = null;
		FileOutputStream fOut = null;
		byte[] b;
		int l = 0;

		try {
			fIn = source;
			fOut = new FileOutputStream(strTarget);

			b = new byte[1024];
			while ((l = fIn.read(b)) > 0) {
				fOut.write(b, 0, l);
			}

			fOut.close();
			fIn.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.toString());
		} catch (IOException ioe) {
			System.err.println(ioe.toString());
		}
	}

	/**
	 * Copy an existing file from source (path origen) to target (path target).
	 *
	 * @param strSource path of the file to copy
	 * @param strTarget path where the file will be copied
	 */
	public static void copy(String strSource, String strTarget) {
		FileInputStream fIn = null;
		FileOutputStream fOut = null;
		byte[] b;
		int l = 0;

		try {
			fIn = new FileInputStream(strSource);
			fOut = new FileOutputStream(strTarget);

			b = new byte[1024];
			while ((l = fIn.read(b)) > 0) {
				fOut.write(b, 0, l);
			}

			fOut.close();
			fIn.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.toString());
		} catch (IOException ioe) {
			System.err.println(ioe.toString());
		}
	}

	/**
	 * Create an folder in he given path with the given name.
	 *
	 * @param strPath path where the folder will be created
	 * @param strNameFolder name of the folder
	 * @return true if the folder was created
	 * @throws Exception the exception
	 */
	public static boolean createFolder(String strPath, String strNameFolder) throws Exception {
		boolean wasCreated = false;
		try {
			strPath = strPath + strNameFolder;
			File myDirectory = new File(strPath);
			myDirectory.mkdirs();
			wasCreated = true;
		} catch (Exception e) {
			throw new Exception("creation folder failed.\n" + e.getCause());
		}
		return wasCreated;
	}

	/**
	 * Create a package in the folder in the given path.
	 *
	 * @param strPath the str path
	 * @param strPackageName the str package name
	 * @return full package path
	 * @throws Exception the exception
	 */
	public static String createPackage(String strPath, String strPackageName) throws Exception {
		try {
			strPath = strPath + replaceAll(strPackageName, ".", File.separator);
			strPath = strPath + File.separator;
			File myDirectory = new File(strPath);
			myDirectory.mkdirs();
			return strPath;
		} catch (Exception e) {
			throw new Exception("creation package failed.\n" + e.getCause());
		}
	}

	/**
	 * replace all characters given in the ol string for the new string in the
	 * string source.
	 *
	 * @param strStringSource original string
	 * @param strOldString string to replace
	 * @param strNewString new string for change in the original string
	 * @return string replaced
	 */
	public static String replaceAll(String strStringSource, String strOldString, String strNewString) {
		StringBuffer strBufReplace = new StringBuffer();
		String strAux;

		for (int i = 0; i < strStringSource.length(); i++) {
			if ((i + strOldString.length()) < strStringSource.length()) {
				strAux = strStringSource.substring(i, i + strOldString.length());
				if (strAux.equals(strOldString)) {
					strBufReplace.append(strNewString);
					i += strOldString.length() - 1;
				} else {
					strBufReplace.append(strStringSource.substring(i, i + 1));
				}
			} else
				strBufReplace.append(strStringSource.substring(i, i + 1));
		}
		return strBufReplace.toString();
	}

	/**
	 * Remove comma from the constructors method in the files (specially java
	 * files) finded in the given path Example: change myMethod(param a, param
	 * b, param c,) to myMethod(param a, param b, param c).
	 *
	 * @param strPath path where the files are be changed
	 */
	public static void commaSolution(String strPath) {
		try {
			FileReader file = new FileReader(strPath);
			BufferedReader in = new BufferedReader(file);
			String strTmp;
			StringBuffer strBufTmp = new StringBuffer();
			while ((strTmp = in.readLine()) != null) {
				strTmp = Utilities.replaceAll(strTmp, ",)", ")");
				strTmp = Utilities.replaceAll(strTmp, ",\n)", ")");
				strTmp = Utilities.replaceAll(strTmp, "&&)", ")");
				strBufTmp.append(strTmp + "\n");
			}

			DataOutputStream out = new DataOutputStream(new FileOutputStream(strPath));
			out.writeBytes(strBufTmp.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Gets the path project.
	 *
	 * @return the pathProject
	 */
	public static String getPathProject() {
		return pathProject;
	}

	/**
	 * Sets the path project.
	 *
	 * @param pathProject the pathProject to set
	 */
	public static void setPathProject(String pathProject) {
		Utilities.pathProject = pathProject;
	}

	/**
	 * Gets the path project repositorio.
	 *
	 * @return the pathProjectRepositorio
	 */
	public static String getPathProjectRepositorio() {
		return pathProjectRepositorio;
	}

	/**
	 * Sets the path project repositorio.
	 *
	 * @param pathProjectRepositorio the pathProjectRepositorio to set
	 */
	public static void setPathProjectRepositorio(String pathProjectRepositorio) {
		Utilities.pathProjectRepositorio = pathProjectRepositorio;
	}

	/**
	 * Gets the path xsl.
	 *
	 * @return the pathXsl
	 */
	public static String getPathXsl() {
		return pathXsl;
	}

	/**
	 * Sets the path xsl.
	 *
	 * @param pathXsl the pathXsl to set
	 */
	public static void setPathXsl(String pathXsl) {
		Utilities.pathXsl = pathXsl;
	}

	/**
	 * Gets the project name.
	 *
	 * @return the projectName
	 */
	public static String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the project name.
	 *
	 * @param projectName the projectName to set
	 */
	public static void setProjectName(String projectName) {
		Utilities.projectName = projectName;
	}
}
