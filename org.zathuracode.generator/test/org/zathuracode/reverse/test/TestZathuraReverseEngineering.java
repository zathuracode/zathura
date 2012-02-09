package org.zathuracode.reverse.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.zathuracode.reverse.engine.IZathuraReverseEngineering;
import org.zathuracode.reverse.engine.ZathuraReverseEngineering;


// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @author William Altuzarra Noriega (williamaltu@gmail.com)
 * @version 1.0
 */
public class TestZathuraReverseEngineering {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		Properties connectionProperties = new Properties();

		// String connectionDriverClass = "com.mysql.jdbc.Driver";
		// String connectionUrl = "jdbc:mysql://localhost:3306/mascotas";
		// String defaultSchema = "mascotas";
		// String connectionUsername = "root";
		// String connectionPassword = "root";
		// String companyDomainName = "co.edu.usbcali.mascotas";
		// String connectionDriverJarPath =
		// "E:/WORKSPACE/mascotas/WebRoot/WEB-INF/lib/mysql-connector-java-3[1].1.12-bin.jar";
		// String destinationDirectory = "E:/WORKSPACE/mascotas/src/";
		// String matchSchemaForTables = null;
		// List<String> tablesList = null;

		String connectionDriverClass = "oracle.jdbc.driver.OracleDriver";
		String connectionUrl = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String defaultSchema = null;
		String connectionUsername = "banco";
		String connectionPassword = "banco";
		String companyDomainName = "co.edu.usbcali.banco.modelo";
		String connectionDriverJarPath = "/home/Diego Armando Gomez Mosquera/Software/jdbc/oracle11g/ojdbc6.jar";
		String destinationDirectory = "/home/Diego Armando Gomez Mosquera/Workspaces/runtime-EclipseApplication/demoBanco/src/";
		String matchSchemaForTables = "BANCO";
		List<String> tablesList = fillTableList();
		Boolean makeItXml = false;

		connectionProperties.put("connectionDriverClass", connectionDriverClass);
		connectionProperties.put("connectionUrl", connectionUrl);
		connectionProperties.put("connectionUsername", connectionUsername);
		connectionProperties.put("connectionPassword", connectionPassword);
		connectionProperties.put("connectionDriverJarPath", connectionDriverJarPath);

		connectionProperties.put("defaultSchema", defaultSchema == null ? "" : defaultSchema);
		connectionProperties.put("matchSchemaForTables", matchSchemaForTables == null ? "" : matchSchemaForTables);
		connectionProperties.put("companyDomainName", companyDomainName);

		connectionProperties.put("destinationDirectory", destinationDirectory);

		connectionProperties.put("makeItXml", makeItXml == true ? "True" : false);
		// ReverseUtil.setFullPath(ReverseUtil.projectPathInConsole());

		IZathuraReverseEngineering mappingTool = new ZathuraReverseEngineering();
		mappingTool.makePojosJPA_V1_0(connectionProperties, tablesList);

	}

	/**
	 * Fill table list.
	 *
	 * @return the list< string>
	 */
	public static List<String> fillTableList() {
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

	/**
	 * Main.
	 */
	public static void main() {
		// ConfigEclipsePluginPath.getInstance();
		main(null);

	}

}
