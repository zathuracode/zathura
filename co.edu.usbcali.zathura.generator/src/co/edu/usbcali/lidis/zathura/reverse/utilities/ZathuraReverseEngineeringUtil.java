package co.edu.usbcali.lidis.zathura.reverse.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import net.sourceforge.squirrel_sql.fw.sql.ISQLAlias;
import net.sourceforge.squirrel_sql.fw.sql.ISQLConnection;
import net.sourceforge.squirrel_sql.fw.sql.ISQLDriver;
import net.sourceforge.squirrel_sql.fw.sql.ITableInfo;
import net.sourceforge.squirrel_sql.fw.sql.SQLDriver;
import net.sourceforge.squirrel_sql.fw.sql.SQLDriverManager;

import org.apache.log4j.Logger;

import com.vortexbird.amazilia.fw.AmaziliaSQLAlias;

/**
 * Zathura Generator
 * 
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @author William Altuzarra Noriega Noriega (williamaltu@gmail.com)
 * @version 1.0
 */
public class ZathuraReverseEngineeringUtil {

	private static String fullPath = "";
	public static String slash = System.getProperty("file.separator");

	public static String revEngFileName = "hibernate.reveng.xml";
	public static String databaseTypesFileName = "database-types.xml";
	public static String buildXmlFileName = "build.xml";
	public static String buildCompileXmlFileName = "buildCompile.xml";

	private static String reverseTemplates = "reverseTemplates" + ZathuraReverseEngineeringUtil.slash;
	private static String tempFiles = "tempFiles" + ZathuraReverseEngineeringUtil.slash;
	private static String antRevEng = "antBuild-revEng" + ZathuraReverseEngineeringUtil.slash;

	private static String xmlDatabaseTypesPath = "config" + ZathuraReverseEngineeringUtil.slash + databaseTypesFileName;
	private static String tempFileBuildPath = tempFiles + buildXmlFileName;
	private static String tempFileBuildCompilePath = tempFiles + buildCompileXmlFileName;

	private static ISQLConnection sqlConnection = null;
	private static ISQLAlias alias = null;
	private static ISQLDriver sqlDriver = null;
	// private static SQLDriverPropertyCollection driverProperties =null;
	private static SQLDriverManager sqlDriverManager = null;

	String[] types = { "TABLE", "VIEW", "SYNONYM", "ALIAS" };

	/**
	 * Log4j
	 */
	private static Logger log = Logger.getLogger(ZathuraReverseEngineeringUtil.class);

	/**
	 * Generator Model
	 */
	private static HashMap<String, DatabaseTypeModel> theZathuraDataBaseTypes = null;

	/**
	 * The names of generators
	 */
	private static java.util.List<String> theZathuraDataBaseNames = new ArrayList<String>();

