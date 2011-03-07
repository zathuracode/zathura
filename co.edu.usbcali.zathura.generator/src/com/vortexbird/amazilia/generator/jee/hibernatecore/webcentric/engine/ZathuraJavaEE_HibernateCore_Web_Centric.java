package com.vortexbird.amazilia.generator.jee.hibernatecore.webcentric.engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.vortexbird.amazilia.generator.jee.hibernatecore.webcentric.utils.IStringBuilder;
import com.vortexbird.amazilia.generator.jee.hibernatecore.webcentric.utils.IStringBuilderForId;
import com.vortexbird.amazilia.generator.jee.hibernatecore.webcentric.utils.StringBuilder;
import com.vortexbird.amazilia.generator.jee.hibernatecore.webcentric.utils.StringBuilderForId;
import com.vortexbird.amazilia.generator.jee.hibernatecore.webcentric.utils.Utilities;

import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.generator.utilities.JalopyCodeFormatter;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;



/**
 * Zathura Generator
 * @author William Altuzarra Noriega (williamaltu@gmail.com)
 * @version 1.0
 */
public class ZathuraJavaEE_HibernateCore_Web_Centric implements IZathuraGenerator,
		IZathuraTemplate {

	private static Logger log = Logger.getLogger(ZathuraJavaEE_HibernateCore_Web_Centric.class);

	private final static String webCentric = GeneratorUtil.getTemplatesZathuraJavaEEHibernateCoreWebCentric();

	public String virginPackageInHd = new String();
	private Properties properties;
	private VelocityEngine ve;

	public void toGenerate(MetaDataModel metaDataModel, String projectName,
			String folderProjectPath, Properties propiedades) {
		log.info("Begin Zathura JavaEE HibernateCore Web Centric generation");

		String jpaPckgName = propiedades.getProperty("jpaPckgName");
		Integer specificityLevel = (Integer) propiedades
				.get("specificityLevel");
		properties = propiedades;
		log.info("doTemplate ZathuraJavaEE_Web_Centric generation");
		doTemplate(folderProjectPath, metaDataModel, jpaPckgName, projectName,
				specificityLevel);
		copyLibreriasExt();
		log.info("End Zathura JavaEE HibernateCore Web Centric generation");
	}

	/**
	 * Copia las librerias y los archivos necesarios para que funcion lo
	 * generado
	 * 
	 * @param propiedades
	 */
	private void copyLibreriasExt() {

		String generatorExtZathuraJavaEEHibernateCoreWebCentricIndexJsp = GeneratorUtil.getGeneratorExtZathuraJavaEEHibernateCoreWebCentric()
				+ GeneratorUtil.slash + "index.jsp";
		String generatorExtZathuraJavaEEHibernateCoreWebCentricImages = GeneratorUtil
				.getGeneratorExtZathuraJavaEEHibernateCoreWebCentric()
				+ GeneratorUtil.slash + "images" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEHibernateCoreWebCentricCSS = GeneratorUtil
				.getGeneratorExtZathuraJavaEEHibernateCoreWebCentric()
				+ GeneratorUtil.slash + "css" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEHibernateCoreWebCentricXmlhttp = GeneratorUtil
				.getGeneratorExtZathuraJavaEEHibernateCoreWebCentric()
				+ GeneratorUtil.slash + "xmlhttp" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEHibernateCoreWebCentricWEBXML = GeneratorUtil
				.getGeneratorExtZathuraJavaEEHibernateCoreWebCentric()
				+ GeneratorUtil.slash + "WEB-INF" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEHibernateCoreWebCentricLOG4J = GeneratorUtil
				.getGeneratorExtZathuraJavaEEHibernateCoreWebCentric()
				+ GeneratorUtil.slash + "log4j" + GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEHibernateCoreWebCentricIceFaces = GeneratorUtil
				.getGeneratorLibrariesZathuraJavaEEHibernateCoreWebCentric()
				+ GeneratorUtil.slash + "iceFaces1.8.1" + GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEHibernateCoreWebCentricJpaHibernate = GeneratorUtil
				.getGeneratorLibrariesZathuraJavaEEHibernateCoreWebCentric()
				+ GeneratorUtil.slash
				+ "core-hibernate3.3"
				+ GeneratorUtil.slash;

		log.info("Copy Libraries files ZathuraJavaEE_Web_Centric generation");

		// Copy Libraries
		String libFolderPath = properties.getProperty("libFolderPath");
		GeneratorUtil.copyFolder(generatorLibrariesZathuraJavaEEHibernateCoreWebCentricIceFaces,	libFolderPath);
		GeneratorUtil.copyFolder(generatorLibrariesZathuraJavaEEHibernateCoreWebCentricJpaHibernate,libFolderPath);

		// Copy Ext web.xml
		String webRootFolderPath = properties.getProperty("webRootFolderPath");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEHibernateCoreWebCentricWEBXML,
				webRootFolderPath + "WEB-INF" + GeneratorUtil.slash);

		// Copy Ext css
		GeneratorUtil.createFolder(webRootFolderPath + "images");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEHibernateCoreWebCentricImages,
				webRootFolderPath + "images" + GeneratorUtil.slash);

		// Copy Ext css
		GeneratorUtil.createFolder(webRootFolderPath + "css");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEHibernateCoreWebCentricCSS,
				webRootFolderPath + "css" + GeneratorUtil.slash);

		// Copy Ext xmlhttp
		GeneratorUtil.createFolder(webRootFolderPath + "xmlhttp");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEHibernateCoreWebCentricXmlhttp,
				webRootFolderPath + "xmlhttp" + GeneratorUtil.slash);

		// Copy Ext index.jsp
		GeneratorUtil.copy(generatorExtZathuraJavaEEHibernateCoreWebCentricIndexJsp,
				webRootFolderPath + GeneratorUtil.slash + "index.jsp");

		// Copy Ext log4j
		String folderProjectPath = properties.getProperty("folderProjectPath");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEHibernateCoreWebCentricLOG4J,
				folderProjectPath + GeneratorUtil.slash);

	}

	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel) {
		try {
			ve = new VelocityEngine();
			Properties properties = new Properties();

			properties.setProperty("file.resource.loader.description",
					"Velocity File Resource Loader");
			properties
					.setProperty("file.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			properties.setProperty("file.resource.loader.path", webCentric);
			properties.setProperty("file.resource.loader.cache", "false");
			properties.setProperty(
					"file.resource.loader.modificationCheckInterval", "2");

			ve.init(properties);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		VelocityContext context = new VelocityContext();

		MetaDataModel dataModel = metaDataModel;
		List<MetaData> list = dataModel.getTheMetaData();

		IStringBuilderForId stringBuilderForId = new StringBuilderForId(list);
		IStringBuilder stringBuilder = new StringBuilder(list,
				(StringBuilderForId) stringBuilderForId);

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
				// TODO: handle exception
			}
		} else {
			try {
				packageOriginal = jpaPckgName;

				int lastIndexOf2 = packageOriginal.lastIndexOf(".") + 1;
				modelName = jpaPckgName.substring(lastIndexOf2);

				int virginLastIndexOf = packageOriginal.lastIndexOf(".");
				virginPackage = packageOriginal.substring(0, virginLastIndexOf);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		String projectNameClass = (projectName.substring(0, 1)).toUpperCase()
				+ projectName.substring(1, projectName.length());

		context.put("packageOriginal", packageOriginal);
		context.put("virginPackage", virginPackage);
		context.put("package", jpaPckgName);
		context.put("projectName", projectName);
		context.put("modelName", modelName);
		context.put("projectNameClass", projectNameClass);

		this.virginPackageInHd = GeneratorUtil.replaceAll(virginPackage, ".",
				GeneratorUtil.slash);

		Utilities.getInstance().buildFolders(virginPackage, hdLocation,
				specificityLevel, packageOriginal, properties);

		// stringBuilderForId.neededIds(list);
		Utilities.getInstance().biuldHashToGetIdValuesLengths(list);

		for (MetaData metaData : list) {

			List<String> imports = Utilities.getInstance().getRelatedClasses(
					metaData, dataModel);

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

			context.put("finalParamForView", stringBuilder.finalParamForView(
					list, metaData));

			context.put("finalParamForDtoUpdate", stringBuilder
					.finalParamForDtoUpdate(list, metaData));

			context.put("finalParamForDtoUpdateOnlyVariables", stringBuilder
					.finalParamForDtoUpdateOnlyVariables(list, metaData));

			context.put("finalParamForViewVariablesInList", stringBuilder
					.finalParamForViewVariablesInList(list, metaData));
			context.put("isFinalParamForViewDatesInList", Utilities
					.getInstance().isFinalParamForViewDatesInList());
			context.put("finalParamForViewDatesInList",
					Utilities.getInstance().dates);

			context.put("finalParamForIdForViewVariablesInList",
					stringBuilderForId.finalParamForIdForViewVariablesInList(
							list, metaData));
			context.put("isFinalParamForIdForViewDatesInList", Utilities
					.getInstance().isFinalParamForIdForViewDatesInList());
			context.put("finalParamForIdForViewDatesInList", Utilities
					.getInstance().datesId);

			context.put("finalParamForIdForViewClass", stringBuilderForId
					.finalParamForIdForViewClass(list, metaData));
			context.put("finalParamForIdClassAsVariablesAsString",
					stringBuilderForId.finalParamForIdClassAsVariablesAsString(
							list, metaData));

			context.put("finalParamForViewForSetsVariablesInList",
					stringBuilder.finalParamForViewForSetsVariablesInList(list,
							metaData));

			context.put("finalParamForIdForDtoForSetsVariablesInList",
					stringBuilderForId
							.finalParamForIdForDtoForSetsVariablesInList(list,
									metaData));

			context.put("finalParamForDtoForSetsVariablesInList", stringBuilder
					.finalParamForDtoForSetsVariablesInList(list, metaData));

			context.put("finalParamForIdForDtoInViewForSetsVariablesInList",
					stringBuilderForId
							.finalParamForIdForDtoInViewForSetsVariablesInList(
									list, metaData));

			context.put("finalParamForDtoInViewForSetsVariablesInList",
					stringBuilder.finalParamForDtoInViewForSetsVariablesInList(
							list, metaData));

			context.put("finalParamForIdForViewForSetsVariablesInList",
					stringBuilderForId
							.finalParamForIdForViewForSetsVariablesInList(list,
									metaData));

			context.put("finalParamForIdVariablesAsList", stringBuilderForId
					.finalParamForIdVariablesAsList(list, metaData));

			String finalParam = stringBuilder.finalParam(list, metaData);
			context.put("finalParam", finalParam);
			metaData.setFinamParam(finalParam);

			String finalParamVariables = stringBuilder.finalParamVariables(
					list, metaData);
			context.put("finalParamVariables", finalParamVariables);
			metaData.setFinamParamVariables(finalParamVariables);

			String finalParamForId = stringBuilderForId.finalParamForId(list,
					metaData);
			context.put("finalParamForId", stringBuilderForId.finalParamForId(
					list, metaData));
			metaData.setFinalParamForId(finalParamForId);

			String finalParamForIdVariables = stringBuilderForId
					.finalParamForIdVariables(list, metaData);
			context.put("finalParamForIdVariables", stringBuilderForId
					.finalParamForIdVariables(list, metaData));
			metaData.setFinalParamForIdVariables(finalParamForIdVariables);

			context.put("finalParamVariablesAsList", stringBuilder
					.finalParamVariablesAsList(list, metaData));
			context.put("finalParamVariablesAsList2", stringBuilder
					.finalParamVariablesAsList2(list, metaData));
			context.put("finalParamVariablesDatesAsList2", stringBuilder
					.finalParamVariablesDatesAsList2(list, metaData));
			context.put("isFinalParamDatesAsList", Utilities.getInstance()
					.isFinalParamDatesAsList());
			context.put("finalParamDatesAsList",
					Utilities.getInstance().datesJSP);

			context.put("finalParamForIdClassAsVariables", stringBuilderForId
					.finalParamForIdClassAsVariables(list, metaData));
			context.put("finalParamForIdClassAsVariables2", stringBuilderForId
					.finalParamForIdClassAsVariables2(list, metaData));
			context.put("isFinalParamForIdClassAsVariablesForDates", Utilities
					.getInstance().isFinalParamForIdClassAsVariablesForDates());
			context.put("finalParamForIdClassAsVariablesForDates", Utilities
					.getInstance().datesIdJSP);

			context.put("finalParamForVariablesDataTablesAsList", stringBuilder
					.finalParamForVariablesDataTablesAsList(list, metaData));
			context.put("finalParamForVariablesDataTablesForIdAsList",
					stringBuilderForId
							.finalParamForVariablesDataTablesForIdAsList(list,
									metaData));

			if (metaData.isGetManyToOneProperties()) {
				context.put("getVariableForManyToOneProperties", stringBuilder
						.getVariableForManyToOneProperties(metaData
								.getManyToOneProperties(), list));
				context.put("getStringsForManyToOneProperties", stringBuilder
						.getStringsForManyToOneProperties(metaData
								.getManyToOneProperties(), list));
			}

			context.put("composedKey", false);

			if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
				context.put("composedKey", true);
				context.put("finalParamForIdClass", stringBuilderForId
						.finalParamForIdClass(list, metaData));
			}

			context.put("metaData", metaData);
			context.put("dataModel", dataModel);

			doDaoXMLHibernate(metaData, context, hdLocation);

			doBackEndBeans(metaData, context, hdLocation, dataModel);
			doJsp(metaData, context, hdLocation, dataModel);
			doLogicXMLHibernate(metaData, context, hdLocation, dataModel, modelName);
			doDto(metaData, context, hdLocation, dataModel, modelName);
			doExceptions(context, hdLocation);
		}

		doUtilites(context, hdLocation, dataModel, modelName);
		doBusinessDelegator(context, hdLocation, dataModel);
		doXMLHibernateDaoFactory(dataModel, context, hdLocation);
		doHibernateSessionFactory(dataModel, context, hdLocation);
		doJspInitialMenu(dataModel, context, hdLocation);
		doFacesConfig(dataModel, context, hdLocation);
		doJspFacelets(context, hdLocation);
	}

	public void doLogicXMLHibernate(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doLogicXMLHibernate");

		Template ilogic = null;
		Template logic = null;

		StringWriter swIlogic = new StringWriter();
		StringWriter swLogic = new StringWriter();

		try {
			ilogic = ve.getTemplate("ILogic.vm");
			logic = ve.getTemplate("LogicXMLHibernate.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			ilogic.merge(context, swIlogic);
			logic.merge(context, swLogic);
			// System.out.println(swIdao);
			// System.out.println(swdao);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + modelName
					+ GeneratorUtil.slash + "control" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "I"
					+ metaData.getRealClassName() + "Logic.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swIlogic.toString());
			// Close the output stream
			out.close();

			FileWriter fstream1 = new FileWriter(realLocation
					+ metaData.getRealClassName() + "Logic.java");
			BufferedWriter out1 = new BufferedWriter(fstream1);
			out1.write(swLogic.toString());
			// Close the output stream
			out1.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ metaData.getRealClassName() + "Logic.java");
			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "I"
					+ metaData.getRealClassName() + "Logic.java");

			log.info("End doLogicXMLHibernate");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}	

	public void doDto(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doDTO");

		Template dto = null;

		StringWriter swDto = new StringWriter();

		try {
			dto = ve.getTemplate("Dto.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			dto.merge(context, swDto);
			// System.out.println(swIdao);
			// System.out.println(swdao);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + modelName
					+ GeneratorUtil.slash + "dto" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ metaData.getRealClassName() + "DTO.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swDto.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ metaData.getRealClassName() + "DTO.java");

			log.info("End doDTOc");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doUtilites(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {
		log.info("Begin doUtilites");

		Template utilities = null;
		Template utilitiesFacesUtils = null;
		Template utilitiesDataPage = null;
		Template utilitiesDataSource = null;
		Template utilitiesPagedListDataModel = null;

		StringWriter swUtilities = new StringWriter();
		StringWriter swUtilitiesFacesUtils = new StringWriter();
		StringWriter swUtilitiesDataPage = new StringWriter();
		StringWriter swUtilitiesDataSource = new StringWriter();
		StringWriter swUtilitiesPagedListDataModel = new StringWriter();

		try {
			utilities = ve.getTemplate("Utilities.vm");
			utilitiesFacesUtils = ve.getTemplate("FacesUtils.vm");
			utilitiesDataPage = ve.getTemplate("DataPage.vm");
			utilitiesDataSource = ve.getTemplate("DataSource.vm");
			utilitiesPagedListDataModel = ve
					.getTemplate("PagedListDataModel.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			utilities.merge(context, swUtilities);
			utilitiesFacesUtils.merge(context, swUtilitiesFacesUtils);
			utilitiesDataPage.merge(context, swUtilitiesDataPage);
			utilitiesDataSource.merge(context, swUtilitiesDataSource);
			utilitiesPagedListDataModel.merge(context,
					swUtilitiesPagedListDataModel);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "utilities"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "Utilities.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swUtilities.toString());
			// Close the output stream
			out.close();
			
			FileWriter fstreamfu = new FileWriter(realLocation + "FacesUtils.java");
			BufferedWriter outfu = new BufferedWriter(fstreamfu);
			outfu.write(swUtilitiesFacesUtils.toString());
			// Close the output stream
			outfu.close();			

			FileWriter fstream2 = new FileWriter(realLocation + "DataPage.java");
			BufferedWriter out2 = new BufferedWriter(fstream2);
			out2.write(swUtilitiesDataPage.toString());
			// Close the output stream
			out2.close();

			FileWriter fstream3 = new FileWriter(realLocation
					+ "DataSource.java");
			BufferedWriter out3 = new BufferedWriter(fstream3);
			out3.write(swUtilitiesDataSource.toString());
			// Close the output stream
			out3.close();

			FileWriter fstream4 = new FileWriter(realLocation
					+ "PagedListDataModel.java");
			BufferedWriter out4 = new BufferedWriter(fstream4);
			out4.write(swUtilitiesPagedListDataModel.toString());
			// Close the output stream
			out4.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "Utilites.java");
			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "FacesUtils.java");		
			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "DataPage.java");
			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "DataSource.java");
			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "PagedListDataModel.java");

			log.info("End doUtilites");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doExceptions(VelocityContext context, String hdLocation) {
		log.info("Begin doException");

		Template exceptions = null;

		StringWriter swExceptions = new StringWriter();

		try {
			exceptions = ve.getTemplate("ZMessManager.vm");

		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			exceptions.merge(context, swExceptions);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "exceptions"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ "ZMessManager.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swExceptions.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "ZMessManager.java");

			log.info("End doException");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doBackEndBeans(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {
		log.info("Begin doBackEndBeans");

		Template backEndBeans = null;

		StringWriter swBackEndBeans = new StringWriter();

		try {
			backEndBeans = ve.getTemplate("BackEndBeans.vm");

		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			backEndBeans.merge(context, swBackEndBeans);

			// System.out.println(swIdao);
			// System.out.println(swdao);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "presentation"
					+ GeneratorUtil.slash + "backEndBeans"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ metaData.getRealClassName() + "View.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swBackEndBeans.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ metaData.getRealClassName() + "View.java");

			Utilities.getInstance().dates = null;
			Utilities.getInstance().datesId = null;

			log.info("End doBackEndBeans");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doJsp(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {
		log.info("Begin doJsp");

		Template jsp = null;
		Template jspData = null;
		Template jspDataEditable = null;

		StringWriter swJsp = new StringWriter();
		StringWriter swJspData = new StringWriter();
		StringWriter swJspDataEditable = new StringWriter();

		try {
			jsp = ve.getTemplate("JSP.vm");
			jspData = ve.getTemplate("JSPdataTables.vm");
			jspDataEditable = ve.getTemplate("JSPdataTables-Editable.vm");

		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jsp.merge(context, swJsp);
			jspData.merge(context, swJspData);
			jspDataEditable.merge(context, swJspDataEditable);

			String realLocation = properties.getProperty("webRootFolderPath")
					+ "JSPX" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ metaData.getRealClassNameAsVariable() + ".jspx");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swJsp.toString());

			// Close the output stream
			out.close();

			FileWriter fstreamData = new FileWriter(realLocation
					+ metaData.getRealClassNameAsVariable()
					+ "ListDataTable.jspx");
			BufferedWriter outData = new BufferedWriter(fstreamData);
			outData.write(swJspData.toString());
			// Close the output stream
			outData.close();

			FileWriter fstreamDataEditable = new FileWriter(realLocation
					+ metaData.getRealClassNameAsVariable()
					+ "ListDataTableEditable.jspx");
			BufferedWriter outDataEditable = new BufferedWriter(
					fstreamDataEditable);
			outDataEditable.write(swJspDataEditable.toString());
			// Close the output stream
			outDataEditable.close();

			Utilities.getInstance().datesJSP = null;
			Utilities.getInstance().datesIdJSP = null;

			log.info("End doJSP");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doJspFacelets(VelocityContext context, String hdLocation) {
		log.info("Begin doJspFacelets");

		Template jspMainTemplate = null;
		Template jspHeader = null;
		Template jspFooter = null;

		StringWriter swMainTemplate = new StringWriter();
		StringWriter swHeader = new StringWriter();
		StringWriter swFooter = new StringWriter();

		try {
			jspMainTemplate = ve.getTemplate("JSPmainTemplate.vm");
			jspHeader = ve.getTemplate("JSPheader.vm");
			jspFooter = ve.getTemplate("JSPfooter.vm");

		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jspMainTemplate.merge(context, swMainTemplate);
			jspHeader.merge(context, swHeader);
			jspFooter.merge(context, swFooter);

			String realLocation = properties.getProperty("webRootFolderPath")
					+ "WEB-INF" + GeneratorUtil.slash + "facelets"
					+ GeneratorUtil.slash;

			// Main template
			FileWriter fstreamMainTemplate = new FileWriter(realLocation
					+ "mainTemplate" + ".jspx");
			BufferedWriter outMainTemplate = new BufferedWriter(
					fstreamMainTemplate);
			outMainTemplate.write(swMainTemplate.toString());
			outMainTemplate.close();

			// Header
			FileWriter fstreamHeader = new FileWriter(realLocation
					+ "header.jspx");
			BufferedWriter outHeader = new BufferedWriter(fstreamHeader);
			outHeader.write(swHeader.toString());
			// Close the output stream
			outHeader.close();

			// Footer
			FileWriter fstreamFooter = new FileWriter(realLocation
					+ "footer.jspx");
			BufferedWriter outFooter = new BufferedWriter(fstreamFooter);
			outFooter.write(swFooter.toString());
			// Close the output stream
			outFooter.close();

			log.info("End doJSPFacelets");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doBusinessDelegator(VelocityContext context, String hdLocation,
			MetaDataModel dataModel) {

		log.info("Begin doBusinessDelegator");

		Template businessDelegator = null;

		StringWriter swBusinessDelegator = new StringWriter();

		try {
			businessDelegator = ve
					.getTemplate("BusinessDelegatorView.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			businessDelegator.merge(context, swBusinessDelegator);
			// System.out.println(swdao);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "presentation"
					+ GeneratorUtil.slash + "businessDelegate"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ "BusinessDelegatorView" + ".java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swBusinessDelegator.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "BusinessDelegatorView" + ".java");

			log.info("End doBusinessDelegator");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doDaoXMLHibernate(MetaData metaData, VelocityContext context,
			String hdLocation) {

		log.info("Begin doDaoXMLHibernate");

		Template idao = null;
		Template dao = null;

		StringWriter swIdao = new StringWriter();
		StringWriter swDao = new StringWriter();

		try {
			idao = ve.getTemplate("IDAO2XMLHibernate.vm");
			dao = ve.getTemplate("DAO2XMLHibernate.vm");
		} catch (ResourceNotFoundException rnfe) {
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			idao.merge(context, swIdao);
			dao.merge(context, swDao);
			// System.out.println(swIdao);
			// System.out.println(swdao);

			String daoLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "dataaccess"
					+ GeneratorUtil.slash + "dao" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(daoLocation + "I"
					+ metaData.getRealClassName() + "DAO.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swIdao.toString());
			// Close the output stream
			out.close();

			FileWriter fstream1 = new FileWriter(daoLocation
					+ metaData.getRealClassName() + "DAO.java");
			BufferedWriter out1 = new BufferedWriter(fstream1);
			out1.write(swDao.toString());
			// Close the output stream
			out1.close();

			JalopyCodeFormatter.formatJavaCodeFile(daoLocation + "I"
					+ metaData.getRealClassName() + "DAO.java");

			JalopyCodeFormatter.formatJavaCodeFile(daoLocation
					+ metaData.getRealClassName() + "DAO.java");

			log.info("End doDaoXMLHibernate");

		} catch (Exception e) {
			log.info("Error doDaoXMLHibernate");
		}

	}	
	
	public void doXMLHibernateDaoFactory(MetaDataModel metaData, VelocityContext context,
			String hdLocation) {

		log.info("Begin doXMLHibernateDaoFactory");

		Template daoFactory = null;
		StringWriter swDaoFactory = new StringWriter();

		try {
			daoFactory = ve.getTemplate("XMLHibernateDaoFactory.vm");
			// logic = ve.getTemplate("Logic.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			daoFactory.merge(context, swDaoFactory);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "dataaccess"
					+ GeneratorUtil.slash + "daoFactory" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ "XMLHibernateDaoFactory.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swDaoFactory.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "XMLHibernateDaoFactory.java");

			log.info("End doXMLHibernateDaoFactory");

		} catch (Exception e) {
			log.info("Error doDaoFactory");
		}

	}	

	public void doHibernateSessionFactory(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {

		log.info("Begin doHibernateSessionFactory");

		Template entityManager = null;
		StringWriter swEntityManager = new StringWriter();

		try {
			entityManager = ve.getTemplate("HibernateSessionFactory.vm");
			// logic = ve.getTemplate("Logic.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			entityManager.merge(context, swEntityManager);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "dataaccess"
					+ GeneratorUtil.slash + "sessionFactory"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ "HibernateSessionFactory.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swEntityManager.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "HibernateSessionFactory.java");

			log.info("End doHibernateSessionFactory");

		} catch (Exception e) {
			log.info("Error doEntityManager");
		}

	}	

	public void doJspInitialMenu(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {

		log.info("Begin doJspInitialMenu");

		Template jspInitialMenu = null;
		StringWriter swJspInitialMenu = new StringWriter();

		try {
			jspInitialMenu = ve.getTemplate("JSPinitialMenu.vm");
			// logic = ve.getTemplate("Logic.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jspInitialMenu.merge(context, swJspInitialMenu);

			String realLocation = properties.getProperty("webRootFolderPath")
					+ "JSPX" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ "initialMenu.jspx");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swJspInitialMenu.toString());
			// Close the output stream
			out.close();

			log.info("End doJspInitialMenu");

		} catch (Exception e) {
			log.info("Error doJspInitialMenu");
		}

	}

	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context,
			String hdLocation) {

		log.info("Begin doFacesConfig");

		Template facesConfig = null;
		StringWriter swFacesConfig = new StringWriter();

		try {
			facesConfig = ve.getTemplate("faces-config.xml.vm");
			// logic = ve.getTemplate("Logic.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			facesConfig.merge(context, swFacesConfig);

			FileWriter fstream = new FileWriter(properties
					.getProperty("webRootFolderPath")
					+ "WEB-INF" + GeneratorUtil.slash + "faces-config.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swFacesConfig.toString());
			// Close the output stream
			out.close();

			log.info("End doFacesConfig");

		} catch (Exception e) {
			log.info("Error doFacesConfig");
		}

	}

}