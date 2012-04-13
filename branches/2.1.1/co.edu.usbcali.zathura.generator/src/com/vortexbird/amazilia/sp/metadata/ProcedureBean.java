package com.vortexbird.amazilia.sp.metadata;

import java.util.List;

/**
 * This class encapsule the metadata of the a store procedure or function on the
 * ProcedureBean
 * 
 * @author Hassan Hammad
 */
public class ProcedureBean {
	private String strProcedureName;
	private int iColumnCount;
	private List<String> columnNames;
	private List<String> columnTypeNames;

	private List<String> argumentsNames;
	private List<String> argumentsTypeNames;
	private List<String> isInOutArguments;
	private int iTotalArgumentsCount;

	private String strCallString = "";

	/**
	 * Constructor class
	 */
	public ProcedureBean() {
		super();
	}

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
	 * @return the argumentsNames
	 */
	public List<String> getArgumentsNames() {
		return argumentsNames;
	}

	/**
	 * @param argumentsNames
	 *            the argumentsNames to set
	 */
	public void setArgumentsNames(List<String> argumentsNames) {
		this.argumentsNames = argumentsNames;
	}

	/**
	 * @return the argumentsTypeNames
	 */
	public List<String> getArgumentsTypeNames() {
		return argumentsTypeNames;
	}

	/**
	 * @param argumentsTypeNames
	 *            the argumentsTypeNames to set
	 */
	public void setArgumentsTypeNames(List<String> argumentsTypeNames) {
		this.argumentsTypeNames = argumentsTypeNames;
	}

	/**
	 * @return the columnNames
	 */
	public List<String> getColumnNames() {
		return columnNames;
	}

	/**
	 * @param columnNames
	 *            the columnNames to set
	 */
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * @return the columnTypeNames
	 */
	public List<String> getColumnTypeNames() {
		return columnTypeNames;
	}

	/**
	 * @param columnTypeNames
	 *            the columnTypeNames to set
	 */
	public void setColumnTypeNames(List<String> columnTypeNames) {
		this.columnTypeNames = columnTypeNames;
	}

	/**
	 * @return the iColumnCount
	 */
	public int getIColumnCount() {
		return iColumnCount;
	}

	/**
	 * @param columnCount
	 *            the iColumnCount to set
	 */
	public void setIColumnCount(int columnCount) {
		iColumnCount = columnCount;
	}

	/**
	 * @return the isInOutArguments
	 */
	public List<String> getIsInOutArguments() {
		return isInOutArguments;
	}

	/**
	 * @param isInOutArguments
	 *            the isInOutArguments to set
	 */
	public void setIsInOutArguments(List<String> isInOutArguments) {
		this.isInOutArguments = isInOutArguments;
	}

	/**
	 * @return the iTotalArgumentsCount
	 */
	public int getITotalArgumentsCount() {
		return iTotalArgumentsCount;
	}

	/**
	 * @param totalArgumentsCount
	 *            the iTotalArgumentsCount to set
	 */
	public void setITotalArgumentsCount(int totalArgumentsCount) {
		iTotalArgumentsCount = totalArgumentsCount;
	}

	/**
	 * @return the strCallString
	 */
	public String getStrCallString() {
		return strCallString;
	}

	/**
	 * @param strCallString
	 *            the strCallString to set
	 */
	public void setStrCallString(String strCallString) {
		this.strCallString = strCallString;
	}

	/**
	 * @return the strProcedureName
	 */
	public String getStrProcedureName() {
		return strProcedureName;
	}

	/**
	 * @param strProcedureName
	 *            the strProcedureName to set
	 */
	public void setStrProcedureName(String strProcedureName) {
		this.strProcedureName = strProcedureName;
	}

}
