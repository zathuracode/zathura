package com.vortexbird.amazilia.sp.metadata;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * This class encapsule the metadata of the a store procedure or function on the
 * ProcedureBean.
 *
 * @author Hassan Hammad
 */
public class ProcedureBean {
	
	/** The str procedure name. */
	private String strProcedureName;
	
	/** The i column count. */
	private int iColumnCount;
	
	/** The column names. */
	private List<String> columnNames;
	
	/** The column type names. */
	private List<String> columnTypeNames;

	/** The arguments names. */
	private List<String> argumentsNames;
	
	/** The arguments type names. */
	private List<String> argumentsTypeNames;
	
	/** The is in out arguments. */
	private List<String> isInOutArguments;
	
	/** The i total arguments count. */
	private int iTotalArgumentsCount;

	/** The str call string. */
	private String strCallString = "";

	/**
	 * Constructor class.
	 */
	public ProcedureBean() {
		super();
	}

	/**
	 * Result procedure to string.
	 *
	 * @return the string buffer
	 */
	public StringBuffer resultProcedureToString() {
		StringBuffer result = new StringBuffer();
		if (strProcedureName != null && iColumnCount > 0 && columnNames != null && columnTypeNames != null) {
			result.append("Procedure Name: " + strProcedureName + "\n" + "Column Count: " + iColumnCount + "\n");
			if (iColumnCount > 0) {
				for (int i = 0; i < columnNames.size(); i++) {
					result.append("Column Name: " + columnNames.get(i) + " -- Column Type Name: " + columnTypeNames.get(i) + "\n");
				}
			}
			if (iTotalArgumentsCount > 0) {
				result.append("Arguments Count: " + iTotalArgumentsCount + "\n");
				for (int j = 0; j < argumentsNames.size(); j++) {
					result.append("In Argument Name: " + argumentsNames.get(j) + " -- In Argument Type Name: " + argumentsTypeNames.get(j) + "\n");
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * Gets the arguments names.
	 *
	 * @return the argumentsNames
	 */
	public List<String> getArgumentsNames() {
		return argumentsNames;
	}

	/**
	 * Sets the arguments names.
	 *
	 * @param argumentsNames the argumentsNames to set
	 */
	public void setArgumentsNames(List<String> argumentsNames) {
		this.argumentsNames = argumentsNames;
	}

	/**
	 * Gets the arguments type names.
	 *
	 * @return the argumentsTypeNames
	 */
	public List<String> getArgumentsTypeNames() {
		return argumentsTypeNames;
	}

	/**
	 * Sets the arguments type names.
	 *
	 * @param argumentsTypeNames the argumentsTypeNames to set
	 */
	public void setArgumentsTypeNames(List<String> argumentsTypeNames) {
		this.argumentsTypeNames = argumentsTypeNames;
	}

	/**
	 * Gets the column names.
	 *
	 * @return the columnNames
	 */
	public List<String> getColumnNames() {
		return columnNames;
	}

	/**
	 * Sets the column names.
	 *
	 * @param columnNames the columnNames to set
	 */
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * Gets the column type names.
	 *
	 * @return the columnTypeNames
	 */
	public List<String> getColumnTypeNames() {
		return columnTypeNames;
	}

	/**
	 * Sets the column type names.
	 *
	 * @param columnTypeNames the columnTypeNames to set
	 */
	public void setColumnTypeNames(List<String> columnTypeNames) {
		this.columnTypeNames = columnTypeNames;
	}

	/**
	 * Gets the i column count.
	 *
	 * @return the iColumnCount
	 */
	public int getIColumnCount() {
		return iColumnCount;
	}

	/**
	 * Sets the i column count.
	 *
	 * @param columnCount the iColumnCount to set
	 */
	public void setIColumnCount(int columnCount) {
		iColumnCount = columnCount;
	}

	/**
	 * Gets the is in out arguments.
	 *
	 * @return the isInOutArguments
	 */
	public List<String> getIsInOutArguments() {
		return isInOutArguments;
	}

	/**
	 * Sets the is in out arguments.
	 *
	 * @param isInOutArguments the isInOutArguments to set
	 */
	public void setIsInOutArguments(List<String> isInOutArguments) {
		this.isInOutArguments = isInOutArguments;
	}

	/**
	 * Gets the i total arguments count.
	 *
	 * @return the iTotalArgumentsCount
	 */
	public int getITotalArgumentsCount() {
		return iTotalArgumentsCount;
	}

	/**
	 * Sets the i total arguments count.
	 *
	 * @param totalArgumentsCount the iTotalArgumentsCount to set
	 */
	public void setITotalArgumentsCount(int totalArgumentsCount) {
		iTotalArgumentsCount = totalArgumentsCount;
	}

	/**
	 * Gets the str call string.
	 *
	 * @return the strCallString
	 */
	public String getStrCallString() {
		return strCallString;
	}

	/**
	 * Sets the str call string.
	 *
	 * @param strCallString the strCallString to set
	 */
	public void setStrCallString(String strCallString) {
		this.strCallString = strCallString;
	}

	/**
	 * Gets the str procedure name.
	 *
	 * @return the strProcedureName
	 */
	public String getStrProcedureName() {
		return strProcedureName;
	}

	/**
	 * Sets the str procedure name.
	 *
	 * @param strProcedureName the strProcedureName to set
	 */
	public void setStrProcedureName(String strProcedureName) {
		this.strProcedureName = strProcedureName;
	}

}
