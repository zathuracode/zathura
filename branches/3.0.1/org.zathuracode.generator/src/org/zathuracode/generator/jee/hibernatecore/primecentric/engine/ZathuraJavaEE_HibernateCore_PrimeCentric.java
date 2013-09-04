package org.zathuracode.generator.jee.hibernatecore.primecentric.engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.zathuracode.generator.model.IZathuraGenerator;
import org.zathuracode.generator.utilities.GeneratorUtil;
import org.zathuracode.generator.utilities.JalopyCodeFormatter;
import org.zathuracode.jee.hibernatecore.primecentric.utils.IStringBuilder;
import org.zathuracode.jee.hibernatecore.primecentric.utils.IStringBuilderForId;
import org.zathuracode.jee.hibernatecore.primecentric.utils.StringBuilder;
import org.zathuracode.jee.hibernatecore.primecentric.utils.StringBuilderForId;
import org.zathuracode.jee.hibernatecore.primecentric.utils.Utilities;
import org.zathuracode.metadata.model.MetaData;
import org.zathuracode.metadata.model.MetaDataModel;



/**
 * Zathura Generator.
 * @author Andrés Mauricio Cárdenas Pérez (mauriciocardenasp@gmail.com)
 * @version 1.0
 */

public class ZathuraJavaEE_HibernateCore_PrimeCentric implements IZathuraTemplate, IZathuraGenerator {
	private VelocityEngine ve;
	private static final String primeHibernateCentric ;	
	private  static Logger logPrimeHibernate;
	private Properties properties;
	private String virginPackageInHd;
	private String webRootPath;

	static{
		primeHibernateCentric=GeneratorUtil.getPrimeHibernateTemplates();
		logPrimeHibernate= Logger.getLogger(ZathuraJavaEE_HibernateCore_PrimeCentric.class);
	}

	@Override
	public void toGenerate(MetaDataModel metaDataModel, String projectName,
			String folderProjectPath, Properties propiedades) {

		logPrimeHibernate.info("Begin generation to Primefaces + Hibernate ");
		properties=propiedades;
		String jpaPckgName = propiedades.getProperty("jpaPckgName");
		Integer specificityLevel = (Integer) propiedades.get("specificityLevel");
		webRootPath=(propiedades.getProperty("webRootFolderPath"));
		doTemplate(folderProjectPath, metaDataModel, jpaPckgName, projectName, specificityLevel);
		copyLibrarys();
	}

