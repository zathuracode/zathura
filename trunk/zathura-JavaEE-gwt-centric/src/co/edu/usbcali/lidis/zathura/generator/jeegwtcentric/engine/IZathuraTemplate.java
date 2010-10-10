package co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.engine;

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
	
	public void doWebXML(MetaDataModel dataModel,
			VelocityContext context, String hdLocation);

	public void doGeneralEntryPoint(VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String projectName);
	
	public void doEntryPoint(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String projectName);
	
	public void doHTML(VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String projectName);
	
	public void doDataServiceImpl(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel);

	//TODO Cambiar Nombre por lo de GWT
//	public void doJsp(MetaData metaData, VelocityContext context,
//			String hdLocation, MetaDataModel dataModel);

	//TODO Cambiar Nombre por lo de GWT
//	public void doJspInitialMenu(MetaDataModel dataModel,
//			VelocityContext context, String hdLocation);

	//TODO Cambiar Nombre por lo de GWT
//	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context,
//			String hdLocation);

	public void doDto(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel);

	public void doExceptions(VelocityContext context, String hdLocation);

	public void doUtilites(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName);

	//TODO Cambiar Nombre por lo de GWT
//	public void doJspFacelets(VelocityContext context, String hdLocation);

}
