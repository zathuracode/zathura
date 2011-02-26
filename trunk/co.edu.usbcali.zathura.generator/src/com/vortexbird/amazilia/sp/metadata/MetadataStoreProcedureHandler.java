package com.vortexbird.amazilia.sp.metadata;

import java.sql.Connection;
import java.util.List;

public interface MetadataStoreProcedureHandler {

	/**
	 * Return a List with the function and store procedure names
	 * 
	 * @param connection
	 *            the connection to use
	 * @param strSchema
	 *            the schema name
	 * @return List<String> procedureNames
	 */
	public abstract List<String> getStoredProcedureNames(Connection connection,String strSchema,String filter);

	/**
	 * Return a List with the schemas of the connection database
	 * 
	 * @param connection
	 *            the connection to use
	 *
	 * @return List<String> schemaNames
	 */
	public abstract List<String> getSchemaNames(Connection connection);

	/**
	 * Return a List with the names of the arguments from the given store
	 * procedure or function name
	 * 
	 * @param connection
	 *            the connection to use
	 * @param strProcedureName
	 *            the store procedure or function name
	 * @return List<String> procedureArguments
	 */
	public abstract List<String> getStoredProceduresArgumentNames(Connection connection, String strProcedureName);

	/**
	 * Return a List with the names of the arguments types from the given store
	 * procedure or function name
	 * 
	 * @param connection
	 *            the connection to use
	 * @param strProcedureName
	 *            the store procedure or function name
	 * @return List<String> procedureArguments
	 */
	public abstract List<String> getStoredProceduresArgumentTypeNames(Connection connection, String strProcedureName);

	/**
	 * Return a List with the name of the type of return (in, out, in/out) of
	 * the given store procedure or function name
	 * 
	 * @param connection
	 *            the connection to use
	 * @param strProcedureName
	 *            the store procedure or function name
	 * @return List<String> procedureArguments
	 */
	public abstract List<String> getStoredProceduresIsInOutArgument(
			Connection connection, String strProcedureName);

	/**
	 * Return an ProcedureBean object which contain the metadata of the given
	 * store procedure or function name
	 * 
	 * @param connection
	 *            the connection to use
	 * @param strProcedureName
	 *            the store procedure or function name
	 * @return ProcedureBean procedureBean
	 */
	public abstract ProcedureBean getProcedureMetadata(Connection connection,
			String strProcedureName);

}