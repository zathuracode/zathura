package org.zathuracode.metadata.test;

import org.apache.log4j.Logger;
import org.zathuracode.metadata.exceptions.MetaDataReaderNotFoundException;
import org.zathuracode.metadata.model.Member;
import org.zathuracode.metadata.model.MetaData;
import org.zathuracode.metadata.model.MetaDataModel;
import org.zathuracode.metadata.model.OneToManyMember;
import org.zathuracode.metadata.reader.IMetaDataReader;
import org.zathuracode.metadata.reader.MetaDataReaderFactory;


// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 */
public class TestJPAEntityLoaderEngine {

	/** The log. */
	private static Logger log = Logger.getLogger(TestJPAEntityLoaderEngine.class);

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {

		String path = "/Users/dgomez/Workspaces/runtime-EclipseApplication/pqrsWeb/build/classes/";
		String pckgName = "com.vortexbird.demo.modelo";

		IMetaDataReader entityLoader;
		try {
			entityLoader = MetaDataReaderFactory.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);

			// MetaDataModel metaDataModel=entityLoader.loadMetaDataModel();
			MetaDataModel metaDataModel = entityLoader.loadMetaDataModel(path, pckgName);

			for (MetaData metaData : metaDataModel.getTheMetaData()) {
				log.info("-----------------------------------------------------");
				log.info("Entity name:" + metaData.getName());
				log.info("Entity name:" + metaData.getRealClassName());
				log.info("Primary Key:" + metaData.getPrimaryKey().getName());
				for (Member member : metaData.getProperties()) {
					log.info("Member name:" + member.getName());
					log.info("Member type:" + member.getType());
					if (member instanceof OneToManyMember) {
						OneToManyMember oneToManyMember = (OneToManyMember) member;
						log.info("Member CollectionType:" + oneToManyMember.getCollectionType());
					}
				}
			}
		} catch (MetaDataReaderNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