	public void copyLibrarys(){
		String pathIndexJsp = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeHibernateCentric()+"index.jsp";
		String pathWebXml= GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeHibernateCentric()+"WEB-INF"+GeneratorUtil.slash;
		String generatorExtZathuraJavaEEWebPrimeHibernateCentricImages = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeHibernateCentric() + GeneratorUtil.slash + "images"
		+ GeneratorUtil.slash;


		String pathHibernate= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimefacesHibernate()+"core-hibernate3.3"+GeneratorUtil.slash;
		String pathPrimeFaces= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimefacesHibernate()+"primeFaces3.2"+GeneratorUtil.slash;
		String pathLib= properties.getProperty("libFolderPath");
		String pathCss = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeHibernateCentric() + GeneratorUtil.slash + "css"
		+ GeneratorUtil.slash;
		String log4j = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeHibernateCentric() + GeneratorUtil.slash + "log4j"
				+ GeneratorUtil.slash;
		
		// Copy Css
		GeneratorUtil.createFolder(webRootPath + "css");
		GeneratorUtil.copyFolder(pathCss, webRootPath + "css" + GeneratorUtil.slash);
		GeneratorUtil.copyFolder(pathWebXml,webRootPath+"WEB-INF"+GeneratorUtil.slash);
		//create folder images and insert .png
		GeneratorUtil.createFolder(webRootPath + "images");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebPrimeHibernateCentricImages, webRootPath + "images" + GeneratorUtil.slash);
		// create index.jsp
		GeneratorUtil.copy(pathIndexJsp,webRootPath+"index.jsp" );
		//copy libraries
		GeneratorUtil.copyFolder(pathHibernate, pathLib);
		GeneratorUtil.copyFolder(pathPrimeFaces, pathLib);
		// copy to Log4j
		String folderProjectPath = properties.getProperty("folderProjectPath");
		GeneratorUtil.copyFolder(log4j, folderProjectPath + GeneratorUtil.slash);
				

	}

	@Override
	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel) {

		try {
			logPrimeHibernate.info("Initialize VelocityEngine");
			ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			properties.setProperty("file.resource.loader.path", primeHibernateCentric);
			properties.setProperty("file.resource.loader.cache", "false");
			properties.setProperty("file.resource.loader.modificationCheckInterval", "2");
			ve.init(properties);
			logPrimeHibernate.info("End Initialize Velocity Engine");

			VelocityContext context = new VelocityContext();
			MetaDataModel dataModel = metaDataModel;
			List<MetaData> list = dataModel.getTheMetaData();
			IStringBuilderForId stringBuilderForId = new StringBuilderForId(list);
			IStringBuilder stringBuilder = new StringBuilder(list, (StringBuilderForId) stringBuilderForId);
			String packageOriginal = null;
			String virginPackage = null;
			String modelName = null;

			if (specificityLevel.intValue() == 2) {
				try {
					int lastIndexOf = jpaPckgName.lastIndexOf(".");
					packageOriginal = jpaPckgName.substring(0, lastIndexOf);

					int lastIndexOf2 = packageOriginal.lastIndexOf(".") + 1;
					modelName = packageOriginal.substring(lastIndexOf2);

					int virginLastIndexOf = packageOriginal.lastIndexOf(".");
					virginPackage = packageOriginal.substring(0, virginLastIndexOf);
				} catch (Exception e) {
					logPrimeHibernate.error(e.getMessage());
				}
			} else {
				try {
					packageOriginal = jpaPckgName;

					int lastIndexOf2 = packageOriginal.lastIndexOf(".") + 1;
					modelName = jpaPckgName.substring(lastIndexOf2);

					int virginLastIndexOf = packageOriginal.lastIndexOf(".");
					virginPackage = packageOriginal.substring(0, virginLastIndexOf);
				} catch (Exception e) {
					logPrimeHibernate.error(e.getMessage());
				}
			}

			String projectNameClass = (projectName.substring(0, 1)).toUpperCase() + projectName.substring(1, projectName.length());
			context.put("packageOriginal", packageOriginal);
			context.put("virginPackage", virginPackage);
			context.put("package", jpaPckgName);
			context.put("projectName", projectName);
			context.put("modelName", modelName);
			context.put("projectNameClass", projectNameClass);
			virginPackageInHd = GeneratorUtil.replaceAll(virginPackage, ".", GeneratorUtil.slash);
			Utilities.getInstance().buildFolders(virginPackage, hdLocation, specificityLevel, packageOriginal, this.properties);
			// stringBuilderForId.neededIds(list);
			Utilities.getInstance().biuldHashToGetIdValuesLengths(list);

			for (MetaData metaData : list) {

				List<String> imports = Utilities.getInstance().getRelatedClasses(metaData, dataModel);

				if (imports != null) {
					if (imports.size() > 0 && !imports.isEmpty()) {
						context.put("isImports", true);
						context.put("imports", imports);
					} else {
						context.put("isImports", false);
					}
				} else {
					context.put("isImports", false);
				}


				// generacion de nuevos dto
				context.put("variableDto", stringBuilder.getPropertiesDto(list, metaData));
				context.put("propertiesDto",Utilities.getInstance().dtoProperties);
				context.put("memberDto",Utilities.getInstance().nameMemberToDto);
				// generacion de la nueva logica 
				context.put("dtoConvert", stringBuilderForId.dtoConvert(list,metaData));
				context.put("dtoConvert2", stringBuilder.dtoConvert2(list, metaData));

				context.put("finalParamForView", stringBuilder.finalParamForView(list, metaData));
				context.put("finalParamForDtoUpdate", stringBuilder.finalParamForDtoUpdate(list, metaData));
				context.put("finalParamForDtoUpdateOnlyVariables", stringBuilder.finalParamForDtoUpdateOnlyVariables(list, metaData));
				context.put("finalParamForViewVariablesInList", stringBuilder.finalParamForViewVariablesInList(list, metaData));
				context.put("isFinalParamForViewDatesInList", Utilities.getInstance().isFinalParamForViewDatesInList());
				context.put("finalParamForViewDatesInList", Utilities.getInstance().dates);
				context.put("finalParamForIdForViewVariablesInList", stringBuilderForId.finalParamForIdForViewVariablesInList(list, metaData));
				context.put("isFinalParamForIdForViewDatesInList", Utilities.getInstance().isFinalParamForIdForViewDatesInList());
				context.put("finalParamForIdForViewDatesInList", Utilities.getInstance().datesId);
				context.put("finalParamForIdForViewClass", stringBuilderForId.finalParamForIdForViewClass(list, metaData));
				context.put("finalParamForIdClassAsVariablesAsString", stringBuilderForId.finalParamForIdClassAsVariablesAsString(list, metaData));
				context.put("finalParamForViewForSetsVariablesInList", stringBuilder.finalParamForViewForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdForDtoForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoForSetsVariablesInList(list, metaData));
				context.put("finalParamForDtoForSetsVariablesInList", stringBuilder.finalParamForDtoForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdForDtoInViewForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoInViewForSetsVariablesInList(list,
						metaData));

				context.put("finalParamForDtoInViewForSetsVariablesInList", stringBuilder.finalParamForDtoInViewForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdForViewForSetsVariablesInList", stringBuilderForId.finalParamForIdForViewForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdVariablesAsList", stringBuilderForId.finalParamForIdVariablesAsList(list, metaData));

				String finalParam = stringBuilder.finalParam(list, metaData);
				context.put("finalParam", finalParam);
				metaData.setFinamParam(finalParam);

				String finalParamVariables = stringBuilder.finalParamVariables(list, metaData);
				context.put("finalParamVariables", finalParamVariables);
				metaData.setFinamParamVariables(finalParamVariables);

				String finalParamForId = stringBuilderForId.finalParamForId(list, metaData);
				context.put("finalParamForId", stringBuilderForId.finalParamForId(list, metaData));
				metaData.setFinalParamForId(finalParamForId);

				String finalParamForIdVariables = stringBuilderForId.finalParamForIdVariables(list, metaData);
				context.put("finalParamForIdVariables", stringBuilderForId.finalParamForIdVariables(list, metaData));
				metaData.setFinalParamForIdVariables(finalParamForIdVariables);

				context.put("finalParamVariablesAsList", stringBuilder.finalParamVariablesAsList(list, metaData));
				context.put("finalParamVariablesAsList2", stringBuilder.finalParamVariablesAsList2(list, metaData));
				context.put("finalParamVariablesDatesAsList2", stringBuilder.finalParamVariablesDatesAsList2(list, metaData));
				context.put("isFinalParamDatesAsList", Utilities.getInstance().isFinalParamDatesAsList());
				context.put("finalParamDatesAsList", Utilities.getInstance().datesJSP);

				context.put("finalParamForIdClassAsVariables", stringBuilderForId.finalParamForIdClassAsVariables(list, metaData));
				context.put("finalParamForIdClassAsVariables2", stringBuilderForId.finalParamForIdClassAsVariables2(list, metaData));
				context.put("isFinalParamForIdClassAsVariablesForDates", Utilities.getInstance().isFinalParamForIdClassAsVariablesForDates());
				context.put("finalParamForIdClassAsVariablesForDates", Utilities.getInstance().datesIdJSP);

				context.put("finalParamForVariablesDataTablesAsList", stringBuilder.finalParamForVariablesDataTablesAsList(list, metaData));
				context.put("finalParamForVariablesDataTablesForIdAsList", stringBuilderForId.finalParamForVariablesDataTablesForIdAsList(list, metaData));

				if (metaData.isGetManyToOneProperties()) {
					context.put("getVariableForManyToOneProperties", stringBuilder.getVariableForManyToOneProperties(metaData.getManyToOneProperties(), list));
					context.put("getStringsForManyToOneProperties", stringBuilder.getStringsForManyToOneProperties(metaData.getManyToOneProperties(), list));
				}

				context.put("composedKey", false);

				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					context.put("composedKey", true);
					context.put("finalParamForIdClass", stringBuilderForId.finalParamForIdClass(list, metaData));
				}

				context.put("metaData", metaData);
				context.put("dataModel", dataModel);

				doDaoXMLHibernate(metaData, context, hdLocation);
				doBackingBeans(metaData, context, hdLocation, dataModel);
				doJsp(metaData, context, hdLocation, dataModel);
				doLogicXMLHibernate(metaData, context, hdLocation, dataModel, modelName);
				doDto(metaData, context, hdLocation, dataModel, modelName);

			}

			doUtilites(context, hdLocation, dataModel, modelName);
			doExceptions(context, hdLocation);
			doBusinessDelegator(context, hdLocation, dataModel);
			doXMLHibernateDaoFactory(dataModel, context, hdLocation);
			doHibernateSessionFactory(dataModel, context, hdLocation);
			doJspInitialMenu(dataModel, context, hdLocation);
			doFacesConfig(dataModel, context, hdLocation);
			doJspFacelets(context, hdLocation);


		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}




	}

	@Override
	public void doBusinessDelegator(VelocityContext context, String hdLocation,
			MetaDataModel dataModel) {

		try {

			logPrimeHibernate.info("Begin BusinessDelegate PrimeFaces + Hibernate");
			Template businessDelegateTemplate = ve.getTemplate("BusinessDelegatorViewHibernatePrime.vm");
			StringWriter swBusinessDelagate= new StringWriter();
			businessDelegateTemplate.merge(context, swBusinessDelagate);

			String path = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "presentation" + GeneratorUtil.slash
			+ "businessDelegate" + GeneratorUtil.slash;

			FileWriter fwBusinessDelegate = new FileWriter(path+"BusinessDelegatorView" + ".java");
			BufferedWriter bwBusinessDelegate = new BufferedWriter(fwBusinessDelegate);
			bwBusinessDelegate.write(swBusinessDelagate.toString());
			bwBusinessDelegate.close();
			fwBusinessDelegate.close();
			logPrimeHibernate.info("End BusinessDelegate PrimeFaces + Hibernate");
			JalopyCodeFormatter.formatJavaCodeFile(path + "BusinessDelegatorView" + ".java");

		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doDaoXMLHibernate(MetaData metaData, VelocityContext context,
			String hdLocation) {

		try {
			logPrimeHibernate.info("Begin IDao Primefaces + Hibernate");
			Template idaoTemplate = ve.getTemplate("IDAOHibernatePrime.vm");
			StringWriter swDaoTemplate = new StringWriter();
			idaoTemplate.merge(context, swDaoTemplate);

			String path =hdLocation+virginPackageInHd+GeneratorUtil.slash+"dataaccess"+GeneratorUtil.slash+"dao"+GeneratorUtil.slash;
			FileWriter fwIdao = new FileWriter(path+"I"+metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwIdao = new BufferedWriter(fwIdao);
			bwIdao.write(swDaoTemplate.toString());
			bwIdao.close();
			fwIdao.close();
			logPrimeHibernate.info("End IDao Primefaces + Hibernate");

			logPrimeHibernate.info("Begin Dao Primefaces + Hibernate");
			Template daoTemplate = ve.getTemplate("DAOHibernatePrime.vm");
			StringWriter swDao = new StringWriter();
			daoTemplate.merge(context, swDao);
			FileWriter fwDao = new FileWriter(path+metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwDao = new BufferedWriter(fwDao);
			bwDao.write(swDao.toString());
			bwDao.close();
			fwDao.close();
			logPrimeHibernate.info("End Dao Primefaces + hibernate");
			JalopyCodeFormatter.formatJavaCodeFile(path+"I"+metaData.getRealClassName()+"DAO.java");
			JalopyCodeFormatter.formatJavaCodeFile(path+metaData.getRealClassName()+"DAO.java");

		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doDto(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {

		try {
			logPrimeHibernate.info("Begin generation prime+Hibernate");
			Template templateDtoPrime= ve.getTemplate("DtoHibernatePrime.vm");
			StringWriter swDtoPrime = new StringWriter();
			templateDtoPrime.merge(context, swDtoPrime);
			String path = hdLocation+virginPackageInHd+GeneratorUtil.slash+modelName+GeneratorUtil.slash+"dto"+GeneratorUtil.slash;

			FileWriter fwDtoPrime = new FileWriter(path+metaData.getRealClassName()+"DTO.java");
			BufferedWriter bwDtoPrime = new BufferedWriter(fwDtoPrime);
			bwDtoPrime.write(swDtoPrime.toString());
			bwDtoPrime.close();
			swDtoPrime.close();
			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "DTO.java");

			logPrimeHibernate.info("End generation prime + hibernate");

		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doExceptions(VelocityContext context, String hdLocation) {
		try {
			logPrimeHibernate.info("Begin Exception PrimeFaces + Hibernate");
			Template exceptionTemplate = ve.getTemplate("ZMessManager.vm");
			StringWriter swException = new StringWriter();
			exceptionTemplate.merge(context, swException);

			String path = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "exceptions" + GeneratorUtil.slash;
			FileWriter fwException = new FileWriter(path+ "ZMessManager.java");
			BufferedWriter bwException = new BufferedWriter(fwException);
			bwException.write(swException.toString());
			bwException.close();
			fwException.close();
			logPrimeHibernate.info("End Exception PrimeFaces + Hibernate");
			JalopyCodeFormatter.formatJavaCodeFile(path + "ZMessManager.java");
		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context,
			String hdLocation) {

		try {

			logPrimeHibernate.info("Begin FacesContext");
			Template  facesConfigTemplate = ve.getTemplate("faces-configPrimeHibernate.xml.vm");
			StringWriter swFacesConfig = new StringWriter();
			facesConfigTemplate.merge(context, swFacesConfig);

			String path= properties.getProperty("webRootFolderPath") + "WEB-INF" + GeneratorUtil.slash ;
			FileWriter fwFacesConfig = new FileWriter(path+"faces-config.xml");
			BufferedWriter bwFacesConfig = new BufferedWriter(fwFacesConfig);
			bwFacesConfig.write(swFacesConfig.toString());
			bwFacesConfig.close();
			fwFacesConfig.close();
			logPrimeHibernate.info("End FacesContext");

		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doHibernateSessionFactory(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {
		try {
			logPrimeHibernate.info("Begin HibernateSessionFactory");
			Template hibernateTemplate = ve.getTemplate("HibernateSessionFactory.vm");
			StringWriter swHibernateSession = new StringWriter();
			hibernateTemplate.merge(context, swHibernateSession);

			String path=  hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "dataaccess" + GeneratorUtil.slash
			+ "sessionFactory" + GeneratorUtil.slash;
			FileWriter fwHibernate = new FileWriter(path+"HibernateSessionFactory.java");
			BufferedWriter bwHibernate = new BufferedWriter(fwHibernate);
			bwHibernate.write(swHibernateSession.toString());
			bwHibernate.close();
			fwHibernate.close();
			logPrimeHibernate.info("End HibernateSessionFactory");


		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doJsp(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {

		try {
			String path=properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;

			logPrimeHibernate.info("Begin xhtml PrimeFaces + Hibernate");
			Template xhtmlTemplate = ve.getTemplate("XHTMLHibernatePrime.vm");
			StringWriter swXhtml = new StringWriter();
			xhtmlTemplate.merge(context, swXhtml);
			FileWriter fwXhtml = new FileWriter(path+metaData.getRealClassNameAsVariable()+".xhtml");
			BufferedWriter bwXhtml = new BufferedWriter(fwXhtml);
			bwXhtml.write(swXhtml.toString());
			bwXhtml.close();
			fwXhtml.close();
			logPrimeHibernate.info("End xhtml PrimeFaces + Hibernate");

			logPrimeHibernate.info("Begin DataTable PrimeFaces + Hibernate");
			Template dataTable = ve.getTemplate("JSPdataTablesHibernatePrime.vm");
			StringWriter swDataTable = new StringWriter();
			dataTable.merge(context, swDataTable);
			FileWriter fwDataTable = new FileWriter(path+metaData.getRealClassNameAsVariable()+"ListDataTable.xhtml");
			BufferedWriter bfDataTable = new BufferedWriter(fwDataTable);
			bfDataTable.write(swDataTable.toString());
			bfDataTable.close();
			fwDataTable.close();
			logPrimeHibernate.info("End DataTable PrimeFaces + Hibernate");

			logPrimeHibernate.info("Begin DataTableEditable PrimeFaces + Hibernate");
			Template dataTableEditableTemplate = ve.getTemplate("JSPdataTableEditablePrime.vm");
			StringWriter swDataTableEditable = new StringWriter();
			dataTableEditableTemplate.merge(context, swDataTableEditable);
			FileWriter fwDataTableEditable = new FileWriter(path+metaData.getRealClassNameAsVariable()+"ListDataTableEditable.xhtml");
			BufferedWriter bwDataTableEditable = new BufferedWriter(fwDataTableEditable);
			bwDataTableEditable.write(swDataTableEditable.toString());
			bwDataTableEditable.close();
			fwDataTableEditable.close();
			logPrimeHibernate.info("Begin DataTableEditable PrimeFaces + Hibernate");

			Utilities.getInstance().datesJSP = null;
			Utilities.getInstance().datesIdJSP = null;

		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doJspFacelets(VelocityContext context, String hdLocation) {
		try {
			String pathFacelets =  properties.getProperty("webRootFolderPath") + "WEB-INF" + GeneratorUtil.slash + "facelets" + GeneratorUtil.slash;
			logPrimeHibernate.info("Begin Header");
			Template templateHeader = ve.getTemplate("JSPheader.vm");
			StringWriter swHeader = new StringWriter();
			templateHeader.merge(context, swHeader);
			FileWriter fwHeader = new FileWriter(pathFacelets+"header.jspx");
			BufferedWriter bwHeader = new BufferedWriter(fwHeader);
			bwHeader.write(swHeader.toString());
			bwHeader.close();
			fwHeader.close();
			logPrimeHibernate.info("Begin Header");
			
			logPrimeHibernate.info("Begin Footer");
			Template templateFooter = ve.getTemplate("JSPfooter.vm");
			StringWriter swFooter = new StringWriter();
			templateFooter.merge(context, swFooter);
			FileWriter fwFooter = new FileWriter(pathFacelets+"footer.jspx");
			BufferedWriter bwFooter = new BufferedWriter(fwFooter);
			bwFooter.write(swFooter.toString());
			bwFooter.close();
			fwFooter.close();
			logPrimeHibernate.info("End Footer");
			
			logPrimeHibernate.info("Begin Footer initialMenu");
			Template footerInitialMenu = ve.getTemplate("footerInitialMenu.vm");
			StringWriter swFooterInitialMenu = new StringWriter();
			footerInitialMenu.merge(context, swFooterInitialMenu);
			FileWriter fwFooterInitialMenu = new FileWriter(pathFacelets+"footerInitialMenu.jspx");
			BufferedWriter bwFooterInitialMenu = new BufferedWriter(fwFooterInitialMenu);
			bwFooterInitialMenu.write(swFooterInitialMenu.toString());
			bwFooterInitialMenu.close();
			fwFooterInitialMenu.close();
			logPrimeHibernate.info("End Footer initialMenu");
			
			
			String pathCommon = properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			logPrimeHibernate.info("Begin CommonColumnsContens");
			Template templateCommonColumnsContens = ve.getTemplate("CommonColumnsContent.vm");
			StringWriter swCommonColumns = new StringWriter();
			templateCommonColumnsContens.merge(context, swCommonColumns);
			FileWriter fwCommonColumns = new FileWriter(pathCommon+"CommonColumnsContent.xhtml");
			BufferedWriter bwCommons = new BufferedWriter(fwCommonColumns);
			bwCommons.write(swCommonColumns.toString());
			bwCommons.close();
			swCommonColumns.close();
			logPrimeHibernate.info("End CommonColumnsContens");
			
			logPrimeHibernate.info("Begin CommonLayout");
			Template templateLayout = ve.getTemplate("CommonLayout.vm");
			StringWriter swCommonLayout = new StringWriter();
			templateLayout.merge(context, swCommonLayout);
			FileWriter fwLayout = new FileWriter(pathCommon+"CommonLayout.xhtml");
			BufferedWriter bwLayout= new BufferedWriter(fwLayout);
			bwLayout.write(swCommonLayout.toString());
			bwLayout.close();
			fwLayout.close();
			logPrimeHibernate.info("End CommonLayout");
			
		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doJspInitialMenu(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {

		try {

			logPrimeHibernate.info("Begin InitialMenu PrimeFaces + Hibernate");
			Template initialMenu = ve.getTemplate("XHTMLinitialMenu.vm");
			StringWriter swInitialMenu = new StringWriter();
			initialMenu.merge(context, swInitialMenu);

			String path=properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;;
			FileWriter fwInitialMenu = new FileWriter(path+"initialMenu.xhtml");
			BufferedWriter bwInitialMenu = new BufferedWriter(fwInitialMenu);
			bwInitialMenu.write(swInitialMenu.toString());
			bwInitialMenu.close();
			fwInitialMenu.close();
			logPrimeHibernate.info("End InitialMenu PrimeFaces + Hibernate");


		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doLogicXMLHibernate(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {

		try {

			logPrimeHibernate.info("Begin ILogic PrimeFaces + Hibernate");
			Template templateILogic = ve.getTemplate("ILogicHibernatePrime.vm");
			StringWriter swILogic = new StringWriter();
			templateILogic.merge(context, swILogic);
			String path=hdLocation+virginPackageInHd+GeneratorUtil.slash+modelName+GeneratorUtil.slash+"control"+GeneratorUtil.slash;
			FileWriter fw = new FileWriter(path+"I"+metaData.getRealClassName()+"Logic.java");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(swILogic.toString());
			bw.close();
			fw.close();
			logPrimeHibernate.info("End ILogic Primefaces + Hibernate");

			logPrimeHibernate.info("Begin Logic PrimeFaces + Hibernate");
			Template templateLogic = ve.getTemplate("LogicHibernatePrime.vm");
			StringWriter swLogic = new StringWriter();
			templateLogic.merge(context, swLogic);
			FileWriter fwLogic = new FileWriter(path+metaData.getRealClassName()+"Logic.java");
			BufferedWriter bwLogic = new BufferedWriter(fwLogic);
			bwLogic.write(swLogic.toString());
			bwLogic.close();
			fwLogic.close();
			logPrimeHibernate.info("End Logic Primefaces + Hibernate");

			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "Logic.java");
			JalopyCodeFormatter.formatJavaCodeFile(path + "I" + metaData.getRealClassName() + "Logic.java");



		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}


	@Override
	public void doUtilites(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {
		try {
			// Falta Facelets y demas
			logPrimeHibernate.info("Begin Utilities PrimeFaces + Hibernate");
			Template utilitiesTemplate = ve.getTemplate("Utilities.vm");
			StringWriter swUtilities = new StringWriter();
			utilitiesTemplate.merge(context, swUtilities);

			String path = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "utilities" + GeneratorUtil.slash;
			FileWriter fwUtilities = new FileWriter(path + "Utilities.java");
			BufferedWriter bwUtilities = new BufferedWriter(fwUtilities);
			bwUtilities.write(swUtilities.toString());
			bwUtilities.close();
			swUtilities.close();
			logPrimeHibernate.info("End Utilities PrimeFaces + Hibernate");

			logPrimeHibernate.info("Begin FacesUtils PrimeFaces + Hibernate");
			Template facesUtilsTemplate = ve.getTemplate("FacesUtils.vm");
			StringWriter swFacesUtils= new StringWriter();
			facesUtilsTemplate.merge(context, swFacesUtils);

			FileWriter fwFacesUtils = new FileWriter(path+"FacesUtils.java");
			BufferedWriter bwFacesUtils = new BufferedWriter(fwFacesUtils);
			bwFacesUtils.write(swFacesUtils.toString());
			bwFacesUtils.close();
			fwFacesUtils.close();
			logPrimeHibernate.info("End FacesUtils PrimeFaces + Hibernate");


		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());			

		}

	}

	@Override
	public void doXMLHibernateDaoFactory(MetaDataModel metaData,
			VelocityContext context, String hdLocation) {

		try {

			logPrimeHibernate.info("Begin DaoFactory PrimeFaces + Hibernate");
			Template daoFatoryTemplate = ve.getTemplate("XMLHibernateDaoFactory.vm");
			StringWriter swDaoFactory = new StringWriter();
			daoFatoryTemplate.merge(context, swDaoFactory);

			String path= hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "dataaccess" + GeneratorUtil.slash
			+ "daoFactory" + GeneratorUtil.slash;


			FileWriter fwDaoFatory = new FileWriter(path+"XMLHibernateDaoFactory.java");
			BufferedWriter bwDaoFacory = new BufferedWriter(fwDaoFatory);
			bwDaoFacory.write(swDaoFactory.toString());
			bwDaoFacory.close();
			fwDaoFatory.close();
			JalopyCodeFormatter.formatJavaCodeFile(path + "XMLHibernateDaoFactory.java");

		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}

	@Override
	public void doBackingBeans(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {

		try {
			logPrimeHibernate.info("Begin BackEndBean PrimeFaces + Hibernate");
			Template backEndBeanPrime = ve.getTemplate("BackingBeansHibernatePrime.vm");
			StringWriter swBackEndBean = new StringWriter();
			backEndBeanPrime.merge(context, swBackEndBean);

			String path=hdLocation+virginPackageInHd+GeneratorUtil.slash+"presentation"+GeneratorUtil.slash+"backingBeans"+GeneratorUtil.slash;
			FileWriter fwBackEndBean = new FileWriter(path+metaData.getRealClassName()+"View.java");
			BufferedWriter bwBackEndBean = new BufferedWriter(fwBackEndBean);
			bwBackEndBean.write(swBackEndBean.toString());
			bwBackEndBean.close();
			fwBackEndBean.close();
			logPrimeHibernate.info("End BackEndBean PrimeFaces + Hibernate");
			JalopyCodeFormatter.formatJavaCodeFile(path+metaData.getRealClassName()+"View.java");
			Utilities.getInstance().dates = null;
			Utilities.getInstance().datesId = null;

		} catch (Exception e) {
			logPrimeHibernate.error(e.getMessage());
		}

	}


}
