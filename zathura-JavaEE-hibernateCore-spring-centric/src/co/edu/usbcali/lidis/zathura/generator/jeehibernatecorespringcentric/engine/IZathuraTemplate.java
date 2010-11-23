package co.edu.usbcali.lidis.zathura.generator.jeehibernatecorespringcentric.engine;

/**
 * 
 * @author William Altuzarra Noriega
 *
 */
import org.apache.velocity.VelocityContext;

import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;

public interface IZathuraTemplate {

	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel, String domainName);


	public void doDaoSpringXMLHibernate(MetaData metaData, VelocityContext context,
			String hdLocation);

	public void doLogicSpringXMLHibernate(MetaData metaData, VelocityContext context,
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
	
	public void doSpringContextConfFiles(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) ;

}
