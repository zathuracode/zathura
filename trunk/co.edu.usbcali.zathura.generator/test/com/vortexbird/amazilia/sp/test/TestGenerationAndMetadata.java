package com.vortexbird.amazilia.sp.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import co.edu.usbcali.lidis.zathura.reverse.utilities.ZathuraReverseEngineeringUtil;

import com.vortexbird.amazilia.sp.generation.CreateFile;
import com.vortexbird.amazilia.sp.generation.GenerateXmlAndJavaFiles;
import com.vortexbird.amazilia.sp.metadata.MetadataStoreProcedureHandler;
import com.vortexbird.amazilia.sp.metadata.MetadataStoreProcedureHandlerFactory;

// TODO: Auto-generated Javadoc
/**
 * Class to test genetation and metadata.
 */
public class TestGenerationAndMetadata {
	
	/**
	 * The main method.
	 *
	 * @param args the args
	 * @throws Exception the exception
	 */
	public static void main(String args[]) throws Exception {
		try {
			String DATA_BASE_TYPE = "Oracle 10g (Thin driver)";
			String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
			String URL = "jdbc:oracle:thin:@10.11.61.10:1521:XE";
			String SCHEMA = "BANCO";
			String USER = "BANCO";
			String PASSWORD = "BANCO";
			String strPath = "C://testPLSQl//";
			String strPackageName = "com.vortexbird.demo.call";

			Connection connection = ZathuraReverseEngineeringUtil.getConnection(URL, DRIVER_CLASS, USER, PASSWORD);

			MetadataStoreProcedureHandler metadata = MetadataStoreProcedureHandlerFactory.getInstace().getHandler(DATA_BASE_TYPE);

			List<String> procedureNames = metadata.getStoredProcedureNames(connection, SCHEMA, "%");

			CreateFile.createFolder(strPath, strPackageName);
			GenerateXmlAndJavaFiles generateXmlAndJavaFiles = new GenerateXmlAndJavaFiles();

			generateXmlAndJavaFiles.generateProcedureXmlHbmFile(connection, strPath + strPackageName, procedureNames, metadata);

			generateXmlAndJavaFiles.generateJavaClasses(connection, strPath, strPackageName, procedureNames, metadata);

		} catch (SQLException e) {
			e.printStackTrace();
			e.getCause();
			e.getMessage();
		}
	}
}
