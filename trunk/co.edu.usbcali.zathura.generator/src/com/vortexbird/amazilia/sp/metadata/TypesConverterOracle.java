package com.vortexbird.amazilia.sp.metadata;

import oracle.jdbc.OracleTypes;

public class TypesConverterOracle {

	public TypesConverterOracle() {
		super();
	}

	/**
	 * Convert an oracle data type in an java datatype
	 * 
	 * @param OracleType
	 *            oracle type to parse
	 * @return java type
	 */
	public static String parseJavaType(String OracleType) {
		String javaType = "";
		if (OracleType.equalsIgnoreCase("NUMBER")
				|| OracleType.equalsIgnoreCase("NUMERIC")
				|| OracleType.equalsIgnoreCase("LONG")) {
			javaType = "java.lang.Long";
		} else {
			if (OracleType.equalsIgnoreCase("VARCHAR2")
					|| OracleType.equalsIgnoreCase("VARCHAR")) {
				javaType = "java.lang.String";
			} else {
				if (OracleType.equalsIgnoreCase("DATE")
						|| OracleType.equalsIgnoreCase("TIMESTAMP")) {
					javaType = "java.sql.Timestamp";
				} else {
					if (OracleType.equalsIgnoreCase("CHAR")) {
						javaType = "char";
					} else {
						if (OracleType.equalsIgnoreCase("FLOAT")) {
							javaType = "java.lang.Float";
						} else {
							if (OracleType.equalsIgnoreCase("INTEGER")
									|| OracleType.equalsIgnoreCase("INT")) {
								javaType = "int";
							} else {
								if (OracleType.equalsIgnoreCase("DECIMAL")) {
									javaType = "java.math.BigDecimal";
								}
							}
						}
					}
				}
			}
		}
		return javaType;
	}

	public static int parseOracleType(String strOracleType) {
		int iRetorno = 0;
		if (strOracleType.equalsIgnoreCase("REF CURSOR")) {
			iRetorno = OracleTypes.CURSOR;
		} else {
			if (strOracleType.equalsIgnoreCase("NUMBER")) {
				iRetorno = OracleTypes.NUMBER;
			} else {
				if (strOracleType.equalsIgnoreCase("NUMERIC")) {
					iRetorno = OracleTypes.NUMERIC;
				} else {
					if (strOracleType.equalsIgnoreCase("NUMBER")) {
						iRetorno = OracleTypes.NUMBER;
					} else {
						if (strOracleType.equalsIgnoreCase("VARCHAR2")
								|| strOracleType.equalsIgnoreCase("VARCHAR")) {
							iRetorno = OracleTypes.VARCHAR;
						} else {
							if (strOracleType.equalsIgnoreCase("DATE")) {
								iRetorno = OracleTypes.DATE;
							} else {
								if (strOracleType.equalsIgnoreCase("TIMESTAMP")) {
									iRetorno = OracleTypes.TIMESTAMP;
								} else {
									if (strOracleType.equalsIgnoreCase("CHAR")) {
										iRetorno = OracleTypes.CHAR;
									} else {
										if (strOracleType
												.equalsIgnoreCase("FLOAT")) {
											iRetorno = OracleTypes.FLOAT;
										} else {
											if (strOracleType
													.equalsIgnoreCase("INTEGER")
													|| strOracleType
															.equalsIgnoreCase("INT")) {
												iRetorno = OracleTypes.INTEGER;
											} else {
												if (strOracleType
														.equalsIgnoreCase("DECIMAL")) {
													iRetorno = OracleTypes.DECIMAL;
												} else {
													if (strOracleType
															.equalsIgnoreCase("")) {
														iRetorno = OracleTypes.NULL;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return iRetorno;
	}
}
