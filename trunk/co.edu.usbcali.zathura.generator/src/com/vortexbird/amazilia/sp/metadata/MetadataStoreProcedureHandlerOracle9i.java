package com.vortexbird.amazilia.sp.metadata;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * This class extract the metadata of the store procedure or function Only
 * Oracle 8i and 9i are supported.
 *
 * @author Hassan Hammad
 */
public class MetadataStoreProcedureHandlerOracle9i implements MetadataStoreProcedureHandler {
	
	/**
	 * Constructor Method of class (Empty).
	 */
	public MetadataStoreProcedureHandlerOracle9i() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.vortexbird.amazilia.sp.metadata.MetadataStoreProcedureHandler#getStoredProcedureNames(java.sql.Connection, java.lang.String, java.lang.String)
	 */
	public List<String> getStoredProcedureNames(Connection connection, String strSchema, String filter) {
		DatabaseMetaData dbmt = null;
		ResultSet resultSet = null;
		List<String> procedureNames = new ArrayList<String>();

		try {

			dbmt = connection.getMetaData();

			if (filter == null || filter.equals("") == true) {
				resultSet = dbmt.getProcedures(null, strSchema, "%");
			} else {
				resultSet = dbmt.getProcedures(null, strSchema, filter);
			}

			while (resultSet.next()) {
				String procName = resultSet.getString(3);
				procedureNames.add(procName);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return procedureNames;
	}

	/* (non-Javadoc)
	 * @see com.vortexbird.amazilia.sp.metadata.MetadataStoreProcedureHandler#getSchemaNames(java.sql.Connection)
	 */
	public List<String> getSchemaNames(Connection connection) {
		List<String> schemaNames = new ArrayList<String>();
		try {
			DatabaseMetaData dbmt = connection.getMetaData();
			ResultSet resultSet = dbmt.getSchemas();

			while (resultSet.next()) {
				String schemaName = resultSet.getString(1);
				schemaNames.add(schemaName);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schemaNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geniar.metadata.MetadataHandler#getStoredProceduresArgumentNames(
	 * java.sql.Connection, java.lang.String)
	 */
	public List<String> getStoredProceduresArgumentNames(Connection connection, String strProcedureName) {
		List<String> procedureArguments = new ArrayList<String>();
		PreparedStatement ps;
		ResultSet rs;
		try {
			String sqlQuery = "select USER_ARGUMENTS.ARGUMENT_NAME " + "from USER_ARGUMENTS " + "where USER_ARGUMENTS.OBJECT_NAME = ?";
			ps = connection.prepareStatement(sqlQuery);
			ps.setString(1, strProcedureName);
			rs = ps.executeQuery();

			while (rs.next()) {
				procedureArguments.add(rs.getString("ARGUMENT_NAME"));
			}
			ps.close();
			rs.close();
			// connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return procedureArguments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geniar.metadata.MetadataHandler#getStoredProceduresArgumentTypeNames
	 * (java.sql.Connection, java.lang.String)
	 */
	public List<String> getStoredProceduresArgumentTypeNames(Connection connection, String strProcedureName) {
		List<String> procedureArguments = new ArrayList<String>();
		PreparedStatement ps;
		ResultSet rs;
		try {
			String sqlQuery = "select USER_ARGUMENTS.DATA_TYPE " + "from USER_ARGUMENTS " + "where USER_ARGUMENTS.OBJECT_NAME = ?";
			ps = connection.prepareStatement(sqlQuery);
			ps.setString(1, strProcedureName);
			rs = ps.executeQuery();

			while (rs.next()) {
				procedureArguments.add(rs.getString("DATA_TYPE"));
			}
			ps.close();
			rs.close();
			// connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return procedureArguments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geniar.metadata.MetadataHandler#getStoredProceduresIsInOutArgument
	 * (java.sql.Connection, java.lang.String)
	 */
	public List<String> getStoredProceduresIsInOutArgument(Connection connection, String strProcedureName) {
		List<String> procedureArguments = new ArrayList<String>();
		PreparedStatement ps;
		ResultSet rs;
		try {
			String sqlQuery = "select USER_ARGUMENTS.IN_OUT " + "from USER_ARGUMENTS " + "where USER_ARGUMENTS.OBJECT_NAME = ?";
			ps = connection.prepareStatement(sqlQuery);
			ps.setString(1, strProcedureName);
			rs = ps.executeQuery();

			while (rs.next()) {
				procedureArguments.add(rs.getString("IN_OUT"));
			}
			ps.close();
			rs.close();
			// connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return procedureArguments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geniar.metadata.MetadataHandler#getProcedureMetadata(java.sql.Connection
	 * , java.lang.String)
	 */
	public ProcedureBean getProcedureMetadata(Connection connection, String strProcedureName) {
		ProcedureBean procedureBean = null;
		List<String> columnNames = new ArrayList<String>();
		List<String> columnTypeNames = new ArrayList<String>();

		List<String> argumentsNames;
		List<String> argumentsTypeNames;
		List<String> isInOutArgument;

		String strCallString = "{ ? = call ";
		CallableStatement cs;
		ResultSet rs;
		try {
			procedureBean = new ProcedureBean();
			argumentsNames = getStoredProceduresArgumentNames(connection, strProcedureName);
			argumentsTypeNames = getStoredProceduresArgumentTypeNames(connection, strProcedureName);
			isInOutArgument = getStoredProceduresIsInOutArgument(connection, strProcedureName);

			// Construct call string to call the function or procedure
			strCallString = strCallString + strProcedureName + "(";

			if (isInOutArgument.size() > 0) {
				for (int j = 0; j < isInOutArgument.size(); j++) {
					if (isInOutArgument.get(j).equalsIgnoreCase("IN") || isInOutArgument.get(j).equalsIgnoreCase("IN/OUT")) {
						strCallString = strCallString + "?";
						if (j + 1 < isInOutArgument.size()) {
							strCallString = strCallString + ",";
						}
					}
				}
			}
			strCallString = strCallString + ") }";

			procedureBean.setStrCallString(strCallString);
			procedureBean.setArgumentsNames(argumentsNames);
			procedureBean.setArgumentsTypeNames(argumentsTypeNames);
			procedureBean.setIsInOutArguments(isInOutArgument);
			int iTotalArguments = argumentsNames.size();
			procedureBean.setITotalArgumentsCount(iTotalArguments);
			// Call procedure
			cs = connection.prepareCall(strCallString);

			// Register Out and In/Out parameters and set In parameters
			if (isInOutArgument.size() > 0) {
				// Ask if the first parameter is out and return a ref cursor
				if (isInOutArgument.get(0).equalsIgnoreCase("OUT") && argumentsTypeNames.get(0).equalsIgnoreCase("REF CURSOR")) {
					for (int i = 0; i < isInOutArgument.size(); i++) {
						if (isInOutArgument.get(i).equalsIgnoreCase("OUT")) {
							cs.registerOutParameter(i + 1, TypesConverterOracle.parseOracleType(argumentsTypeNames.get(i)));
						} else {
							if (isInOutArgument.get(i).equalsIgnoreCase("IN")) {
								if (argumentsTypeNames.get(i).equalsIgnoreCase("NUMBER") || argumentsTypeNames.get(i).equalsIgnoreCase("NUMERIC")) {
									cs.setInt(i + 1, 0);
								}
								if (argumentsTypeNames.get(i).equalsIgnoreCase("VARCHAR2") || argumentsTypeNames.get(i).equalsIgnoreCase("VARCHAR")) {
									cs.setString(i + 1, "");
								}
								if (argumentsTypeNames.get(i).equalsIgnoreCase("LONG")) {
									cs.setLong(i + 1, 0);
								}
								if (argumentsTypeNames.get(i).equalsIgnoreCase("DATE") || argumentsTypeNames.get(i).equalsIgnoreCase("TIMESTAMP")) {
									cs.setTimestamp(i + 1, null);
								}
							} else {
								if (isInOutArgument.get(i).equalsIgnoreCase("IN/OUT")) {
									cs.registerOutParameter(i + 1, TypesConverterOracle.parseOracleType(argumentsTypeNames.get(i)));

									if (argumentsTypeNames.get(i).equalsIgnoreCase("NUMBER") || argumentsTypeNames.get(i).equalsIgnoreCase("NUMERIC")) {
										cs.setInt(i + 1, 0);
									}
									if (argumentsTypeNames.get(i).equalsIgnoreCase("VARCHAR2") || argumentsTypeNames.get(i).equalsIgnoreCase("VARCHAR")) {
										cs.setString(i + 1, "");
									}
									if (argumentsTypeNames.get(i).equalsIgnoreCase("LONG")) {
										cs.setLong(i + 1, 0);
									}
									if (argumentsTypeNames.get(i).equalsIgnoreCase("DATE") || argumentsTypeNames.get(i).equalsIgnoreCase("TIMESTAMP")) {
										cs.setTimestamp(i + 1, null);
									}
								}
							}
						}
					}
				} else {
					throw new SQLException("The first parameter of the Store Procedure or Functon " + strProcedureName + " must be OUT and return a REF CURSOR");
				}
			}

			// Execute callable statement
			cs.execute();
			// Set callable statement result into resulSet
			rs = (ResultSet) cs.getObject(1);
			// rollback to the callable statement to undo the call
			connection.rollback();
			// Get the column count of the procedure
			int columnCount = rs.getMetaData().getColumnCount();
			procedureBean.setIColumnCount(columnCount);
			procedureBean.setStrProcedureName(strProcedureName);

			// Get colmun names and types of the store procedure
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rs.getMetaData().getColumnName(i);
				String columnTypeName = rs.getMetaData().getColumnTypeName(i);

				// convert data type
				columnTypeName = TypesConverterOracle.parseJavaType(columnTypeName);
				columnName = columnName.toLowerCase();

				columnNames.add(columnName);
				columnTypeNames.add(columnTypeName);
			}

			procedureBean.setColumnNames(columnNames);
			procedureBean.setColumnTypeNames(columnTypeNames);
			rs.close();
			cs.close();
			// connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			e.getCause();
			e.getMessage();
		}
		return procedureBean;
	}
}
