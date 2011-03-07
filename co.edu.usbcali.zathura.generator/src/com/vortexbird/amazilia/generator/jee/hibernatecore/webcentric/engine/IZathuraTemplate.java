package com.vortexbird.amazilia.generator.jee.hibernatecore.webcentric.engine;

/**
 * Zathura Generator
 * @author William Altuzarra Noriega (williamaltu@gmail.com)
 * @version 1.0
 */
import org.apache.velocity.VelocityContext;

import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;

public interface IZathuraTemplate {

	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel);


	public void doDaoXMLHibernate(MetaData metaData, VelocityContext context,
			String hdLocation);

	public void doHibernateSessionFactory(MetaDataModel dataModel,
			VelocityContext context, String hdLocation);

	public void doXMLHibernateDaoFactory(MetaDataModel metaData,
			VelocityContext context, String hdLocation);

	public void doLogicXMLHibernate(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName);

	public void doBusinessDelegator(VelocityContext context, String hdLocation,
			MetaDataModel dataModel);

	public void doJsp(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel);

	public void doJspInitialMenu(MetaDataModel dataModel,
			VelocityContext context, String hdLocation);

	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context,
			String hdLocation);

	public void doDto(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName);

	public void doExceptions(VelocityContext context, String hdLocation);

	public void doUtilites(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName);

	public void doJspFacelets(VelocityContext context, String hdLocation);

}
