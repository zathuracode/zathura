package co.edu.usbcali.lidis.zathura.generator.jeewebcentric.engine;
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

}
