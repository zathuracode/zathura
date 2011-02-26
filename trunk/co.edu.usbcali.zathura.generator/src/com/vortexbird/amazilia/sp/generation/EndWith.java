package com.vortexbird.amazilia.sp.generation;

import java.io.File;
import java.io.FilenameFilter;

public class EndWith implements FilenameFilter
{
    private String strExtention;

    /**
         * Constructor of the class
         */
    public EndWith(String strExtension)
    {
	super();
	this.strExtention = strExtension;
    }

    /**
         * @return true if the strName of file name finish with the given
         *         strExtention
         */
    public boolean accept(File dir, String strName)
    {
	if (strName.endsWith(strExtention))
	    return true;
	else
	    return false;
    }
}
