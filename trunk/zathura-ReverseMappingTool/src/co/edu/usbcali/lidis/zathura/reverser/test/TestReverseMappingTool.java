package co.edu.usbcali.lidis.zathura.reverser.test;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.openjpa.jdbc.meta.ReverseMappingTool;

/**
 * 
 * @author diegomez
 *	
 */
public class TestReverseMappingTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String argumentos[]=new String[24];
		
		//-directory/-d <output directory>: The directory where all generated code should be placed. Defaults to the current directory.
		argumentos[0]="-directory";
		argumentos[1]="/home/diegomez/Workspaces/workspaceZathura/demoOpenJPA/src/";
		
		//-package/-pkg <package name>: The name of the package for all generated classes. Defaults to no package.
		argumentos[2]="-package";
		argumentos[3]="co.edu.usbcali.banco.modelo";
		
		//-useSchemaName/-sn <true/t | false/f>: Set this flag to true to include the schema name as part of the generated class name for each table.
		argumentos[4]="-useGenericCollections";
		argumentos[5]="true";
		
		//-useSchemaName/-sn <true/t | false/f>: Set this flag to true to include the schema name as part of the generated class name for each table.
		argumentos[6]="-useSchemaName";
		argumentos[7]="false";
		
		//-blobAsObject/-bo <true/t | false/f>: Set to true to make all binary columns map to Object rather than byte[].
		argumentos[8]="-blobAsObject";
		argumentos[9]="true";
		
		//-nullableAsObject/-no <true/t | false/f>: Set to true to make all nullable columns map to object types; columns that would normally map to a primitive will map to the appropriate wrapper type instead.
		argumentos[10]="-nullableAsObject";
		argumentos[11]="true";
	
		//-typeMap/-typ <types>: Default mapping of SQL type names to Java classes.
		argumentos[12]="-typeMap";
		argumentos[13]= "INTEGER=java.lang.Integer, " +
						"NUMBER=java.lang.Long, " +
						"TINYINT=java.lang.Integer, " +
						"NUMERIC(10,0)=java.lang.Integer, " +
						"NUMERIC(10,2)=java.math.BigDecimal," +
						"DECIMAL=java.lang.Double, " +
						"FLOAT=java.lang.Float, " +
						"SMALLINT=java.lang.Integer, " +
						"BIGINT=java.lang.Integer";
		
		//-annotations/-ann <true/t | false/f>: Set to true to generate JPA annotations in generated code.
		argumentos[14]="-annotations"; 
		argumentos[15]="true";
		
		//-accessType/-access <field | property>: Change access type for generated annotations. Defaults to field access.
		argumentos[16]="-accessType";
		argumentos[17]="property";
		
		//-metadata/-md <class | package | none>: Specify the level the metadata should be generated at. Defaults to generating a single package-level metadata file.
		argumentos[18]="-metadata";
		argumentos[19]="none";
		
		//-properties
		argumentos[20]="-properties";
		argumentos[21]="/home/diegomez/Workspaces/workspaceZathura/zathura-ReverseMappingTool/src/META-INF/my-persistence.xml";
		
		//-schemas/-s <schemas and tables>: Comma-separated list of schemas and tables to reverse-map.
		argumentos[22]="-schemas";
		argumentos[23]="BANCO.CLIENTES, BANCO.TIPOS_DOCUMENTOS, BANCO.CONSIGNACIONES, BANCO.CUENTAS, BANCO.RETIROS, BANCO.TIPOS_USUARIOS, BANCO.USUARIOS";
		
		

		
		for (String string : argumentos) {
			System.out.println(string);
		}		
		
		
		try {
			ReverseMappingTool.main(argumentos);
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}

	}

}
