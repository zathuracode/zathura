package org.zathuracode.generator.jee.jpa.primecentric.engine;

import org.apache.velocity.VelocityContext;
import org.zathuracode.metadata.model.MetaData;
import org.zathuracode.metadata.model.MetaDataModel;

public interface IZathuraTemplate {

	/**
	 * Do template.
	 *
	 * @param hdLocation the hd location
	 * @param metaDataModel the meta data model
	 * @param jpaPckgName the jpa pckg name
	 * @param projectName the project name
	 * @param specificityLevel the specificity level
	 */
	public void doTemplate(String hdLocation, MetaDataModel metaDataModel, String jpaPckgName, String projectName, Integer specificityLevel);

	/**
	 * Do dao.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doDao(MetaData metaData, VelocityContext context, String hdLocation);

	/**
	 * Do persitence xml.
	 *
	 * @param dataModel the data model
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doPersitenceXml(MetaDataModel dataModel, VelocityContext context, String hdLocation);

	/**
	 * Do entity manager.
	 *
	 * @param dataModel the data model
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doEntityManager(MetaDataModel dataModel, VelocityContext context, String hdLocation);

	/**
	 * Do dao factory.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doDaoFactory(MetaDataModel metaData, VelocityContext context, String hdLocation);

	/**
	 * Do logic.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 * @param modelName the model name
	 */
	public void doLogic(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName);

	/**
	 * Do business delegator.
	 *
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 */
	public void doBusinessDelegator(VelocityContext context, String hdLocation, MetaDataModel dataModel);

	/**
	 * Do jsp.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 */
	public void doJsp(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel);

	/**
	 * Do jsp initial menu.
	 *
	 * @param dataModel the data model
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doJspInitialMenu(MetaDataModel dataModel, VelocityContext context, String hdLocation);

	/**
	 * Do faces config.
	 *
	 * @param dataModel the data model
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context, String hdLocation);

	/**
	 * Do dto.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 * @param modelName the model name
	 */
	public void doDto(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName);

	/**
	 * Do exceptions.
	 *
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doExceptions(VelocityContext context, String hdLocation);

	/**
	 * Do utilites.
	 *
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 * @param modelName the model name
	 */
	public void doUtilites(VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName);

	/**
	 * Do jsp facelets.
	 *
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doJspFacelets(VelocityContext context, String hdLocation);
	
	/**
	 * Do back end beans.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 */
	
	public void doBackingBeans(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel) ;
		
	

	
	
}
