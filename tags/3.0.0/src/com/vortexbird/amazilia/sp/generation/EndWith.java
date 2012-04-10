package com.vortexbird.amazilia.sp.generation;

import java.io.File;
import java.io.FilenameFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class EndWith.
 */
public class EndWith implements FilenameFilter {
	
	/** The str extention. */
	private String strExtention;

	/**
	 * Constructor of the class.
	 *
	 * @param strExtension the str extension
	 */
	public EndWith(String strExtension) {
		super();
		this.strExtention = strExtension;
	}

	/**
	 * Accept.
	 *
	 * @param dir the dir
	 * @param strName the str name
	 * @return true if the strName of file name finish with the given
	 * strExtention
	 */
	public boolean accept(File dir, String strName) {
		if (strName.endsWith(strExtention))
			return true;
		else
			return false;
	}
}
