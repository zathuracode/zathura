package com.vortexbird.amazilia.sp.metadata;


public class MetadataStoreProcedureHandlerFactory 
{	
	private static MetadataStoreProcedureHandlerFactory me = null;//static instance of class
	private static MetadataStoreProcedureHandlerOracle9i metadataStoreProcedureHandlerOracle9i=null;
	private static MetadataStoreProcedureHandlerOracle10g metadataStoreProcedureHandlerOracle10g=null;
	
	private MetadataStoreProcedureHandlerFactory() 
	{
		super();
	}
	
	public static MetadataStoreProcedureHandlerFactory getInstace()
	{
		if(me == null){
			me = new MetadataStoreProcedureHandlerFactory();		
		}			
		return me;
	}
	
	public MetadataStoreProcedureHandler getHandler(String dataBaseName)
	{
		if(dataBaseName.equalsIgnoreCase("Oracle8i/9i (Thin driver)"))
		{
			if(metadataStoreProcedureHandlerOracle9i==null){
				metadataStoreProcedureHandlerOracle9i=new MetadataStoreProcedureHandlerOracle9i();
			}
			return metadataStoreProcedureHandlerOracle9i;
			
		}else if(dataBaseName.equalsIgnoreCase("Oracle 10g (Thin driver)")){
					if(metadataStoreProcedureHandlerOracle10g==null){
						metadataStoreProcedureHandlerOracle10g=new MetadataStoreProcedureHandlerOracle10g();
					}
			return metadataStoreProcedureHandlerOracle10g;
		}
		return null;
	}
}
