package org.zathuracode.generator.jee.jpa.gwtcentric.engine;

/**
 * Zathura Generator
 * @author William Altuzarra Noriega (williamaltu@gmail.com)
 * @version 1.0
 */
import org.apache.velocity.VelocityContext;
import org.zathuracode.metadata.model.MetaData;
import org.zathuracode.metadata.model.MetaDataModel;


// TODO: Auto-generated Javadoc
/**
 * The Interface IZathuraTemplate.
 */
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
	 * Do web xml.
	 *
	 * @param dataModel the data model
	 * @param context the context
	 * @param hdLocation the hd location
	 */
	public void doWebXML(MetaDataModel dataModel, VelocityContext context, String hdLocation);

	/**
	 * Do general entry point.
	 *
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 * @param projectName the project name
	 */
	public void doGeneralEntryPoint(VelocityContext context, String hdLocation, MetaDataModel dataModel, String projectName);

	/**
	 * Do entry point.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 * @param projectName the project name
	 */
	public void doEntryPoint(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String projectName);

	/**
	 * Do html.
	 *
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 * @param projectName the project name
	 */
	public void doHTML(VelocityContext context, String hdLocation, MetaDataModel dataModel, String projectName);

	/**
	 * Do data service impl.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 */
	public void doDataServiceImpl(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel);

	// TODO Cambiar Nombre por lo de GWT
	// public void doJsp(MetaData metaData, VelocityContext context,
	// String hdLocation, MetaDataModel dataModel);

	// TODO Cambiar Nombre por lo de GWT
	// public void doJspInitialMenu(MetaDataModel dataModel,
	// VelocityContext context, String hdLocation);

	// TODO Cambiar Nombre por lo de GWT
	// public void doFacesConfig(MetaDataModel dataModel, VelocityContext
	// context,
	// String hdLocation);

	/**
	 * Do dto.
	 *
	 * @param metaData the meta data
	 * @param context the context
	 * @param hdLocation the hd location
	 * @param dataModel the data model
	 */
	public void doDto(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel);

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

	// TODO Cambiar Nombre por lo de GWT
	// public void doJspFacelets(VelocityContext context, String hdLocation);


}
