package co.edu.usbcali.lidis.zathura.reverse.test;

import java.util.List;
import java.util.Properties;

//import co.edu.usbcali.lidis.zathura.eclipse.plugin.reverse.utilities.ConfigEclipsePluginPath;
import co.edu.usbcali.lidis.zathura.reverse.engine.IZathuraJPAReverseEngineering;
import co.edu.usbcali.lidis.zathura.reverse.engine.ZathuraJPAReverseEngineering;
import co.edu.usbcali.lidis.zathura.reverse.utilities.ReverseEngineeringUtil;

public class TestZathuraJPAReverseEngineering {

	public static void main(String[] args) {
		Properties connectionProperties = new Properties();

//		String connectionDriverClass = "com.mysql.jdbc.Driver";
//		String connectionUrl = "jdbc:mysql://localhost:3306/mascotas";
//		String defaultSchema = "mascotas";
//		String connectionUsername = "root";
//		String connectionPassword = "root";
//		String companyDomainName = "co.edu.usbcali.mascotas";
//		String connectionDriverJarPath = "E:/WORKSPACE/mascotas/WebRoot/WEB-INF/lib/mysql-connector-java-3[1].1.12-bin.jar";
//		String destinationDirectory = "E:/WORKSPACE/mascotas/src/";
//		String matchSchemaForTables = null;
//		List<String> tablesList = null;
		
									  
		String connectionDriverClass = "oracle.jdbc.driver.OracleDriver";
		String connectionUrl = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String defaultSchema = null;
		String connectionUsername = "banco";
		String connectionPassword = "banco";
		String companyDomainName = "co.edu.usbcali.banco.modelo";
		String connectionDriverJarPath = "/home/diegomez/Software/jdbc/oracle11g/ojdbc6.jar";
		String destinationDirectory = "/home/diegomez/Workspaces/runtime-EclipseApplication/demoBanco/src/";
		String matchSchemaForTables = "BANCO";
		List<String> tablesList = ReverseEngineeringUtil.fillTableList();
		Boolean makeItXml = false;
		

		connectionProperties.put("connectionDriverClass", connectionDriverClass);
		connectionProperties.put("connectionUrl", connectionUrl);
		connectionProperties.put("defaultSchema", defaultSchema == null ? "" : defaultSchema);
		connectionProperties.put("connectionUsername", connectionUsername);
		connectionProperties.put("connectionPassword", connectionPassword);
		connectionProperties.put("companyDomainName", companyDomainName);
		connectionProperties.put("matchSchemaForTables", matchSchemaForTables == null ? "" : matchSchemaForTables);

		connectionProperties.put("connectionDriverJarPath",	connectionDriverJarPath);
		
		connectionProperties.put("destinationDirectory",destinationDirectory);

		connectionProperties.put("makeItXml", makeItXml == true ? "True": false);
		//ReverseUtil.setFullPath(ReverseUtil.projectPathInConsole());

		IZathuraJPAReverseEngineering mappingTool = new ZathuraJPAReverseEngineering();
		mappingTool.makePojosJPA_V1_0(connectionProperties, tablesList);

	}
	
	public static void main() {
		//ConfigEclipsePluginPath.getInstance();
		main(null);

	}

}
