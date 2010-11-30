package co.edu.usbcali.lidis.zathura.generator.jee.jpa.webcentric.engine;


import org.apache.velocity.VelocityContext;

import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;


/**
 * Zathura Generator
 * @author William Altuzarra (williamaltu@gmail.com)
 * @version 1.0
 */
public interface IZathuraTemplate {
	
	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel);

	public void doDao(MetaData metaData, VelocityContext context,
			String hdLocation);

	public void doPersitenceXml(MetaDataModel dataModel,
			VelocityContext context, String hdLocation);

	public void doEntityManager(MetaDataModel dataModel,
			VelocityContext context, String hdLocation);

	public void doDaoFactory(MetaDataModel metaData, VelocityContext context,
			String hdLocation);

	public void doLogic(MetaData metaData, VelocityContext context,
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
