package com.vortexbird.amazilia.sp.generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class alows create a files and folders, copy and delete files.
 * @author Hassan Hammad
 */
public class CreateFile
{
    /**
         * Constructor of the class
         */
    public CreateFile()
    {
	super();
    }

    /**
         * Create a file in the specific path, with the given name, extention
         * and text.
         * @param strPath path to create the file
         * @param strFileName name of the file
         * @param strExtention extention of the file (txt, java, xml..)
         * @param strText content of the file
         * @throws IOException
         */
    public static void createFile(String strPath, String strFileName,
	    String strExtention, String strText) throws IOException
    {
	try
	{
	    // Verified that parameters are not empty
	    if (strPath.trim().length() <= 0
		    || strFileName.trim().length() <= 0
		    || strText.trim().length() <= 0
		    || strExtention.trim().length() <= 0)
	    {
		throw new IOException(
			"You must specified the file path, file name "
				+ "extention and body (text) of the file.");
	    }
	    // complete the fileName plus extention
	    strFileName = strFileName + "." + strExtention;
	    // create file
	    File file = new File(strPath, strFileName);
	    FileWriter writer = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(writer);
	    PrintWriter out = new PrintWriter(bw);
	    // write into file
	    out.println(strText);
	    // close writers
	    out.close();
	    bw.close();
	    writer.close();
	}
	catch (IOException e)
	{
	    e.getMessage();
	    e.getCause();
	    e.printStackTrace();
	}
    }

    /**
         * Create a file in the default path (root folder), with the given name,
         * extention and text.
         * @param strFileName name of the file
         * @param strExtention extention of the file (txt, java, xml..)
         * @param strText content of the file
         * @throws IOException
         */
    public static void createFile(String strFileName, String strExtention,
	    String strText) throws IOException
    {
	try
	{
	    // Verified that parameters are not empty
	    if (strFileName.trim().length() <= 0
		    || strText.trim().length() <= 0
		    || strExtention.trim().length() <= 0)
	    {
		throw new IOException("You must specified the file name "
			+ "extention and body (text) of the file.");
	    }
	    // complete the fileName plus extention
	    strFileName = strFileName + "." + strExtention;
	    // create file
	    File file = new File(strFileName);
	    FileWriter writer = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(writer);
	    PrintWriter out = new PrintWriter(bw);
	    // write into file
	    out.println(strText);
	    // close writers
	    out.close();
	    bw.close();
	    writer.close();
	}
	catch (IOException e)
	{
	    e.getMessage();
	    e.getCause();
	    e.printStackTrace();
	}
    }

    /**
         * Create an folder in the given path with given name
         * @param strPath path to new folder
         * @param strFolderName name of the folder
         * @return true if the file was created
         */
    public static boolean createFolder(String strPath, String strFolderName)
    {
	boolean wasCreated = false;
	try
	{
	    strPath = strPath + strFolderName;
	    File myDirectory = new File(strPath);
	    myDirectory.mkdirs();
	    wasCreated = true;
	}
	catch (Exception e)
	{
	    e.getMessage();
	    e.getCause();
	    e.printStackTrace();
	}
	return wasCreated;
    }

    /**
         * Copy an file from source to target. The target file must be exist
         * @param strSource path of the source file
         * @param strTarget path of the target file
         */
    public static void copy(String strSource, String strTarget)
    {
	FileInputStream fIn = null;
	FileOutputStream fOut = null;
	byte[] b;
	int l = 0;

	try
	{
	    fIn = new FileInputStream(strSource);
	    fOut = new FileOutputStream(strTarget);

	    b = new byte[1024];
	    while ((l = fIn.read(b)) > 0)
	    {
		fOut.write(b, 0, l);
	    }

	    fOut.close();
	    fIn.close();
	}
	catch (FileNotFoundException fnfe)
	{
	    System.err.println(fnfe.toString());
	}
	catch (IOException ioe)
	{
	    System.err.println(ioe.toString());
	}
    }

    /**
         * Delete given file
         * @param file to delete
         */
    public static void deleteFiles(File file)
    {
	File fileAux = null;
	File listFiles[] = null;
	int iPos = -1;

	listFiles = file.listFiles();
	for (iPos = 0; iPos < listFiles.length; iPos++)
	{
	    fileAux = listFiles[iPos];
	    if (fileAux.isDirectory())
		deleteFiles(listFiles[iPos]);
	    listFiles[iPos].delete();
	}
	if (file.listFiles().length == 0)
	    file.delete();
    }
}
