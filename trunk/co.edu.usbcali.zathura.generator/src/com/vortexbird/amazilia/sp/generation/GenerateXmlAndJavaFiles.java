package com.vortexbird.amazilia.sp.generation;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;

import com.vortexbird.amazilia.sp.metadata.MetadataStoreProcedureHandler;
import com.vortexbird.amazilia.sp.metadata.ProcedureBean;
import com.vortexbird.amazilia.sp.utilities.Utilities;

// TODO: Auto-generated Javadoc
/**
 * The Class GenerateXmlAndJavaFiles.
 */
public class GenerateXmlAndJavaFiles {

	/**
	 * Generate xml.hbm files from given store procedures or functions in the
	 * given path. Generate an xml.hbm for each store Procedure
	 *
	 * @param connection oracle connection
	 * @param strPath path to generate xml.hbm files
	 * @param procedureNames List<String>with the names of the store procedures
	 * @param metadataHandler the metadata handler
	 */
	public void generateProcedureXmlHbmFile(Connection connection, String strPath, List<String> procedureNames, MetadataStoreProcedureHandler metadataHandler) {
		try {
			for (Iterator iter = procedureNames.iterator(); iter.hasNext();) {
				String strProcedureName = (String) iter.next();
				ProcedureBean procedureBean = metadataHandler.getProcedureMetadata(connection, strProcedureName);
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				stringBuffer.append("\n");
				stringBuffer.append("<hibernate-mapping>");
				stringBuffer.append("\n");
				stringBuffer.append("	<sql-query name=" + "\"" + strProcedureName + "\"" + " callable=\"true" + "\">");
				stringBuffer.append("\n");

				List<String> columnNames = procedureBean.getColumnNames();
				List<String> columnTypeName = procedureBean.getColumnTypeNames();
				for (int i = 0; i < procedureBean.getIColumnCount(); i++) {
					stringBuffer.append("		<return-scalar column=" + "\"" + columnNames.get(i) + "\"" + " type=" + "\"" + columnTypeName.get(i) + "\"/>");
					stringBuffer.append("\n");
				}

				stringBuffer.append("		" + procedureBean.getStrCallString());
				stringBuffer.append("\n");
				stringBuffer.append("	</sql-query>");
				stringBuffer.append("\n");
				stringBuffer.append("</hibernate-mapping>");

				CreateFile.createFile(strPath, FormatCodeWithJalopy.formatNameFile(strProcedureName), "hbm.xml", stringBuffer.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
	}

	/**
	 * Generate xml files and transform this xml to java files using an xsl
	 * file. Java classes are formatted and saved in the given package
	 *
	 * @param connection oracle connection
	 * @param strPath path for the generate files
	 * @param strPackageName name of the package
	 * @param procedureNames the procedure names
	 * @param metadataHandler the metadata handler
	 */
	public void generateJavaClasses(Connection connection, String strPath, String strPackageName, List<String> procedureNames,
			MetadataStoreProcedureHandler metadataHandler) {

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer;
		// MetadataHandler metadataHandler =
		// MetadataHandlerFactory.getInstace().getHandler(GetConnection.getDataBase().getName());
		Date date = new Date(System.currentTimeMillis());
		try {
			for (Iterator iter = procedureNames.iterator(); iter.hasNext();) {
				String strProcedureName = (String) iter.next();
				ProcedureBean procedureBean = metadataHandler.getProcedureMetadata(connection, strProcedureName);
				List<String> columnNames = procedureBean.getColumnNames();
				List<String> columnTypeName = procedureBean.getColumnTypeNames();
				List<String> isInOutArguments = procedureBean.getIsInOutArguments();
				List<String> argumentsTypeNames = procedureBean.getArgumentsTypeNames();
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
				stringBuffer.append("\n");
				stringBuffer.append("<class>");
				stringBuffer.append("\n");
				stringBuffer.append("	<packageName>" + strPackageName + "</packageName>");
				stringBuffer.append("\n");
				stringBuffer.append("	<creationDate>" + date + "</creationDate>");
				stringBuffer.append("\n");
				stringBuffer.append("	<className>" + FormatCodeWithJalopy.formatNameFile(strProcedureName) + "</className>");
				stringBuffer.append("\n");
				stringBuffer.append("	<procedureName>" + strProcedureName + "</procedureName>");
				stringBuffer.append("\n");
				stringBuffer.append("	<attributes>");
				stringBuffer.append("\n");

				for (int i = 0; i < procedureBean.getIColumnCount(); i++) {
					stringBuffer.append("		<attribute>");
					stringBuffer.append("\n");
					stringBuffer.append("			<name>" + columnNames.get(i) + "</name>");
					stringBuffer.append("\n");
					stringBuffer.append("			<type>" + columnTypeName.get(i) + "</type>");
					stringBuffer.append("\n");
					stringBuffer.append("		</attribute>");
					stringBuffer.append("\n");
				}
				stringBuffer.append("	</attributes>");
				stringBuffer.append("\n");
				stringBuffer.append("	<setQuerys>");
				stringBuffer.append("\n");

				if (procedureBean.getIsInOutArguments().size() > 0) {
					for (int j = 0; j < isInOutArguments.size(); j++) {
						if (!isInOutArguments.get(j).equalsIgnoreCase("OUT")) {
							if (argumentsTypeNames.get(j).equalsIgnoreCase("NUMBER") || argumentsTypeNames.get(j).equalsIgnoreCase("NUMERIC")) {
								stringBuffer.append("		<setQuery>");
								stringBuffer.append("\n");
								stringBuffer.append("			<setAttribute>setInteger(" + j + ",0)</setAttribute>");
								stringBuffer.append("\n");
								stringBuffer.append("		</setQuery>");
								stringBuffer.append("\n");
							}
							if (argumentsTypeNames.get(j).equalsIgnoreCase("VARCHAR2") || argumentsTypeNames.get(j).equalsIgnoreCase("VARCHAR")) {
								stringBuffer.append("		<setQuery>");
								stringBuffer.append("\n");
								stringBuffer.append("			<setAttribute>setString(" + j + ",\"\")</setAttribute>");
								stringBuffer.append("\n");
								stringBuffer.append("		</setQuery>");
								stringBuffer.append("\n");
							}
							if (argumentsTypeNames.get(j).equalsIgnoreCase("LONG")) {
								stringBuffer.append("		<setQuery>");
								stringBuffer.append("\n");
								stringBuffer.append("			<setAttribute>setLong(" + j + ",0)</setAttribute>");
								stringBuffer.append("\n");
								stringBuffer.append("		</setQuery>");
								stringBuffer.append("\n");
							}
							if (argumentsTypeNames.get(j).equalsIgnoreCase("DATE") || argumentsTypeNames.get(j).equalsIgnoreCase("TIMESTAMP")) {
								stringBuffer.append("		<setQuery>");
								stringBuffer.append("\n");
								stringBuffer.append("			<setAttribute>setTimestamp(" + j + ",new Date())</setAttribute>");
								stringBuffer.append("\n");
								stringBuffer.append("		</setQuery>");
								stringBuffer.append("\n");
							}
						}
					}
				}
				stringBuffer.append("	</setQuerys>");
				stringBuffer.append("\n");
				stringBuffer.append("</class>");

				// format name of the store procedure
				strProcedureName = FormatCodeWithJalopy.formatNameFile(strProcedureName);

				CreateFile.createFile(strPath + "/" + strPackageName, strProcedureName, "xml", stringBuffer.toString());

				// Generate beans
				transformer = tFactory.newTransformer(new StreamSource(GeneratorUtil.getstoreProcedureTemplates() + "procedureBeanGenerator.xsl"));

				transformer.transform(new StreamSource(strPath + "/" + strPackageName + "/" + strProcedureName + ".xml"), new StreamResult(
						new FileOutputStream(strPath + "/" + strPackageName + "/" + "Bean" + strProcedureName + ".java")));

				Utilities.commaSolution(strPath + "/" + strPackageName + "/" + "Bean" + strProcedureName + ".java");

				// Generate Ctrl classes
				transformer = tFactory.newTransformer(new StreamSource(GeneratorUtil.getstoreProcedureTemplates() + "procedureControlGenerator.xsl"));

				transformer.transform(new StreamSource(strPath + "/" + strPackageName + "/" + strProcedureName + ".xml"), new StreamResult(
						new FileOutputStream(strPath + "/" + strPackageName + "/" + "Ctrl" + FormatCodeWithJalopy.formatNameFile(strProcedureName) + ".java")));

				// format java files in given path with the given package name
				FormatCodeWithJalopy.FormatJavaCodeFolder(strPath + "/" + strPackageName + "/");
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			e.getCause();
		}
	}
}
