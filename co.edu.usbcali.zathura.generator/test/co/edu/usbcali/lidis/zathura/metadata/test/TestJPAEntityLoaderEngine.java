package co.edu.usbcali.lidis.zathura.metadata.test;

import org.apache.log4j.Logger;

import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.Member;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.model.OneToManyMember;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;

/**
 * Zathura Generator
 * 
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 */
public class TestJPAEntityLoaderEngine {

	private static Logger log = Logger.getLogger(TestJPAEntityLoaderEngine.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String path = "E:/WORKSPACE/zathura-demoBancoJPA/bin/";
		String pckgName = "co.edu.usbcali.modelo";

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
		}
	}

}
