package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;

public class ConnectionsUtils {
	
	private ConnectionsUtils() {

	}
	
	private static Properties properties = new java.util.Properties();
	
	/**
	 * Log4j
	 */
	private static Logger log = Logger.getLogger(ConnectionsUtils.class);
	
	/**
	 * xml file path 
	 */
	private static String xmlConfigConnections = GeneratorUtil.getXmlConfig()+"zathura-connections.properties";
	
	/**
	 * Generator Model
	 */
	private static HashMap<String, ConnectionModel> theZathuraConnections = null;
	
	static {
		try {
			loadZathuraConnections();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private static void loadZathuraConnections() throws FileNotFoundException, IOException  {		
		log.info("Reading:"+xmlConfigConnections);
		theZathuraConnections=new HashMap<String, ConnectionModel>();
		try {
			properties.load(new java.io.FileInputStream(xmlConfigConnections));
		} catch (Exception e) {
			properties.store(new java.io.FileOutputStream(xmlConfigConnections),"");
		}
		List<String> connectionNames=new ArrayList<String>();
		Enumeration<Object> theKeys=properties.keys();
		while (theKeys.hasMoreElements()) {
			Object object = (Object) theKeys.nextElement();
			if(object instanceof String){
				String key=(String)object;
				if(key.contains("-name")){
					connectionNames.add(properties.getProperty(key));
				}
			}
		}
		
		for (String nameConnection : connectionNames) {
			ConnectionModel connectionModel=new ConnectionModel();
			connectionModel.setDriverClassName(properties.getProperty(nameConnection+"-"+"driverClass"));
			connectionModel.setJarPath(properties.getProperty(nameConnection+"-"+"jarPath"));
			connectionModel.setName(properties.getProperty(nameConnection+"-"+"name"));
			connectionModel.setPassword(properties.getProperty(nameConnection+"-"+"password"));
			connectionModel.setUrl(properties.getProperty(nameConnection+"-"+"url"));
			connectionModel.setUser(properties.getProperty(nameConnection+"-"+"user"));
			connectionModel.setDriverTemplate(properties.getProperty(nameConnection+"-"+"driverTemplate"));
			theZathuraConnections.put(nameConnection,connectionModel);
		}
	}

	/**
	 * 
	 * @return
	 */
	public static HashMap<String, ConnectionModel> getTheZathuraConnections() {
		return theZathuraConnections;
	}
	
	public static ConnectionModel getTheZathuraConnectionModel(String name) {
		return theZathuraConnections.get(name);
	}
	/**
	 * 
	 * @return
	 */
	public static java.util.List<String> getConnectionNames() {
		
		List<String> connectionNames=new ArrayList<String>();
		Enumeration<Object> theKeys=properties.keys();
		while (theKeys.hasMoreElements()) {
			Object object = (Object) theKeys.nextElement();
			if(object instanceof String){
				String key=(String)object;
				if(key.contains("-name")){
					connectionNames.add(properties.getProperty(key));
				}
			}
		}
		return connectionNames;
		
	}
	/**
	 * Remove a ConnectionModel
	 * @param name
	 * @throws Exception
	 */
	public static void removeConnectionModel(String connectionName)throws Exception{
		properties.remove(connectionName+"-name");
		properties.remove(connectionName+"-driverClass");
		properties.remove(connectionName+"-jarPath");
		properties.remove(connectionName+"-password");
		properties.remove(connectionName+"-url");
		properties.remove(connectionName+"-user");
		properties.remove(connectionName+"-driverTemplate");
		
		
		theZathuraConnections.remove(connectionName);
		
		store();		
	}
	/**
	 * Save a connection Model
	 * @param connectionModel
	 * @throws Exception
	 */
	public static void saveConnectionModel(ConnectionModel connectionModel)throws Exception{
		if(connectionModel==null){
			throw new Exception("Connection model null");
		}
		if(connectionModel.getDriverTemplate()==null || connectionModel.getDriverTemplate().trim().equals("")==true){
			throw new Exception("Driver Template null");
		}
		if(connectionModel.getDriverClassName()==null || connectionModel.getDriverClassName().trim().equals("")==true){
			throw new Exception("DriverClassName null");
		}
		if(connectionModel.getJarPath()==null || connectionModel.getJarPath().trim().equals("")==true){
			throw new Exception("JarPath null");
		}
		if(connectionModel.getName()==null || connectionModel.getName().trim().equals("")==true){
			throw new Exception("Name null");
		}
		if(connectionModel.getPassword()==null || connectionModel.getPassword().trim().equals("")==true){
			throw new Exception("Password null");
		}
		if(connectionModel.getUrl()==null || connectionModel.getUrl().trim().equals("")==true){
			throw new Exception("URL null");
		}
		if(connectionModel.getUser()==null || connectionModel.getUser().trim().equals("")==true){
			throw new Exception("User null");
		}
		/*
		if(connectionExist(connectionModel.getName())==true){
			throw new Exception("A driver with that name already exists");
		}
		*/
		
		properties.put(connectionModel.getName()+"-name", connectionModel.getName());
		properties.put(connectionModel.getName()+"-driverClass", connectionModel.getDriverClassName());
		properties.put(connectionModel.getName()+"-jarPath", connectionModel.getJarPath());
		properties.put(connectionModel.getName()+"-password", connectionModel.getPassword());
		properties.put(connectionModel.getName()+"-url", connectionModel.getUrl());
		properties.put(connectionModel.getName()+"-user", connectionModel.getUser());
		properties.put(connectionModel.getName()+"-driverTemplate", connectionModel.getDriverTemplate());
		
		//Graba en el properties
		store();
		
		//Se adjunta a el hashMap
		theZathuraConnections.put(connectionModel.getName(), connectionModel);
		
	}
	/**
	 * Save properties information in the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void store() throws FileNotFoundException, IOException{		
		properties.store(new java.io.FileOutputStream(xmlConfigConnections),"");		
	}
	
	
    /**
     * Returns <tt>true</tt> if this map contains a mapping for the
     * specified connectionName.
     *
     * @param   connectionName   The key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * connectionName.
     */
	public static Boolean connectionExist(String connectionName){
		return theZathuraConnections.containsKey(connectionName);
	}
	
	
	
}
