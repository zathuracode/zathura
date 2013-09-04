package org.zathuracode.eclipse.plugin.generator.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class TestConnectionsUtilsProperties.
 */
public class TestConnectionsUtilsProperties {

	/**
	 * The main method.
	 *
	 * @param args the args
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException the IO exception
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties proper = new java.util.Properties();
		try {
			proper.load(new java.io.FileInputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "zathuraCode.properties"));
		} catch (Exception e) {
			proper.put("banco-name", "banco");
			proper.put("banco-url", "jdbc:oracle:thin:@127.0.0.1:1521:xe");
			proper.put("banco-user", "banco");
			proper.put("banco-password", "banco");
			proper.put("banco-driverClass", "oracle.jdbc.driver.OracleDriver");
			proper.put("banco-jarPath", "D:\\Software\\jdbc\\oracle11g\\ojdbc6.jar");

			proper.put("test-name", "banco");
			proper.put("test-url", "jdbc:oracle:thin:@127.0.0.1:1521:xe");
			proper.put("test-user", "banco");
			proper.put("test-password", "banco");
			proper.put("test-driverClass", "oracle.jdbc.driver.OracleDriver");
			proper.put("test-jarPath", "D:\\Software\\jdbc\\oracle11g\\ojdbc6.jar");
			proper.store(new java.io.FileOutputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "zathuraCode.properties"), "");
		}

		proper.put("coomeva-name", "banco");
		proper.put("coomeva-url", "jdbc:oracle:thin:@127.0.0.1:1521:xe");
		proper.put("coomeva-user", "banco");
		proper.put("coomeva-password", "banco");
		proper.put("coomeva-driverClass", "oracle.jdbc.driver.OracleDriver");
		proper.put("coomeva-jarPath", "D:\\Software\\jdbc\\oracle11g\\ojdbc6.jar");

		proper.store(new java.io.FileOutputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "zathuraCode.properties"), "");

	}

}
