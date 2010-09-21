package co.edu.usbcali.lidis.zathura.reverse.utilities;

import java.sql.*;
import java.util.*;

/**
 * 
 * @author Diego
 * 
 */
public class DatabaseUtilities {

	/**
	 * Consulta los catalogos de una base de datos
	 * 
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getCatalogs(Connection connection)throws SQLException {
		DatabaseMetaData databaseMetaData = null;
		ResultSet resultSet = null;
		List<String> catalogsList = null;

		try {
			databaseMetaData = connection.getMetaData();
			resultSet = databaseMetaData.getCatalogs();
			catalogsList = new ArrayList<String>();
			while (resultSet.next()) {
				catalogsList.add(resultSet.getString(1));
			}
			return catalogsList;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	/**
	 * Consulta los schemas de una base de datos
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static List<String> getSchemas(Connection connection) throws SQLException, Exception {
		DatabaseMetaData databaseMetaData = null;
		ResultSet resultSet = null;
		Map<String,List<String>> schemasMap = null;
		List<String> schemasList=null;
		try {
			databaseMetaData = connection.getMetaData();
			resultSet = databaseMetaData.getSchemas();
			schemasMap = new HashMap<String, List<String>>();
			
			while (resultSet.next()) {
				String schema = resultSet.getString(1);
				String catalog = null;
				if (resultSet.getMetaData().getColumnCount() > 1) {
					catalog = resultSet.getString(2);
				}
				
				schemasList = (List<String>) schemasMap.get(catalog);
				if (schemasList == null) {
					schemasList = new ArrayList<String>();
					schemasMap.put(catalog, schemasList);
				}
				schemasList.add(schema);
			}
			
			Collection<List<String>> collections=schemasMap.values();
			schemasList=new ArrayList<String>();
			for (List<String> list : collections) {
				for (String schemasName : list) {
					schemasList.add(schemasName);
				}
			}
			
			return schemasList;
		} finally {
			if (resultSet != null){
				resultSet.close();
			}
		}
	}
	/**
	 * Consulta las tablas de una base de datos por catalogo o schema
	 * @param connection
	 * @param catalog
	 * @param schema
	 * @param tablePattern
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getTables(Connection connection, String catalog, String schema,String tablePattern) throws SQLException {
		DatabaseMetaData databaseMetaData = null;
		ResultSet resultSet = null;
		List<String> tableList=null; 
		try {
			if(tablePattern==null || tablePattern.equals("")==true){
				tablePattern="%";
			}
			databaseMetaData = connection.getMetaData();
			resultSet = databaseMetaData.getTables(catalog, schema, tablePattern, new String[] {"TABLE", "VIEW", "SYNONYM", "ALIAS"});
			tableList = new ArrayList<String>();
			while (resultSet.next()) {
				tableList.add(resultSet.getString(3));
			}
			return tableList;
		} finally {
			if (resultSet != null)
				resultSet.close();
		}
	}
	
	/**
	 * Consulta las ForeignKey de una tabla
	 * @param connection
	 * @param catalog
	 * @param schema
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static Set<String> getForeignKeyColumns(Connection connection, String catalog,String schema, String table) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		HashSet<String> columns=null;
		try {
			resultSet = databaseMetaData.getImportedKeys(catalog, schema, table);
			columns = new HashSet<String>();
			while (resultSet.next()) {
				columns.add(resultSet.getString(8));
			}
			return columns;
		} finally {
			if (resultSet != null)
				resultSet.close();
		}
	}
}
