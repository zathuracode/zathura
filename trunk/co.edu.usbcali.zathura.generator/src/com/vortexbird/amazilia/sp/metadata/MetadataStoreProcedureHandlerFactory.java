package com.vortexbird.amazilia.sp.metadata;

// TODO: Auto-generated Javadoc
/**
 * The Class MetadataStoreProcedureHandlerFactory.
 */
public class MetadataStoreProcedureHandlerFactory {
	
	/** The me. */
	private static MetadataStoreProcedureHandlerFactory me = null;// static
																	// instance
																	// of class
	/** The metadata store procedure handler oracle9i. */
																	private static MetadataStoreProcedureHandlerOracle9i metadataStoreProcedureHandlerOracle9i = null;
	
	/** The metadata store procedure handler oracle10g. */
	private static MetadataStoreProcedureHandlerOracle10g metadataStoreProcedureHandlerOracle10g = null;

	/**
	 * The Constructor.
	 */
	private MetadataStoreProcedureHandlerFactory() {
		super();
	}

	/**
	 * Gets the instace.
	 *
	 * @return the instace
	 */
	public static MetadataStoreProcedureHandlerFactory getInstace() {
		if (me == null) {
			me = new MetadataStoreProcedureHandlerFactory();
		}
		return me;
	}

	/**
	 * Gets the handler.
	 *
	 * @param dataBaseName the data base name
	 * @return the handler
	 */
	public MetadataStoreProcedureHandler getHandler(String dataBaseName) {
		if (dataBaseName.equalsIgnoreCase("Oracle8i/9i (Thin driver)")) {
			if (metadataStoreProcedureHandlerOracle9i == null) {
				metadataStoreProcedureHandlerOracle9i = new MetadataStoreProcedureHandlerOracle9i();
			}
			return metadataStoreProcedureHandlerOracle9i;

		} else if (dataBaseName.equalsIgnoreCase("Oracle 10g (Thin driver)")) {
			if (metadataStoreProcedureHandlerOracle10g == null) {
				metadataStoreProcedureHandlerOracle10g = new MetadataStoreProcedureHandlerOracle10g();
			}
			return metadataStoreProcedureHandlerOracle10g;
		}
		return null;
	}
}