	public static String getTempFileBuildPath() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + tempFileBuildPath;
		}
		return tempFileBuildPath;
	}

	public static String getTempFileBuildCompilePath() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + tempFileBuildCompilePath;
		}
		return tempFileBuildCompilePath;
	}

	public static String getXmlDatabaseTypesPath() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + xmlDatabaseTypesPath;
		}
		return xmlDatabaseTypesPath;
	}

	public static String getFullPath() {
		return fullPath;
	}

	public static void setFullPath(String fullPath) {

		if (fullPath != null && fullPath.startsWith("/") && System.getProperty("os.name").toUpperCase().contains("WINDOWS") == true) {
			fullPath = fullPath.substring(1, fullPath.length());
		}
		if (fullPath != null) {
			if (slash.equals("/")) {
				fullPath = replaceAll(fullPath, "\\", slash);
				if (fullPath.endsWith("\\") == true) {
					fullPath = fullPath.substring(0, fullPath.length() - 1);
					fullPath = fullPath + slash;
				}

			} else if (slash.equals("\\")) {
				fullPath = replaceAll(fullPath, "/", slash);
				if (fullPath.endsWith("/") == true) {
					fullPath = fullPath.substring(0, fullPath.length() - 1);
					fullPath = fullPath + slash;
				}
			}
		}
		ZathuraReverseEngineeringUtil.fullPath = fullPath;
	}

	public static String getReverseTemplates() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + reverseTemplates;
		}
		return reverseTemplates;
	}

	public static String getTempFilesPath() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + tempFiles;
		}
		return tempFiles;
	}

	public static String getAntBuildRevEngPath() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + antRevEng;
		}
		return reverseTemplates;
	}

	public static String fixDomain(String domainName) {
		String retFixDomian = null;
		if (domainName == null) {
			return "";
		}
		if (domainName.length() > 0) {
			retFixDomian = replaceAll(domainName, ".", ZathuraReverseEngineeringUtil.slash);
		}
		return retFixDomian;
	}

	/**
	 * 
	 * @param cadena
	 * @param old
	 * @param snew
	 * @return
	 */
	public static String replaceAll(String cadena, String old, String snew) {
		StringBuffer replace = new StringBuffer();
		String aux;

		for (int i = 0; i < cadena.length(); i++) {
			if ((i + old.length()) < cadena.length()) {
				aux = cadena.substring(i, i + old.length());
				if (aux.equals(old)) {
					replace.append(snew);
					i += old.length() - 1;
				} else {
					replace.append(cadena.substring(i, i + 1));
				}
			} else
				replace.append(cadena.substring(i, i + 1));
		}
		return replace.toString();
	}

	public static String projectPathInConsole() {
		URL url = ZathuraReverseEngineeringUtil.class.getResource("ReverseUtil.class");
		String classPath = url.getPath().substring(1);
		int tmp = classPath.indexOf("zathura-ReverseMappingTool") + 26;

		String inconmpletePath = classPath.substring(0, tmp);
		String cPath = "" + classPath.charAt(tmp);
		String projectPath = null;

		if (cPath.equals("2")) {
			projectPath = inconmpletePath + "2" + ZathuraReverseEngineeringUtil.slash;
		} else {
			projectPath = inconmpletePath + ZathuraReverseEngineeringUtil.slash;
		}
		return projectPath;
	}

	public static boolean validationsList(List list) {
		if (list != null) {
			if (!list.isEmpty() && list.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static HashMap<String, DatabaseTypeModel> loadZathuraDatabaseTypes() throws FileNotFoundException, XMLStreamException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		log.info("Reading:" + ZathuraReverseEngineeringUtil.xmlDatabaseTypesPath);

		DatabaseTypeModel databaseTypeModel = null;
		boolean boolName = false;
		boolean boolUrl = false;
		boolean boolDriverClassName = false;

		theZathuraDataBaseTypes = new HashMap<String, DatabaseTypeModel>();

		// Get the factory instace first.
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
		factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
		factory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);

		log.debug("FACTORY: " + factory);

		XMLEventReader r = factory.createXMLEventReader(new FileInputStream(getXmlDatabaseTypesPath()));

		// iterate as long as there are more events on the input stream
		while (r.hasNext()) {
			XMLEvent e = r.nextEvent();
			if (e.isStartElement()) {
				StartElement startElement = (StartElement) e;
				QName qname = startElement.getName();
				String localName = qname.getLocalPart();
				if (localName.equals("database") == true) {
					databaseTypeModel = new DatabaseTypeModel();
				} else if (localName.equals("name") == true) {
					boolName = true;
					log.info(localName);
				} else if (localName.equals("url") == true) {
					boolUrl = true;
					log.info(localName);
				} else if (localName.equals("driverClassName") == true) {
					boolDriverClassName = true;
					log.info(localName);
				}

			} else if (e.isCharacters()) {
				Characters characters = (Characters) e;
				String cadena = characters.getData().toString().trim();
				if (boolName == true) {
					databaseTypeModel.setName(cadena);
					theZathuraDataBaseNames.add(cadena);
					boolName = false;
					log.info(cadena);
				} else if (boolUrl == true) {
					databaseTypeModel.setUrl(cadena);
					boolUrl = false;
					log.info(cadena);
				} else if (boolDriverClassName == true) {
					databaseTypeModel.setDriverClassName(cadena);
					boolDriverClassName = false;
					log.info(cadena);
				}
			} else if (e.isEndElement() == true) {
				EndElement endElement = (EndElement) e;
				QName qname = endElement.getName();
				String localName = qname.getLocalPart();
				if (localName.equals("database") == true) {
					theZathuraDataBaseTypes.put(databaseTypeModel.getName(), databaseTypeModel);
				}
			}

		}
		log.debug("DataBaseTypes length:" + theZathuraDataBaseTypes.size());
		return theZathuraDataBaseTypes;
	}

	public static DatabaseTypeModel getDatabaseTypeModel(String name) {
		if (theZathuraDataBaseTypes == null) {
			try {
				theZathuraDataBaseTypes = loadZathuraDatabaseTypes();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (XMLStreamException e) {

				e.printStackTrace();
			} catch (InstantiationException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		}
		return theZathuraDataBaseTypes.get(name);
	}

	public static void main(String[] args) {
		try {
			HashMap<String, DatabaseTypeModel> theZathuraDataBaseTypes = loadZathuraDatabaseTypes();

			for (DatabaseTypeModel databaseTypeModel : theZathuraDataBaseTypes.values()) {
				System.out.println(databaseTypeModel.getName());
				System.out.println(databaseTypeModel.getUrl());
				System.out.println(databaseTypeModel.getDriverClassName());
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void validarPackage(String packageName) throws Exception {
		if (packageName.startsWith(".") || packageName.endsWith(".")) {
			throw new Exception("A package name cannot start or end with a dot");
		}
	}

	/**
	 * 
	 * @param path
	 */
	public static void deleteFiles(String path) {
		File file = new File(path);
		deleteFiles(file);
	}

	/**
	 * 
	 * @param file
	 */
	public static void deleteFiles(File file) {
		File fileAux = null;
		File listFiles[] = null;
		int iPos = -1;

		listFiles = file.listFiles();
		for (iPos = 0; iPos < listFiles.length; iPos++) {
			fileAux = listFiles[iPos];
			if (fileAux.isDirectory())
				deleteFiles(listFiles[iPos]);
			listFiles[iPos].delete();
		}
		if (file.listFiles().length == 0)
			file.delete();
	}

	/**
	 * 
	 * @param path
	 * @param nameFolder
	 * @return
	 */
	public static File createFolder(String path) {
		File aFile = new File(path);
		aFile.mkdirs();
		return aFile;
	}

	/**
	 * Borrar los archivos de la carpeta tempFiles
	 * 
	 * @param path
	 */
	public static void resetTempFiles(String path) {
		// Borrar carpeta de temporales
		deleteFiles(path);
		// Crea carpeta de temporales
		createFolder(path);
	}

	/**
	 * 
	 * @param url
	 * @param driverClassName
	 * @param user
	 * @param password
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public static void testDriver(String url, String driverClassName, String user, String password) throws ClassNotFoundException, SQLException, Exception {

		sqlDriverManager = new SQLDriverManager();
		sqlDriver = new SQLDriver();
		alias = new AmaziliaSQLAlias();

		sqlDriver.setDriverClassName(driverClassName);
		sqlDriver.setUrl(url);
		alias.setUrl(url);
		alias.setUserName(user);
		alias.setPassword(password);

		sqlConnection = sqlDriverManager.getConnection(sqlDriver, alias, user, password);

	}

	public static Connection getConnection(String url, String driverClassName, String user, String password) throws ClassNotFoundException, SQLException,
			Exception {

		sqlDriverManager = new SQLDriverManager();
		sqlDriver = new SQLDriver();
		alias = new AmaziliaSQLAlias();

		sqlDriver.setDriverClassName(driverClassName);
		sqlDriver.setUrl(url);
		alias.setUrl(url);
		alias.setUserName(user);
		alias.setPassword(password);

		sqlConnection = sqlDriverManager.getConnection(sqlDriver, alias, user, password);
		return sqlConnection.getConnection();

	}

	public static void closeAll() {

		try {
			if (sqlConnection != null) {
				sqlConnection.close();
			}
			alias = null;
			sqlDriver = null;
			sqlConnection = null;
			sqlDriverManager = null;
		} catch (SQLException e) {
			// Ignore
		}

	}

	public static String[] getCatalogs() throws SQLException {
		return sqlConnection.getSQLMetaData().getCatalogs();
	}

	public static String[] getSchemas() throws SQLException, Exception {
		return sqlConnection.getSQLMetaData().getSchemas();
	}

	// public synchronized ITableInfo[] getTables(String catalog, String
	// schemaPattern, String tableNamePattern,String[] types, ProgressCallBack
	// progressCallBack)
	public static ITableInfo[] getTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
		String[] types = { "TABLE", "VIEW", "SYNONYM", "ALIAS" };
		ITableInfo[] tableInfo = null;
		try {
			tableInfo = sqlConnection.getSQLMetaData().getTables(catalog, schemaPattern, tableNamePattern, types, null);
		} catch (Exception e) { // Ignore
			e.printStackTrace();
		}
		return tableInfo;
	}

	/**
	 * @return the sqlConnection
	 */
	public static ISQLConnection getSqlConnection() {
		return sqlConnection;
	}

}
