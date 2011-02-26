package com.vortexbird.amazilia.sp.generation;

import de.hunsicker.jalopy.Jalopy;
import java.io.File;
import java.io.FilenameFilter;

/**
 * This class allows format code of java files (classes)
 * @author Hassan Hammad
 */
public class FormatCodeWithJalopy
{
    public FormatCodeWithJalopy()
    {
	super();
    }

    /**
         * This method format code of all java files in the given strPath.
         * @param strPath path of java files
         */
    public static void FormatJavaCodeFolder(String strPath)
    {
	try
	{
	    Jalopy jalopy = new Jalopy();
	    FilenameFilter filter = new EndWith(".java");
	    File dir = new File(strPath);
	    String[] strFileNames = dir.list(filter);

	    for (int i = 0; i < strFileNames.length; i++)
	    {
		File in = new File(strPath + strFileNames[i]);
		jalopy.setInput(in);
		jalopy.setOutput(in);

		jalopy.format();
		/*
                 * if (jalopy.getState() == Jalopy.State.OK) {
                 * System.out.println(in + " Formateo exitoso"); } else if
                 * (jalopy.getState() == Jalopy.State.WARN) {
                 * System.out.println(in + " Formateo con Warning"); } else if
                 * (jalopy.getState() == Jalopy.State.ERROR) {
                 * System.out.println(in + " Formateo con Error"); }
                 */
		in = null;
		System.gc();
	    }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    e.getCause();
	    e.getMessage();
	}
    }

    public static void FormatGeneralFolder(String path)
    {
	try
	{
	    Jalopy jalopy = new Jalopy();
	    File dir = new File(path);
	    String[] fileNames = dir.list();

	    for (int i = 0; i < fileNames.length; i++)
	    {
		File in = new File(path + fileNames[i]);
		jalopy.setInput(in);
		jalopy.setOutput(in);

		jalopy.format();

		if (jalopy.getState() == Jalopy.State.OK)
		{
		    System.out.println(" [Formatting Succeful]");
		}
		else
		    if (jalopy.getState() == Jalopy.State.WARN)
		    {
			System.out.println(" [Formatting with Warnings]");
		    }
		    else
			if (jalopy.getState() == Jalopy.State.ERROR)
			{
			    System.out.println(" [Formatting with Errors]");
			}
		in = null;
		System.gc();
	    }

	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    e.getCause();
	    e.getMessage();
	}
    }

    public static void FormatGeneralOne(String path)
    {
	Jalopy jalopy = new Jalopy();
	try
	{
	    File in = new File(path);
	    jalopy.setInput(in);
	    jalopy.setOutput(in);

	    jalopy.format();

	    if (jalopy.getState() == Jalopy.State.OK)
	    {
		System.out.println(in.toString() + " [Formatting Succeful]");
	    }
	    else
		if (jalopy.getState() == Jalopy.State.WARN)
		{
		    System.out.println(in.toString()
			    + " [Formatting with Warnings]");
		}
		else
		    if (jalopy.getState() == Jalopy.State.ERROR)
		    {
			System.out.println(in.toString()
				+ " [Formatting with Errors]");
		    }
	    jalopy = null;
	    in = null;
	    System.gc();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    e.getCause();
	    e.getMessage();
	}
    }

    /**
         * Format the given String to other String like: caSAnoVa to Casanova
         * @param strName
         * @return strName formatted
         */
    public static String formatNameFile(String strName)
    {
	try
	{
	    strName = strName.toLowerCase();
	    String tmp = "" + strName.charAt(0);
	    strName = strName.replaceFirst(tmp, tmp.toUpperCase());
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    e.getCause();
	    e.getMessage();
	}
	return strName;
    }
}
