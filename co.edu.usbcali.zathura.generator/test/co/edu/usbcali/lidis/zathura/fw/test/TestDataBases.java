package co.edu.usbcali.lidis.zathura.fw.test;

import java.net.MalformedURLException;
import java.sql.SQLException;

import net.sourceforge.squirrel_sql.fw.persist.ValidationException;
import net.sourceforge.squirrel_sql.fw.sql.ISQLAlias;
import net.sourceforge.squirrel_sql.fw.sql.ISQLConnection;
import net.sourceforge.squirrel_sql.fw.sql.ISQLDriver;
import net.sourceforge.squirrel_sql.fw.sql.ITableInfo;
import net.sourceforge.squirrel_sql.fw.sql.SQLDriver;
import net.sourceforge.squirrel_sql.fw.sql.SQLDriverManager;

import com.vortexbird.amazilia.fw.AmaziliaSQLAlias;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDataBases.
 */
public class TestDataBases {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {

		try {

			String className = null;
			String url = null;
			String user = null;
			String password = null;
			/** Oracle */
			className = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
			user = "banco";
			password = "banco";

			System.out.println("------------------------------------ ORACLE ------------------------------------ ");
			// testMetaData(className, url, user, password);

			/** JT400 */
			className = "com.ibm.as400.access.AS400JDBCDriver";
			url = "jdbc:as400://192.1.4.164:8471/ZATURADB2";
			user = "dnxout01";
			password = "eszyjm1";

			// System.out.println("------------------------------------ JT400 ------------------------------------ ");
			testMetaData(className, url, user, password);

			/** mysql */
			className = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://127.0.0.1:3306/banco";
			user = "root";
			password = "diego";

			System.out.println("------------------------------------ mysql ------------------------------------ ");
			// testMetaData(className, url, user, password);

			/** postgres */
			className = "org.postgresql.Driver";
			url = "jdbc:postgresql://127.0.0.1:5432/postgres";
			user = "vortex";
			password = "vortex";

			System.out.println("------------------------------------ postgres ------------------------------------ ");
			// testMetaData(className, url, user, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test meta data.
	 *
	 * @param className the class name
	 * @param url the url
	 * @param user the user
	 * @param password the password
	 * @throws ValidationException the validation exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InstantiationException the instantiation exception
	 * @throws MalformedURLException the malformed url exception
	 * @throws SQLException the SQL exception
	 */
	private static void testMetaData(String className, String url, String user, String password) throws ValidationException, ClassNotFoundException,
			IllegalAccessException, InstantiationException, MalformedURLException, SQLException {
		/** Alias we are going to connect to. */
		ISQLAlias alias;
		/** JDBC driver for <TT>_alias</TT>. */
		ISQLDriver sqlDriver;
		SQLDriverManager sqlDriverManager = null;
		// SQLDriverPropertyCollection driverProperties =
		// _alias.getDriverPropertiesClone();
		ISQLConnection sqlConnection;

		String[] types = { "TABLE", "VIEW", "SYNONYM", "ALIAS" };

		String[] listaSchemas = null;
		String[] listaCatalogs = null;
		String catalogName = null;
		String schemaName = null;
		ITableInfo[] tableInfo = null;

		sqlDriverManager = new SQLDriverManager();
		sqlDriver = new SQLDriver();
		alias = new AmaziliaSQLAlias();

		sqlDriver.setDriverClassName(className);
		sqlDriver.setUrl(url);

		alias.setUrl(url);
		alias.setUserName(user);
		alias.setPassword(password);

		sqlConnection = sqlDriverManager.getConnection(sqlDriver, alias, user, password);

		System.out.println(sqlConnection.getAutoCommit());
		System.out.println(sqlConnection.getSQLMetaData().getURL());
		System.out.println(sqlConnection.getSQLMetaData().getDatabaseProductVersion());

		listaCatalogs = sqlConnection.getSQLMetaData().getCatalogs();
		if (listaCatalogs != null && listaCatalogs.length > 0)
			System.out.println("-------- Catalogs --------");
		for (String catalog : listaCatalogs) {
			System.out.println(catalog);
			catalogName = catalog;
			try {
				tableInfo = sqlConnection.getSQLMetaData().getTables(catalog, null, null, types, null);
				if (tableInfo != null && tableInfo.length > 0)
					System.out.println("-------- TABLES --------");
				for (ITableInfo iTableInfo : tableInfo) {
					System.out.println(iTableInfo.getSimpleName());
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}

		}

		listaSchemas = sqlConnection.getSQLMetaData().getSchemas();
		if (listaSchemas != null && listaSchemas.length > 0)
			System.out.println("-------- Schema --------");
		for (String schema : listaSchemas) {
			System.out.println(schema);
			schemaName = schema;
			if (schemaName.equalsIgnoreCase("ZATURADB2") == true || schemaName.equalsIgnoreCase("BANCO") == true) {
				try {
					tableInfo = sqlConnection.getSQLMetaData().getTables(catalogName, schema, null, types, null);
					if (tableInfo != null && tableInfo.length > 0)
						System.out.println("-------- TABLES --------");
					for (ITableInfo iTableInfo : tableInfo) {
						System.out.println(iTableInfo.getSimpleName());
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
	}

}
