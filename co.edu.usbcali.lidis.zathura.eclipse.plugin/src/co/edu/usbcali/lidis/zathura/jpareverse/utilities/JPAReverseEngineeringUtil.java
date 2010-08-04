package co.edu.usbcali.lidis.zathura.jpareverse.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

import org.apache.log4j.Logger;

public class JPAReverseEngineeringUtil {

	private static String fullPath = "";
	public static String slash = System.getProperty("file.separator");
	
	
	public static String revEngFileName = "hibernate.reveng.xml";
	public static String databaseTypesFileName="database-types.xml";
	public static String buildXmlFileName="build.xml";
	

	private static String reverseTemplates = "reverseTemplates"+ JPAReverseEngineeringUtil.slash;
	private static String tempFiles = "tempFiles" + JPAReverseEngineeringUtil.slash;
	private static String antRevEng = "antBuild-revEng" + JPAReverseEngineeringUtil.slash;
		
	private static String xmlDatabaseTypesPath ="config" + JPAReverseEngineeringUtil.slash + databaseTypesFileName;
	private static String tempFileBuildPath=tempFiles+buildXmlFileName;
	
	private static Connection connection=null;
	
	
	/**
	 * Log4j
	 */
	private static Logger log = Logger.getLogger(JPAReverseEngineeringUtil.class);
	
	/**
	 * Generator Model
	 */
	private static HashMap<String, DatabaseTypeModel> theZathuraDataBaseTypes = null;

	/**
	 * The names of generators
	 */
	private static java.util.List<String> theZathuraDataBaseNames=new ArrayList<String>();
	
	
	
	public static void testDriver(String url,String driverClassName, String user, String password)throws ClassNotFoundException,SQLException,Exception{
		
		Class.forName(driverClassName);
		connection=DriverManager.getConnection(url,user,password);
		
	}
	
	public static List<String> getCatalogSchema(){
		
		List<String> listCatalogSchema=new ArrayList<String>(); 		
		
		try {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rsSchemas = meta.getSchemas();
			ResultSet rsCatalogs = meta.getCatalogs();
			
			while(rsSchemas.next()){				
				listCatalogSchema.add(rsSchemas.getString(1));
			}
			
			while(rsCatalogs.next()){
				listCatalogSchema.add(rsCatalogs.getString(1));	    	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listCatalogSchema;
	}
	
	public static List<String> getTables(String catalogSchema){
		
		List<String> tables=new ArrayList<String>(); 		
		
		try {
			DatabaseMetaData meta = connection.getMetaData();			
			ResultSet rs = meta.getTables(catalogSchema, catalogSchema, "%", null);
			while(rs.next()){
				tables.add(rs.getString(3));
			}    
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tables;
	}
	
	
	
	
	
	
	public static String getTempFileBuildPath() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+tempFileBuildPath;
		}
		return tempFileBuildPath;
	}
	public static String getXmlDatabaseTypesPath() {
		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath+xmlDatabaseTypesPath;
		}
		return xmlDatabaseTypesPath;
	}
	
	public static String getFullPath() {
		return fullPath;
	}
	public static void setFullPath(String fullPath) {
		JPAReverseEngineeringUtil.fullPath = fullPath;
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
		if(domainName == null){
			return "";
		}
		if(domainName.length()>0){
			retFixDomian = replaceAll(domainName, ".", JPAReverseEngineeringUtil.slash);
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
	public static String replaceAll (String cadena,String old,String snew) {
		StringBuffer replace= new StringBuffer();
		String aux;
	
		for (int i = 0; i < cadena.length(); i++) {
			if ( (i+old.length()) <cadena.length() ){
				aux =  cadena.substring(i,i+old.length());
				if (aux.equals(old)){
					replace.append(snew);
					i+=old.length()-1;
				}else{
					replace.append(cadena.substring(i,i+1));
				}
			}else
				replace.append(cadena.substring(i,i+1));
		}
		return replace.toString();
	}
	
	public static String projectPathInConsole(){
		URL url = JPAReverseEngineeringUtil.class.getResource("ReverseUtil.class");
		String classPath = url.getPath().substring(1);
		int tmp = classPath.indexOf("zathura-ReverseMappingTool")+26;
		
		String inconmpletePath =  classPath.substring(0, tmp);
		String cPath = ""+classPath.charAt(tmp);
		String projectPath = null;
		
		if(cPath.equals("2")){
			projectPath = inconmpletePath+"2"+JPAReverseEngineeringUtil.slash;
		}else{
			projectPath = inconmpletePath+JPAReverseEngineeringUtil.slash;
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
	
	public static List<String> fillTableList(){
		List<String> tablesList = new ArrayList<String>();
		
		tablesList.add("CLIENTES");
		tablesList.add("CONSIGNACIONES");
		tablesList.add("CUENTAS");
		tablesList.add("RETIROS");
		tablesList.add("TIPOS_DOCUMENTOS");
		tablesList.add("TIPOS_USUARIOS");
		tablesList.add("USUARIOS");
		
		return tablesList;
	}
	
public static HashMap<String, DatabaseTypeModel> loadZathuraDatabaseTypes() throws FileNotFoundException,XMLStreamException, InstantiationException, IllegalAccessException,ClassNotFoundException {
		
		log.info("Reading:"+JPAReverseEngineeringUtil.xmlDatabaseTypesPath);
		
		DatabaseTypeModel databaseTypeModel = null;
		boolean boolName = false;
		boolean boolUrl = false;
		boolean boolDriverClassName=false;


		theZathuraDataBaseTypes = new HashMap<String, DatabaseTypeModel>();
		
		// Get the factory instace first.		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		 factory.setProperty(
			        XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
			        Boolean.TRUE);
			    factory.setProperty(
			        XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
			        Boolean.FALSE);
			    factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE,
			        Boolean.TRUE);
			    factory.setProperty(XMLInputFactory.IS_COALESCING,
			        Boolean.TRUE);

		
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
				}else if (localName.equals("driverClassName") == true) {
					boolDriverClassName = true;
					log.info(localName);
				}
				
			} else if (e.isCharacters()) {
				Characters characters = (Characters) e;
				String cadena = characters.getData().toString().trim();
				if(boolName == true) {
					databaseTypeModel.setName(cadena);
					theZathuraDataBaseNames.add(cadena);
					boolName = false;
					log.info(cadena);
				} else if (boolUrl == true) {
					databaseTypeModel.setUrl(cadena);
					boolUrl = false;
					log.info(cadena);
				}else if (boolDriverClassName == true) {
					databaseTypeModel.setDriverClassName(cadena);
					boolDriverClassName = false;
					log.info(cadena);
				}
			} else if (e.isEndElement() == true) {
				EndElement endElement = (EndElement) e;
				QName qname = endElement.getName();
				String localName = qname.getLocalPart();
				if (localName.equals("database") == true) {
					theZathuraDataBaseTypes.put(databaseTypeModel.getName(),databaseTypeModel);
				}
			}

		}
		log.debug("DataBaseTypes length:"+theZathuraDataBaseTypes.size());
		return theZathuraDataBaseTypes;
	}
	
	public static void main(String[] args) {
		try {
			HashMap<String, DatabaseTypeModel> theZathuraDataBaseTypes=loadZathuraDatabaseTypes();
			
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

}
