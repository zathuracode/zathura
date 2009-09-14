package co.edu.usbcali.lidis.zathura.generator.jeewebcentric.engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.Basic;
import javax.persistence.Column;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.generator.utilities.JalopyCodeFormatter;
import co.edu.usbcali.lidis.zathura.metadata.model.ManyToOneMember;
import co.edu.usbcali.lidis.zathura.metadata.model.Member;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.model.SimpleMember;

/**
 * 
 * @author William Altuzarra Noriega
 * 
 */
public class ZathuraJavaEE_Web_Centric implements IZathuraGenerator,
		IZathuraTemplate {

	private static Logger log = Logger
			.getLogger(ZathuraJavaEE_Web_Centric.class);

	private final static String webCentric = GeneratorUtil
			.getWebCentricTemplates();

	private static String ifcondition = "if(";
	private static String ifconditionClose = "){";
	private static String throwExceptionNull = "throw new Exception(ExceptionMessages.VARIABLE_NULL+";
	private static String throwExceptionLength = "throw new Exception(ExceptionMessages.VARIABLE_LENGTH+";
	private static String throwExceptionClose = ");}";

	private Long length;
	private Long precision;
	private Long scale;
	private Boolean nullable;

	private HashMap<String, Member> manyToOneTempHash;

	public static HashMap<String, String> hashMapIds = new HashMap<String, String>();
	public String virginPackageInHd = new String();
	public List<String> dates;
	public List<String> datesJSP;
	public List<String> datesId;
	public List<String> datesIdJSP;

	private Properties properties;

	public void toGenerate(MetaDataModel metaDataModel, String projectName,
			String folderProjectPath, Properties propiedades) {
		log.info("Begin ZathuraJavaEE_Web_Centric generation");

		String jpaPckgName = propiedades.getProperty("jpaPckgName");
		Integer specificityLevel = (Integer) propiedades
				.get("specificityLevel");
		properties = propiedades;
		log.info("doTemplate ZathuraJavaEE_Web_Centric generation");
		doTemplate(folderProjectPath, metaDataModel, jpaPckgName, projectName,
				specificityLevel);
		copyLibreriasExt();
		log.info("End ZathuraJavaEE_Web_Centric generation");
	}

	/**
	 * Copia las librerias y los archivos necesarios para que funcion lo
	 * generado
	 * 
	 * @param propiedades
	 */
	private void copyLibreriasExt() {

		String generatorExtZathuraJavaEEWebCentricIndexJsp = GeneratorUtil
				.getGeneratorExtZathuraJavaEEWebCentric()
				+ GeneratorUtil.slash + "index.jsp";
		String generatorExtZathuraJavaEEWebCentricImages = GeneratorUtil
				.getGeneratorExtZathuraJavaEEWebCentric()
				+ GeneratorUtil.slash + "images" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEWebCentricCSS = GeneratorUtil
				.getGeneratorExtZathuraJavaEEWebCentric()
				+ GeneratorUtil.slash + "css" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEWebCentricXmlhttp = GeneratorUtil
				.getGeneratorExtZathuraJavaEEWebCentric()
				+ GeneratorUtil.slash + "xmlhttp" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEWebCentricWEBXML = GeneratorUtil
				.getGeneratorExtZathuraJavaEEWebCentric()
				+ GeneratorUtil.slash + "WEB-INF" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEWebCentricLOG4J = GeneratorUtil
				.getGeneratorExtZathuraJavaEEWebCentric()
				+ GeneratorUtil.slash + "log4j" + GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEWebCentricIceFaces = GeneratorUtil
				.getGeneratorLibrariesZathuraJavaEEWebCentric()
				+ GeneratorUtil.slash + "iceFaces1.8.1" + GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEWebCentricJpaHibernate = GeneratorUtil
				.getGeneratorLibrariesZathuraJavaEEWebCentric()
				+ GeneratorUtil.slash
				+ "jpa-hibernate3.2"
				+ GeneratorUtil.slash;

		log.info("Copy Libraries files ZathuraJavaEE_Web_Centric generation");

		// Copy Libraries
		String libFolderPath = properties.getProperty("libFolderPath");
		GeneratorUtil.copyFolder(
				generatorLibrariesZathuraJavaEEWebCentricIceFaces,
				libFolderPath);
		GeneratorUtil.copyFolder(
				generatorLibrariesZathuraJavaEEWebCentricJpaHibernate,
				libFolderPath);

		// Copy Ext web.xml
		String webRootFolderPath = properties.getProperty("webRootFolderPath");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebCentricWEBXML,
				webRootFolderPath + "WEB-INF" + GeneratorUtil.slash);

		// Copy Ext css
		GeneratorUtil.createFolder(webRootFolderPath + "images");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebCentricImages,
				webRootFolderPath + "images" + GeneratorUtil.slash);

		// Copy Ext css
		GeneratorUtil.createFolder(webRootFolderPath + "css");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebCentricCSS,
				webRootFolderPath + "css" + GeneratorUtil.slash);

		// Copy Ext xmlhttp
		GeneratorUtil.createFolder(webRootFolderPath + "xmlhttp");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebCentricXmlhttp,
				webRootFolderPath + "xmlhttp" + GeneratorUtil.slash);

		// Copy Ext index.jsp
		GeneratorUtil.copy(generatorExtZathuraJavaEEWebCentricIndexJsp,
				webRootFolderPath + GeneratorUtil.slash + "index.jsp");

		// Copy Ext log4j
		String folderProjectPath = properties.getProperty("folderProjectPath");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebCentricLOG4J,
				folderProjectPath + GeneratorUtil.slash);

	}

	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel) {
		try {
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

			Velocity.init(properties);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		VelocityContext context = new VelocityContext();

		MetaDataModel dataModel = metaDataModel;
		List<MetaData> list = dataModel.getTheMetaData();

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

		buildFolders(virginPackage, hdLocation, specificityLevel,
				packageOriginal);

		neededIds(list);
		biuldHashToGetIdValuesLengths(list);

		for (MetaData metaData : list) {

			List<String> imports = getRelatedClasses(metaData, dataModel);

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

			context.put("finalParamForView", finalParamForView(list, metaData));

			context.put("finalParamForDtoUpdate", finalParamForDtoUpdate(list,
					metaData));

			context.put("finalParamForDtoUpdateOnlyVariables",
					finalParamForDtoUpdateOnlyVariables(list, metaData));

			context.put("finalParamForViewVariablesInList",
					finalParamForViewVariablesInList(list, metaData));
			context.put("isFinalParamForViewDatesInList",
					isFinalParamForViewDatesInList());
			context.put("finalParamForViewDatesInList", this.dates);

			context.put("finalParamForIdForViewVariablesInList",
					finalParamForIdForViewVariablesInList(list, metaData));
			context.put("isFinalParamForIdForViewDatesInList",
					isFinalParamForIdForViewDatesInList());
			context.put("finalParamForIdForViewDatesInList", this.datesId);

			context.put("finalParamForIdForViewClass",
					finalParamForIdForViewClass(list, metaData));
			context.put("finalParamForIdClassAsVariablesAsString",
					finalParamForIdClassAsVariablesAsString(list, metaData));

			context.put("finalParamForViewForSetsVariablesInList",
					finalParamForViewForSetsVariablesInList(list, metaData));

			context
					.put("finalParamForIdForDtoForSetsVariablesInList",
							finalParamForIdForDtoForSetsVariablesInList(list,
									metaData));

			context.put("finalParamForDtoForSetsVariablesInList",
					finalParamForDtoForSetsVariablesInList(list, metaData));

			context.put("finalParamForIdForDtoInViewForSetsVariablesInList",
					finalParamForIdForDtoInViewForSetsVariablesInList(list,
							metaData));

			context
					.put("finalParamForDtoInViewForSetsVariablesInList",
							finalParamForDtoInViewForSetsVariablesInList(list,
									metaData));

			context
					.put("finalParamForIdForViewForSetsVariablesInList",
							finalParamForIdForViewForSetsVariablesInList(list,
									metaData));

			context.put("finalParamForIdVariablesAsList",
					finalParamForIdVariablesAsList(list, metaData));

			String finalParam = finalParam(list, metaData);
			context.put("finalParam", finalParam);
			metaData.setFinamParam(finalParam);

			String finalParamVariables = finalParamVariables(list, metaData);
			context.put("finalParamVariables", finalParamVariables);
			metaData.setFinamParamVariables(finalParamVariables);

			String finalParamForId = finalParamForId(list, metaData);
			context.put("finalParamForId", finalParamForId(list, metaData));
			metaData.setFinalParamForId(finalParamForId);

			String finalParamForIdVariables = finalParamForIdVariables(list,
					metaData);
			context.put("finalParamForIdVariables", finalParamForIdVariables(
					list, metaData));
			metaData.setFinalParamForIdVariables(finalParamForIdVariables);

			context.put("finalParamVariablesAsList", finalParamVariablesAsList(
					list, metaData));
			context.put("finalParamVariablesAsList2",
					finalParamVariablesAsList2(list, metaData));
			context.put("finalParamVariablesDatesAsList2",
					finalParamVariablesDatesAsList2(list, metaData));
			context.put("isFinalParamDatesAsList", isFinalParamDatesAsList());
			context.put("finalParamDatesAsList", this.datesJSP);

			context.put("finalParamForIdClassAsVariables",
					finalParamForIdClassAsVariables(list, metaData));
			context.put("finalParamForIdClassAsVariables2",
					finalParamForIdClassAsVariables2(list, metaData));
			context.put("isFinalParamForIdClassAsVariablesForDates",
					isFinalParamForIdClassAsVariablesForDates());
			context.put("finalParamForIdClassAsVariablesForDates",
					this.datesIdJSP);

			context.put("finalParamForVariablesDataTablesAsList",
					finalParamForVariablesDataTablesAsList(list, metaData));
			context
					.put("finalParamForVariablesDataTablesForIdAsList",
							finalParamForVariablesDataTablesForIdAsList(list,
									metaData));

			if (metaData.isGetManyToOneProperties()) {
				context.put("getVariableForManyToOneProperties",
						getVariableForManyToOneProperties(metaData
								.getManyToOneProperties(), list));
				context.put("getStringsForManyToOneProperties",
						getStringsForManyToOneProperties(metaData
								.getManyToOneProperties(), list));
			}

			context.put("composedKey", false);

			if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
				context.put("composedKey", true);
				context.put("finalParamForIdClass", finalParamForIdClass(list,
						metaData));
			}

			context.put("metaData", metaData);
			context.put("dataModel", dataModel);

			doDao(metaData, context, hdLocation);

			doBackEndBeans(metaData, context, hdLocation, dataModel);
			doJsp(metaData, context, hdLocation, dataModel);
			doLogic(metaData, context, hdLocation, dataModel, modelName);
			doDto(metaData, context, hdLocation, dataModel, modelName);
			doExceptions(context, hdLocation);
		}

		doUtilites(context, hdLocation, dataModel, modelName);
		doBusinessDelegator(context, hdLocation, dataModel);
		doDaoFactory(dataModel, context, hdLocation);
		doPersitenceXml(dataModel, context, hdLocation);
		doEntityManager(dataModel, context, hdLocation);
		doJspInitialMenu(dataModel, context, hdLocation);
		doFacesConfig(dataModel, context, hdLocation);
		doJspFacelets(context, hdLocation);
	}

	public void doLogic(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doLogic");

		Template ilogic = null;
		Template logic = null;

		StringWriter swIlogic = new StringWriter();
		StringWriter swLogic = new StringWriter();

		try {
			ilogic = Velocity.getTemplate("ILogic.vm");
			logic = Velocity.getTemplate("Logic.vm");
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

			log.info("End doLogic");

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
			dto = Velocity.getTemplate("Dto.vm");
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
		Template utilitiesDataPage = null;
		Template utilitiesDataSource = null;
		Template utilitiesPagedListDataModel = null;

		StringWriter swUtilities = new StringWriter();
		StringWriter swUtilitiesDataPage = new StringWriter();
		StringWriter swUtilitiesDataSource = new StringWriter();
		StringWriter swUtilitiesPagedListDataModel = new StringWriter();

		try {
			utilities = Velocity.getTemplate("Utilities.vm");
			utilitiesDataPage = Velocity.getTemplate("DataPage.vm");
			utilitiesDataSource = Velocity.getTemplate("DataSource.vm");
			utilitiesPagedListDataModel = Velocity
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
		Template exceptions2 = null;

		StringWriter swExceptions = new StringWriter();
		StringWriter swExceptions2 = new StringWriter();

		try {
			exceptions2 = Velocity.getTemplate("ExceptionManager.vm");
			exceptions = Velocity.getTemplate("ExceptionMessages.vm");

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
			exceptions2.merge(context, swExceptions2);
			// System.out.println(swIdao);
			// System.out.println(swdao);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "exceptions"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ "ExceptionMessages.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swExceptions.toString());
			// Close the output stream
			out.close();

			FileWriter fstream2 = new FileWriter(realLocation
					+ "ExceptionManager.java");
			BufferedWriter out2 = new BufferedWriter(fstream2);
			out2.write(swExceptions2.toString());
			// Close the output stream
			out2.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "ExceptionManager.java");

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
			backEndBeans = Velocity.getTemplate("BackEndBeans.vm");

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

			dates = null;
			datesId = null;

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
			jsp = Velocity.getTemplate("JSP.vm");
			jspData = Velocity.getTemplate("JSPdataTables.vm");
			jspDataEditable = Velocity.getTemplate("JSPdataTables-Editable.vm");

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

			datesJSP = null;
			datesIdJSP = null;

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
			jspMainTemplate = Velocity.getTemplate("JSPmainTemplate.vm");
			jspHeader = Velocity.getTemplate("JSPheader.vm");
			jspFooter = Velocity.getTemplate("JSPfooter.vm");

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
			businessDelegator = Velocity
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

	public void doDao(MetaData metaData, VelocityContext context,
			String hdLocation) {

		log.info("Begin doDao");

		Template idao = null;
		Template dao = null;

		StringWriter swIdao = new StringWriter();
		StringWriter swDao = new StringWriter();

		try {
			idao = Velocity.getTemplate("IDAO2.vm");
			dao = Velocity.getTemplate("DAO2.vm");
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

			log.info("End doDao");

		} catch (Exception e) {
			log.info("Error doDao");
		}

	}

	public void doDaoFactory(MetaDataModel metaData, VelocityContext context,
			String hdLocation) {

		log.info("Begin doDaoFactory");

		Template daoFactory = null;
		StringWriter swDaoFactory = new StringWriter();

		try {
			daoFactory = Velocity.getTemplate("JPADaoFactory.vm");
			// logic = Velocity.getTemplate("Logic.vm");
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
					+ "JPADaoFactory.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swDaoFactory.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "JPADaoFactory.java");

			log.info("End doDaoFactory");

		} catch (Exception e) {
			log.info("Error doDaoFactory");
		}

	}

	public void doPersitenceXml(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {

		log.info("Begin doPersitenceXml");

		Template persistence = null;
		StringWriter swPersistence = new StringWriter();

		try {
			persistence = Velocity.getTemplate("persistence.xml.vm");
			// logic = Velocity.getTemplate("Logic.vm");
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
			persistence.merge(context, swPersistence);

			FileWriter fstream = new FileWriter(hdLocation + "META-INF"
					+ GeneratorUtil.slash + "persistence.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swPersistence.toString());
			// Close the output stream
			out.close();

			log.info("End doPersitenceXml");

		} catch (Exception e) {
			log.info("Error doPersitenceXml");
		}

	}

	public void doEntityManager(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {

		log.info("Begin doEntityManager");

		Template entityManager = null;
		StringWriter swEntityManager = new StringWriter();

		try {
			entityManager = Velocity.getTemplate("EntityManagerHelper.vm");
			// logic = Velocity.getTemplate("Logic.vm");
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
					+ GeneratorUtil.slash + "entityManager"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation
					+ "EntityManagerHelper.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swEntityManager.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "EntityManagerHelper.java");

			log.info("End doEntityManager");

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
			jspInitialMenu = Velocity.getTemplate("JSPinitialMenu.vm");
			// logic = Velocity.getTemplate("Logic.vm");
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
			facesConfig = Velocity.getTemplate("faces-config.xml.vm");
			// logic = Velocity.getTemplate("Logic.vm");
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

	public List<String> getRelatedClasses(MetaData metaData,
			MetaDataModel dataModel) {
		List<String> imports = null;
		Member member = null;
		imports = new ArrayList<String>();
		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			imports.add(metaData.getPrimaryKey().getType().getName());
		}
		for (Object object : metaData.getProperties()) {

			if (!(object instanceof SimpleMember)) {

				member = (Member) object;
				imports.add(member.getType().getName());

				getRelatedClasses(dataModel, member, imports);
			} else {
				if (object instanceof SimpleMember) {
					if (((SimpleMember) object).getType().getName()
							.equalsIgnoreCase("java.util.Date")) {
						imports
								.add(((SimpleMember) object).getType()
										.getName());
					}
				}
			}
		}

		return imports;
	}

	public void getRelatedClasses(MetaDataModel dataModel, Member member,
			List<String> imports) {

		for (MetaData metaDataInList : dataModel.getTheMetaData()) {
			if (metaDataInList.getRealClassName().equalsIgnoreCase(
					member.getRealClassName())) {
				if (metaDataInList.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					imports.add(metaDataInList.getPrimaryKey().getType()
							.getName());
				}
			}
		}

	}

	public String finalParam(List<MetaData> theMetaData, MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				finalParam = finalParam + realType + " " + name + ", ";
			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {
					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName() + ", ";
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				// String params[] = getTypeAndvariableForManyToOneProperties(
				// member.getName(), theMetaData);
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getRealClassName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							tmpFinalParam = tmpFinalParam + " " + params[cont];

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam + ", ";
							} else {
								finalParam = finalParam + tmpFinalParam + i
										+ ", ";
							}
						}

					}
				}

			}
		}

		String finalCharacter = new String();

		if (finalParam.length() > 2) {
			finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

			finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
					finalParam.length() - 2) : finalParam;
		}

		return finalParam;
	}

	public String finalParamVariables(List<MetaData> theMetaData,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				finalParam = finalParam + name + ", ";
			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {
					finalParam = finalParam + member.getName() + ", ";
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam + ", ";
							}
						}

					}
				}

			}
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	public List<String> finalParamVariablesAsList(List<MetaData> theMetaData,
			MetaData metaData) {
		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		this.datesJSP = new ArrayList<String>();

		// if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
		// Field[] field = metaData.getComposeKey().getDeclaredFields();
		// for (Field field2 : field) {
		//				
		// String name = field2.getName().substring(0, 1).toUpperCase()
		// + field2.getName().substring(1);
		//				
		// finalParam = finalParam + name + ", ";
		// finalParam2.add(name);
		// }
		// }

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!metaData.getPrimaryKey().getName()
						.equals(member.getName())) {

					String name = member.getName().substring(0, 1)
							.toUpperCase()
							+ member.getName().substring(1);

					finalParam = finalParam + name + ", ";

					if (member.getRealClassName().equalsIgnoreCase("date")) {
						this.datesJSP.add(name);
					} else {
						finalParam2.add(name);
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String forstCont = params[cont];

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {

								String name = tmpFinalParam.substring(0, 1)
										.toUpperCase()
										+ tmpFinalParam.substring(1);

								finalParam = finalParam + tmpFinalParam + ", ";

								if (forstCont.equalsIgnoreCase("date")) {
									this.datesJSP.add(name);
								} else {
									finalParam2.add(name);
								}

							}
						}

					}
				}

				// String tmpFinalParam = params[1];
				// if (!finalParam.contains(tmpFinalParam)) {
				//
				// String name = params[1].substring(0, 1).toUpperCase()
				// + params[1].substring(1);
				//
				// finalParam = finalParam + params[1] + ", ";
				// finalParam2.add(name);
				// }
			}
		}

		List<String> primaryKey = finalParamForIdClassAsVariables(theMetaData,
				metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	public List<String> finalParamVariablesAsList2(List<MetaData> theMetaData,
			MetaData metaData) {
		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!metaData.getPrimaryKey().getName()
						.equals(member.getName())) {

					String name = member.getName().substring(0, 1)
							.toUpperCase()
							+ member.getName().substring(1);

					finalParam = finalParam + name + ", ";

					finalParam2.add(name);
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String forstCont = params[cont];

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {

								String name = tmpFinalParam.substring(0, 1)
										.toUpperCase()
										+ tmpFinalParam.substring(1);

								finalParam = finalParam + tmpFinalParam + ", ";

								finalParam2.add(name);
							}
						}

					}
				}
			}
		}

		List<String> primaryKey = finalParamForIdClassAsVariables(theMetaData,
				metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	public List<String> finalParamVariablesDatesAsList2(
			List<MetaData> theMetaData, MetaData metaData) {
		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!metaData.getPrimaryKey().getName()
						.equals(member.getName())) {
					String type = member.getRealClassName();
					if (type.equalsIgnoreCase("date")) {
						String name = member.getName().substring(0, 1)
								.toLowerCase()
								+ member.getName().substring(1);

						finalParam = finalParam + name + ", ";

						finalParam2.add(name);
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String forstCont = params[cont];

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];
							String tmpFinalType = "";
							try {
								tmpFinalType = params[cont - 1];
							} catch (Exception e) {
								tmpFinalType = "";
							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								if (tmpFinalType.equalsIgnoreCase("date")) {
									String name = tmpFinalParam.substring(0, 1)
											.toLowerCase()
											+ tmpFinalParam.substring(1);

									finalParam = finalParam + tmpFinalParam
											+ ", ";

									finalParam2.add(name);
								}
							}
						}

					}
				}
			}
		}

		List<String> primaryKey = finalParamForIdClassAsVariables(theMetaData,
				metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	public List<String> finalParamForVariablesDataTablesForIdAsList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {

				String name = field2.getName();

				finalParam2
						.add(metaData.getPrimaryKey().getName() + "." + name);
			}
		} else {
			finalParam2.add(metaData.getPrimaryKey().getName());
		}

		return finalParam2;

	}

	public List<String> finalParamForVariablesDataTablesAsList(
			List<MetaData> theMetaData, MetaData metaData) {

		// if (metaData.getRealClassName().equalsIgnoreCase("SampleInReb")) {
		// String tmp = null;
		// System.out.println(tmp);
		// }

		List<String> finalParam2 = new ArrayList<String>();
		//
		// if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
		// Field[] field = metaData.getComposeKey().getDeclaredFields();
		// for (Field field2 : field) {
		//
		// String name = field2.getName();
		//
		// finalParam2
		// .add(metaData.getPrimaryKey().getName() + "." + name);
		// }
		// } else {
		// finalParam2.add(metaData.getPrimaryKey().getName());
		// }

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!metaData.getPrimaryKey().getName()
						.equals(member.getName())) {

					String name = member.getName();

					finalParam2.add(name);
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];

							HashMap map = hashMapIds;

							String name = null;
							String idName = null;
							try {
								idName = hashMapIds.get(tmpFinalParam);

							} catch (Exception e) {
								name = member.getName() + "." + tmpFinalParam;
							}

							if (idName != null)
								name = member.getName() + "." + idName + "."
										+ tmpFinalParam.split("_")[0];
							else
								name = member.getName() + "."
										+ tmpFinalParam.split("_")[0];

							finalParam2.add(name);

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

						}
					}
				}

				// String tmpFinalParam = params[1];
				// if (!finalParam.contains(tmpFinalParam)) {
				//
				// String name = params[1].substring(0, 1).toUpperCase()
				// + params[1].substring(1);
				//
				// finalParam = finalParam + params[1] + ", ";
				// finalParam2.add(name);
				// }
			}
		}

		// List<String> primaryKey =
		// finalParamForIdClassAsVariables(theMetaData,
		// metaData);

		// return ListUtils.subtract(finalParam2, primaryKey);
		return finalParam2;
	}

	public List<String> finalParamForIdVariablesAsList(
			List<MetaData> theMetaData, MetaData metaData) {
		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.getRealClassName().equalsIgnoreCase("NodoProduccion")) {
			String tmp = "";
			System.out.println(tmp);
		}

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {

				String name = field2.getName();

				finalParam = finalParam + name + ", ";
				finalParam2.add(ifcondition + name + "==null"
						+ ifconditionClose + throwExceptionNull + "\"" + name
						+ "\"" + throwExceptionClose);

				finalParam2 = addVariablesValuesToListDependingOnDataTypeForID(
						finalParam2, field2, name, metaData.getComposeKey());

			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {

					try {
						if (member.getNullable() == false) {
							String name = member.getName();

							finalParam = finalParam + name + ", ";
							finalParam2.add(ifcondition + name + "==null"
									+ ifconditionClose + throwExceptionNull
									+ "\"" + name + "\"" + throwExceptionClose);

						}

						finalParam2 = addVariablesValuesToListDependingOnDataType(
								finalParam2, member.getRealClassName(), member
										.getName(), member.getPrecision()
										.toString(), member.getScale()
										.toString(), member.getLength()
										.toString());
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {

			for (Member member : metaData.getManyToOneProperties()) {

				manyToOneTempHash = new HashMap<String, Member>();

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmp = params[cont];

							tmpFinalParam = finalParam + tmp + ", ";

							if (!finalParam.contains(tmpFinalParam)) {

								ManyToOneMember manyToOneMember = (ManyToOneMember) member;

								String variableNames = (tmp.split("_"))[0];
								String className = (tmp.split("_"))[1];

								Boolean nullable = null;

								try {
									nullable = manyToOneMember
											.getHashMapNullableColumn()
											.get(variableNames.toUpperCase());
								} catch (Exception e) {
									// TODO: handle exception
								}

								if (nullable == null) {
									try {
										nullable = manyToOneMember
												.getHashMapNullableColumn()
												.get(className.toUpperCase());
									} catch (Exception e) {
										// TODO: handle exception
									}
								}

								Member primarySimple = manyToOneTempHash
										.get(variableNames);

								try {
									if (nullable == false) {
										finalParam2.add(ifcondition + tmp
												+ "==null" + ifconditionClose
												+ throwExceptionNull + "\""
												+ tmp + "\""
												+ throwExceptionClose);
									}
								} catch (Exception e) {
									// System.out.println(e.getMessage());
								}

								try {

									finalParam2 = addVariablesValuesToListDependingOnDataType(
											finalParam2, primarySimple
													.getRealClassName(), tmp,
											primarySimple.getPrecision()
													.toString(), primarySimple
													.getScale().toString(),
											primarySimple.getLength()
													.toString());
								} catch (Exception e) {
									// TODO: handle exception
								}

							} else {
								ManyToOneMember manyToOneMember = (ManyToOneMember) member;

								String variableNames = (tmp.split("_"))[0] + i;
								String variableNames2 = (tmp.split("_"))[0];
								String className = (tmp.split("_"))[1] + i;

								Boolean nullable = null;

								try {
									nullable = manyToOneMember
											.getHashMapNullableColumn()
											.get(variableNames.toUpperCase());
								} catch (Exception e) {
									// TODO: handle exception
								}

								if (nullable == null) {
									try {
										nullable = manyToOneMember
												.getHashMapNullableColumn()
												.get(className.toUpperCase());
									} catch (Exception e) {
										// TODO: handle exception
									}
								}

								Member primarySimple = manyToOneTempHash
										.get(variableNames2);

								try {
									if (nullable == false) {
										finalParam2.add(ifcondition + tmp
												+ "==null" + ifconditionClose
												+ throwExceptionNull + "\""
												+ tmp + "\""
												+ throwExceptionClose);
									}
								} catch (Exception e) {
									// System.out.println(e.getMessage());
								}

								try {

									finalParam2 = addVariablesValuesToListDependingOnDataType(
											finalParam2, primarySimple
													.getRealClassName(), tmp,
											primarySimple.getPrecision()
													.toString(), primarySimple
													.getScale().toString(),
											primarySimple.getLength()
													.toString());
								} catch (Exception e) {
									// TODO: handle exception
								}
							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;
						}
					}
				}
			}
		}

		return finalParam2;
	}

	public String finalParamForView(List<MetaData> theMetaData,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String nameWithCapitalOnFirst = getGetNameOfPrimaryName(name);
				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				if (realType.equalsIgnoreCase("date")) {
					finalParam = finalParam + "(txt" + nameWithCapitalOnFirst
							+ ".getValue())==null||(txt"
							+ nameWithCapitalOnFirst
							+ ".getValue()).equals(\"\")?null:" + "("
							+ realType + ")txt" + nameWithCapitalOnFirst
							+ ".getValue(), ";
				} else {
					finalParam = finalParam + "(txt" + nameWithCapitalOnFirst
							+ ".getValue())==null||(txt"
							+ nameWithCapitalOnFirst
							+ ".getValue()).equals(\"\")?null:new " + realType
							+ "(txt" + nameWithCapitalOnFirst
							+ ".getValue().toString()), ";
				}

			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {

					// finalParam = finalParam + "new "
					// + member.getRealClassName() + "((String)txt"
					// + member.getGetNameOfPrimaryName()
					// + ".getValue()), ";

					if (member.getRealClassName().equalsIgnoreCase("date")) {
						finalParam = finalParam + "(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue())==null||(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue()).equals(\"\")?null:" + "("
								+ member.getRealClassName() + ")txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue(), ";
					} else {
						finalParam = finalParam + "(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue())==null||(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue()).equals(\"\")?null:new "
								+ member.getRealClassName() + "(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue().toString()), ";
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmp = getGetNameOfPrimaryName(params[cont]);

							// tmpFinalParam = "new " + tmpFinalParam
							// + "((String)txt" + tmp;

							if (tmpFinalParam.equalsIgnoreCase("date")) {
								tmpFinalParam = "(txt" + tmp
										+ ".getValue())==null||(txt" + tmp
										+ ".getValue()).equals(\"\")?null:"
										+ "(" + tmpFinalParam + ")txt" + tmp
										+ ".getValue(), ";
							} else {
								tmpFinalParam = "(txt" + tmp
										+ ".getValue())==null||(txt" + tmp
										+ ".getValue()).equals(\"\")?null:new "
										+ tmpFinalParam + "(txt" + tmp
										+ ".getValue().toString()), ";
							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam;
							}
						}

					}
				}

				// String tmpFinalParam = "(" + params[0] + ") txt" + params[2];
				// if (!finalParam.contains(tmpFinalParam)) {
				// finalParam = finalParam + "(" + params[0] + ") txt"
				// + params[2] + ".getValue(), ";
				// }
			}
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	public String finalParamForDtoUpdate(List<MetaData> theMetaData,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				if (realType.equalsIgnoreCase("date")) {
					finalParam = finalParam + name + "==null || " + name
							+ ".equals(\"\")?null:" + name + ", ";
				} else {
					finalParam = finalParam + name + "==null||" + name
							+ ".equals(\"\")?null:new " + realType + "(" + name
							+ "), ";
				}

			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {

					// finalParam = finalParam + "new "
					// + member.getRealClassName() + "((String)txt"
					// + member.getGetNameOfPrimaryName()
					// + ".getValue()), ";

					if (member.getRealClassName().equalsIgnoreCase("date")) {
						finalParam = finalParam + member.getName()
								+ "==null|| " + member.getName()
								+ ".equals(\"\")?null:" + member.getName()
								+ ", ";
					} else {
						finalParam = finalParam + member.getName() + "==null||"
								+ member.getName() + ".equals(\"\")?null:new "
								+ member.getRealClassName() + "("
								+ member.getName() + "), ";
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmp = params[cont];

							// tmpFinalParam = "new " + tmpFinalParam
							// + "((String)txt" + tmp;

							if (tmpFinalParam.equalsIgnoreCase("date")) {
								tmpFinalParam = tmp + "==null||" + tmp
										+ ".equals(\"\")?null:" + tmp + ", ";
							} else {
								tmpFinalParam = tmp + "==null|| " + tmp
										+ ".equals(\"\")?null:new "
										+ tmpFinalParam + "(" + tmp + "), ";
							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam;
							}
						}

					}
				}

				// String tmpFinalParam = "(" + params[0] + ") txt" + params[2];
				// if (!finalParam.contains(tmpFinalParam)) {
				// finalParam = finalParam + "(" + params[0] + ") txt"
				// + params[2] + ".getValue(), ";
				// }
			}
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	public String finalParamForDtoUpdateOnlyVariables(
			List<MetaData> theMetaData, MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				finalParam = finalParam + name + ", ";

			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {

					finalParam = finalParam + member.getName() + ", ";
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmp = params[cont];

							tmpFinalParam = tmp + ", ";

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam;
							}
						}
					}
				}
			}
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	public List<String> finalParamForViewVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		this.dates = new ArrayList<String>();

		if (metaData.getRealClassName().equalsIgnoreCase("DiaNoLaboral")) {
			String tmp = "";
			System.out.println(tmp);
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {
					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName();
					String tmp2 = (member.getName().substring(0, 1))
							.toUpperCase()
							+ member.getName().substring(1);
					if (member.getRealClassName().equalsIgnoreCase("date")) {
						this.dates.add(tmp2);
					} else {

						finalParam2.add(tmp2);
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							tmpFinalParam = tmpFinalParam + " " + params[cont];

							if (!finalParam.contains(tmpFinalParam)) {

								finalParam = finalParam + tmpFinalParam;
								;

								String tmp3 = (params[cont].substring(0, 1))
										.toUpperCase()
										+ params[cont].substring(1);

								if (tmpFinalParam.equalsIgnoreCase("date")) {
									this.dates.add(tmp3);
								} else {
									finalParam2.add(tmp3);
								}

							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

						}

					}
				}

				// String tmpFinalParam = params[0] + " " + params[1];
				//				
				// if (!finalParam.contains(tmpFinalParam)) {
				//					
				// finalParam = finalParam + params[0] + " " + params[1];
				//					
				// String tmp3 = (params[1].substring(0, 1)).toUpperCase()
				// + params[1].substring(1);
				//					
				// finalParam2.add(tmp3);
				// }
			}
		}

		List primaryKey = finalParamForIdForViewVariablesInList(theMetaData,
				metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	public List<String> finalParamForViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!member.getName().equalsIgnoreCase(
						metaData.getPrimaryKey().getName())) {

					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName();
					String tmp2 = "txt"
							+ (member.getName().substring(0, 1)).toUpperCase()
							+ member.getName().substring(1) + ".setValue("
							+ "entity.get"
							+ (member.getName().substring(0, 1)).toUpperCase()
							+ member.getName().substring(1) + "()" + ");"
							+ "txt"
							+ (member.getName().substring(0, 1)).toUpperCase()
							+ (member.getName().substring(1))
							+ ".setDisabled(false);";
					finalParam2.add(tmp2);

				}
			}
		}

		// txtCliCedula.setValue(entity.getClientes().getCliCedula());
		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				String tmpFinalParam = "";
				String tmpFinalParam1 = "";
				String tmpFinalParam2 = "";
				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							tmpFinalParam1 = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam2 = params[cont];

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam
											+ ", ";

									HashMap<String, String> map = hashMapIds;

									String hashMapProve = "";
									String hashMapProve1 = "";
									try {

										hashMapProve = (hashMapIds
												.get(tmpFinalParam2));

									} catch (Exception e) {
										// TODO: handle exception
									}
									String tmp3 = "";

									if (hashMapProve == null) {
										tmp3 = "txt"
												+ (tmpFinalParam2.substring(0,
														1)).toUpperCase()
												+ tmpFinalParam2.substring(1)
												+ ".setValue("
												+ "entity.get"
												+ member.getRealClassName()
												+ "().get"
												+ (tmpFinalParam2.substring(0,
														1)).toUpperCase()
												+ tmpFinalParam2
														.substring(
																1,
																tmpFinalParam2
																		.lastIndexOf("_"))
												+ "()"
												+ ");"
												+ "txt"
												+ (tmpFinalParam2.substring(0,
														1)).toUpperCase()
												+ tmpFinalParam2.substring(1)
												+ ".setDisabled(false);";
									} else {
										if (hashMapProve.equals("")) {

											tmp3 = "txt"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ ".setValue("
													+ "entity.get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "()"
													+ ");"
													+ "txt"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ ".setDisabled(false);";

										} else {

											tmp3 = "txt"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ ".setValue("
													+ "entity.get"
													+ member.getRealClassName()
													+ "().get"
													+ hashMapProve.substring(0,
															1).toUpperCase()
													+ hashMapProve.substring(1)
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "()"
													+ ");"
													+ "txt"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ ".setDisabled(false);";

										}
									}

									finalParam2.add(tmp3);
								}
							}

						}
					}

				} else {

					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								tmpFinalParam1 = params[cont];

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam;

									String tmp3 = "txt"
											+ (tmpFinalParam1.substring(0, 1))
													.toUpperCase()
											+ tmpFinalParam1.substring(1)
											+ ".setValue("
											+ "entity.get"
											+ member.getRealClassName()
											+ "()"
											+ ".get"
											+ tmpFinalParam1.substring(0, 1)
													.toUpperCase()
											+ tmpFinalParam1.substring(1,
													tmpFinalParam1
															.lastIndexOf("_"))
											+ "()"
											+ ");"
											+ "txt"
											+ (tmpFinalParam1.substring(0, 1))
													.toUpperCase()
											+ tmpFinalParam1.substring(1)
											+ ".setDisabled(false);";

									finalParam2.add(tmp3);
								}
							}
						}
					}
				}
			}
		}

		List primaryKey = finalParamForIdForViewForSetsVariablesInList(
				theMetaData, metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	public List<String> finalParamForDtoForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!member.getName().equalsIgnoreCase(
						metaData.getPrimaryKey().getName())) {

					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName();
					if (!member.getRealClassName().equalsIgnoreCase("date")) {
						String tmp2 = member.getName()
								+ "="
								+ metaData.getRealClassNameAsVariable()
								+ ".get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "()!=null ? "
								+ metaData.getRealClassNameAsVariable()
								+ ".get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "().toString() : null;";
						finalParam2.add(tmp2);
					} else {
						String tmp2 = member.getName()
								+ "="
								+ metaData.getRealClassNameAsVariable()
								+ ".get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1) + "();";
						finalParam2.add(tmp2);
					}

				}
			}
		}

		// txtCliCedula.setValue(entity.getClientes().getCliCedula());
		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				String tmpFinalParam = "";
				String tmpFinalParam1 = "";
				String tmpFinalParam2 = "";
				String tmpFinalParam3 = "";
				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							tmpFinalParam1 = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam2 = params[cont];
								try {
									tmpFinalParam3 = params[cont - 1];
								} catch (Exception e) {
									tmpFinalParam3 = "";
								}

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam
											+ ", ";

									HashMap<String, String> map = hashMapIds;

									String hashMapProve = "";
									String hashMapProve1 = "";
									try {

										hashMapProve = (hashMapIds
												.get(tmpFinalParam2));

									} catch (Exception e) {
										// TODO: handle exception
									}
									String tmp3 = "";

									if (hashMapProve == null) {
										if (!tmpFinalParam3
												.equalsIgnoreCase("date")) {
											tmp3 = tmpFinalParam2
													+ "="
													+ metaData
															.getRealClassNameAsVariable()
													+ ".get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "()!=null ? "
													+ metaData
															.getRealClassNameAsVariable()
													+ ".get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "().toString() : null;";
										} else {
											tmp3 = tmpFinalParam2
													+ "="
													+ metaData
															.getRealClassNameAsVariable()
													+ ".get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "();";
										}
									} else {
										if (hashMapProve.equals("")) {
											if (!tmpFinalParam3
													.equalsIgnoreCase("date")) {
												tmp3 = tmpFinalParam2
														+ "="
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "()!=null ? "
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "().toString() : null;";
											} else {
												tmp3 = tmpFinalParam2
														+ "="
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "();";
											}
										} else {
											if (!tmpFinalParam3
													.equalsIgnoreCase("date")) {
												tmp3 = tmpFinalParam2
														+ "="
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "()!=null ? "
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "().toString() : null;";
											} else {
												tmp3 = tmpFinalParam2
														+ "="
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "();";
											}
										}
									}

									finalParam2.add(tmp3);
								}
							}

						}
					}

				} else {

					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								tmpFinalParam1 = params[cont];
								try {
									tmpFinalParam3 = params[cont - 1];
								} catch (Exception e) {
									tmpFinalParam3 = "";
								}

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam;
									if (!tmpFinalParam3
											.equalsIgnoreCase("date")) {
										String tmp3 = tmpFinalParam1
												+ "="
												+ metaData
														.getRealClassNameAsVariable()
												+ ".get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "()!=null ?"
												+ metaData
														.getRealClassNameAsVariable()
												+ ".get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "().toString() : null;";

										finalParam2.add(tmp3);
									} else {
										String tmp3 = tmpFinalParam1
												+ "="
												+ metaData
														.getRealClassNameAsVariable()
												+ ".get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "();";

										finalParam2.add(tmp3);
									}
								}
							}
						}
					}
				}
			}
		}

		List primaryKey = finalParamForIdForDtoForSetsVariablesInList(
				theMetaData, metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	public List<String> finalParamForIdForDtoForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				finalParam = finalParam + name;
				String type = field2.getClass().getCanonicalName();
				// String idName = metaData.getPrimaryKey().getName();
				// txtMasCodigo.setValue(entity.getId().getMasCodigo());
				if (!type.equalsIgnoreCase("date")) {
					String tmp1 = name
							+ "="
							+ metaData.getRealClassNameAsVariable()
							+ ".get"
							+ metaData.getPrimaryKey()
									.getGetNameOfPrimaryName() + "()" + ".get"
							+ (name.substring(0, 1)).toUpperCase()
							+ name.substring(1) + "().toString()" + ";";

					finalParam2.add(tmp1);
				} else {
					String tmp1 = name
							+ "="
							+ metaData.getRealClassNameAsVariable()
							+ ".get"
							+ metaData.getPrimaryKey()
									.getGetNameOfPrimaryName() + "()" + ".get"
							+ (name.substring(0, 1)).toUpperCase()
							+ name.substring(1) + "()" + ";";

					finalParam2.add(tmp1);
				}

			}

		} else {
			// txtCliCedula.setValue(entity.getCliCedula());
			finalParam = metaData.getPrimaryKey().getName();
			String type = metaData.getPrimaryKey().getRealClassName();
			if (!type.equalsIgnoreCase("date")) {
				String tmp1 = finalParam + "="
						+ metaData.getRealClassNameAsVariable() + ".get"
						+ (finalParam.substring(0, 1)).toUpperCase()
						+ finalParam.substring(1) + "().toString()" + ";";
				finalParam2.add(tmp1);
			} else {
				String tmp1 = finalParam + "="
						+ metaData.getRealClassNameAsVariable() + ".get"
						+ (finalParam.substring(0, 1)).toUpperCase()
						+ finalParam.substring(1) + "()" + ";";
				finalParam2.add(tmp1);
			}

		}

		return finalParam2;

	}

	public List<String> finalParamForIdForDtoInViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				finalParam = finalParam + name;
				String type = field2.getClass().getCanonicalName();
				// String idName = metaData.getPrimaryKey().getName();
				// txtMasCodigo.setValue(entity.getId().getMasCodigo());
				if (!type.equalsIgnoreCase("date")) {
					String tmp1 = metaData.getRealClassNameAsVariable()
							+ "DTO2.set"
							+ name.substring(0, 1).toUpperCase()
							+ name.substring(1)
							+ "("
							+ metaData.getRealClassNameAsVariable()
							+ "Tmp.get"
							+ metaData.getPrimaryKey()
									.getGetNameOfPrimaryName() + "()" + ".get"
							+ (name.substring(0, 1)).toUpperCase()
							+ name.substring(1) + "().toString()" + ");";

					finalParam2.add(tmp1);
				} else {
					String tmp1 = metaData.getRealClassNameAsVariable()
							+ "DTO2.set"
							+ name.substring(0, 1).toUpperCase()
							+ name.substring(1)
							+ "("
							+ metaData.getRealClassNameAsVariable()
							+ "Tmp.get"
							+ metaData.getPrimaryKey()
									.getGetNameOfPrimaryName() + "()" + ".get"
							+ (name.substring(0, 1)).toUpperCase()
							+ name.substring(1) + "()" + ");";

					finalParam2.add(tmp1);
				}
			}

		} else {
			// txtCliCedula.setValue(entity.getCliCedula());
			String type = metaData.getPrimaryKey().getRealClassName();
			finalParam = metaData.getPrimaryKey().getName();
			if (!type.equalsIgnoreCase("date")) {
				String tmp1 = metaData.getRealClassNameAsVariable()
						+ "DTO2.set" + finalParam.substring(0, 1).toUpperCase()
						+ finalParam.substring(1) + "("
						+ metaData.getRealClassNameAsVariable() + "Tmp.get"
						+ (finalParam.substring(0, 1)).toUpperCase()
						+ finalParam.substring(1) + "().toString())" + ";";
				finalParam2.add(tmp1);
			} else {
				String tmp1 = metaData.getRealClassNameAsVariable()
						+ "DTO2.set" + finalParam.substring(0, 1).toUpperCase()
						+ finalParam.substring(1) + "("
						+ metaData.getRealClassNameAsVariable() + "Tmp.get"
						+ (finalParam.substring(0, 1)).toUpperCase()
						+ finalParam.substring(1) + "())" + ";";
				finalParam2.add(tmp1);
			}
		}

		return finalParam2;

	}

	public List<String> finalParamForDtoInViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!member.getName().equalsIgnoreCase(
						metaData.getPrimaryKey().getName())) {

					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName();
					if (!member.getRealClassName().equalsIgnoreCase("date")) {
						String tmp2 = metaData.getRealClassNameAsVariable()
								+ "DTO2.set"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "("
								+ metaData.getRealClassNameAsVariable()
								+ "Tmp.get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "()!=null ?"
								+ metaData.getRealClassNameAsVariable()
								+ "Tmp.get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "().toString() : null);";
						finalParam2.add(tmp2);
					} else {
						String tmp2 = metaData.getRealClassNameAsVariable()
								+ "DTO2.set"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "("
								+ metaData.getRealClassNameAsVariable()
								+ "Tmp.get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1) + "());";
						finalParam2.add(tmp2);
					}

				}
			}
		}

		// txtCliCedula.setValue(entity.getClientes().getCliCedula());
		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				String tmpFinalParam = "";
				String tmpFinalParam1 = "";
				String tmpFinalParam2 = "";
				String tmpFinalParam3 = "";
				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							tmpFinalParam1 = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam2 = params[cont];
								try {
									tmpFinalParam3 = params[cont - 1];
								} catch (Exception e) {
									tmpFinalParam3 = "";
								}

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam
											+ ", ";

									HashMap<String, String> map = hashMapIds;

									String hashMapProve = "";
									String hashMapProve1 = "";
									try {

										hashMapProve = (hashMapIds
												.get(tmpFinalParam2));

									} catch (Exception e) {
										// TODO: handle exception
									}
									String tmp3 = "";

									if (hashMapProve == null) {
										if (!tmpFinalParam3
												.equalsIgnoreCase("date")) {
											tmp3 = metaData
													.getRealClassNameAsVariable()
													+ "DTO2.set"
													+ tmpFinalParam2.substring(
															0, 1).toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ "("
													+ metaData
															.getRealClassNameAsVariable()
													+ "Tmp.get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "()!=null ? "
													+ metaData
															.getRealClassNameAsVariable()
													+ "Tmp.get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "().toString() : null);";
										} else {
											tmp3 = metaData
													.getRealClassNameAsVariable()
													+ "DTO2.set"
													+ tmpFinalParam2.substring(
															0, 1).toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ "("
													+ metaData
															.getRealClassNameAsVariable()
													+ "Tmp.get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "());";
										}
									} else {
										if (hashMapProve.equals("")) {
											if (!tmpFinalParam3
													.equalsIgnoreCase("date")) {
												tmp3 = metaData
														.getRealClassNameAsVariable()
														+ "DTO2.set"
														+ tmpFinalParam2
																.substring(0, 1)
																.toUpperCase()
														+ tmpFinalParam2
																.substring(1)
														+ "("
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "()!=null ? "
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "().toString() : null);";
											} else {
												tmp3 = metaData
														.getRealClassNameAsVariable()
														+ "DTO2.set"
														+ tmpFinalParam2
																.substring(0, 1)
																.toUpperCase()
														+ tmpFinalParam2
																.substring(1)
														+ "("
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "());";
											}
										} else {
											if (!tmpFinalParam3
													.equalsIgnoreCase("date")) {
												tmp3 = metaData
														.getRealClassNameAsVariable()
														+ "DTO2.set"
														+ tmpFinalParam2
																.substring(0, 1)
																.toUpperCase()
														+ tmpFinalParam2
																.substring(1)
														+ "("
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "()!=null ?"
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "().toString() : null);";
											} else {
												tmp3 = metaData
														.getRealClassNameAsVariable()
														+ "DTO2.set"
														+ tmpFinalParam2
																.substring(0, 1)
																.toUpperCase()
														+ tmpFinalParam2
																.substring(1)
														+ "("
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "());";
											}
										}
									}

									finalParam2.add(tmp3);
								}
							}

						}
					}

				} else {

					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								tmpFinalParam1 = params[cont];
								try {
									tmpFinalParam3 = params[cont - 1];
								} catch (Exception e) {
									tmpFinalParam3 = "";
								}

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam;
									if (!tmpFinalParam3
											.equalsIgnoreCase("date")) {
										String tmp3 = metaData
												.getRealClassNameAsVariable()
												+ "DTO2.set"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1.substring(1)
												+ "("
												+ metaData
														.getRealClassNameAsVariable()
												+ "Tmp.get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "()!=null ? "
												+ metaData
														.getRealClassNameAsVariable()
												+ "Tmp.get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "().toString() : null);";

										finalParam2.add(tmp3);
									} else {
										String tmp3 = metaData
												.getRealClassNameAsVariable()
												+ "DTO2.set"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1.substring(1)
												+ "("
												+ metaData
														.getRealClassNameAsVariable()
												+ "Tmp.get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "());";

										finalParam2.add(tmp3);
									}
								}
							}
						}
					}
				}
			}
		}

		List primaryKey = finalParamForIdForDtoInViewForSetsVariablesInList(
				theMetaData, metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	public List<String> finalParamForIdForViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				finalParam = finalParam + name;
				// String idName = metaData.getPrimaryKey().getName();
				// txtMasCodigo.setValue(entity.getId().getMasCodigo());
				String tmp1 = "txt" + (name.substring(0, 1)).toUpperCase()
						+ name.substring(1) + ".setValue(" + "entity.get"
						+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
						+ "()" + ".get" + (name.substring(0, 1)).toUpperCase()
						+ name.substring(1) + "()" + ");" + "txt"
						+ (name.substring(0, 1)).toUpperCase()
						+ name.substring(1) + ".setDisabled(true);";

				finalParam2.add(tmp1);
			}

		} else {
			// txtCliCedula.setValue(entity.getCliCedula());
			finalParam = metaData.getPrimaryKey().getName();
			String tmp1 = "txt" + (finalParam.substring(0, 1)).toUpperCase()
					+ finalParam.substring(1) + ".setValue(" + "entity.get"
					+ (finalParam.substring(0, 1)).toUpperCase()
					+ finalParam.substring(1) + "()" + ");" + "txt"
					+ (finalParam.substring(0, 1)).toUpperCase()
					+ finalParam.substring(1) + ".setDisabled(true);";
			finalParam2.add(tmp1);
		}

		return finalParam2;

	}

	public List<String> finalParamForIdClass(List<MetaData> list,
			MetaData metaData) {
		List<String> finalParam = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			String id = metaData.getPrimaryKey().getRealClassName() + " "
					+ metaData.getPrimaryKey().getName() + "Class = " + "new "
					+ metaData.getPrimaryKey().getRealClassName() + "();";
			finalParam.add(id);
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				String setToId = metaData.getPrimaryKey().getName()
						+ "Class.set" + name.substring(0, 1).toUpperCase()
						+ name.substring(1, name.length()) + "(" + name + ");";
				finalParam.add(setToId);
			}
		}

		return finalParam;
	}

	public List<String> finalParamForIdClassAsVariables(List<MetaData> list,
			MetaData metaData) {
		List<String> finalParam = new ArrayList<String>();

		this.datesIdJSP = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String realType = getRealClassName(field2.getType().getName());

				if (realType.equalsIgnoreCase("date")) {
					this.datesIdJSP.add(name);
				} else {
					finalParam.add(name);
				}
			}
		} else {
			finalParam.add(metaData.getPrimaryKey().getName());
		}

		return finalParam;
	}

	public List<String> finalParamForIdClassAsVariables2(List<MetaData> list,
			MetaData metaData) {
		List<String> finalParam = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				finalParam.add(name);
			}
		} else {
			finalParam.add(metaData.getPrimaryKey().getName());
		}

		return finalParam;
	}

	public String finalParamForIdClassAsVariablesAsString(List<MetaData> list,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String nameWithCapitalOnFirst = getGetNameOfPrimaryName(name);
				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				// finalParam = finalParam + "new " + realType + "((String)txt"
				// + nameWithCapitalOnFirst + ".getValue()), ";
				if (realType.equalsIgnoreCase("date")) {
					finalParam = finalParam + "(txt" + nameWithCapitalOnFirst
							+ ".getValue())==null||(txt"
							+ nameWithCapitalOnFirst
							+ ".getValue()).equals(\"\")?null:(" + realType
							+ ")txt" + nameWithCapitalOnFirst + ".getValue(), ";
				} else {
					finalParam = finalParam + "(txt" + nameWithCapitalOnFirst
							+ ".getValue())==null||(txt"
							+ nameWithCapitalOnFirst
							+ ".getValue()).equals(\"\")?null:new " + realType
							+ "(txt" + nameWithCapitalOnFirst
							+ ".getValue().toString()), ";
				}

			}
		} else {
			// finalParam = "new " + metaData.getPrimaryKey().getRealClassName()
			// + "((String) txt"
			// + metaData.getPrimaryKey().getGetNameOfPrimaryName()
			// + ".getValue())";

			if (metaData.getPrimaryKey().getRealClassName().equalsIgnoreCase(
					"date")) {
				finalParam = "(txt"
						+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
						+ ".getValue())==null||(txt"
						+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
						+ ".getValue()).equals(\"\")?null:("
						+ metaData.getPrimaryKey().getRealClassName() + ")txt"
						+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
						+ ".getValue(), ";
			} else {
				finalParam = "(txt"
						+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
						+ ".getValue())==null||(txt"
						+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
						+ ".getValue()).equals(\"\")?null:new "
						+ metaData.getPrimaryKey().getRealClassName() + "(txt"
						+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
						+ ".getValue().toString()), ";
			}

		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	public List<String> finalParamForIdForViewClass(List<MetaData> list,
			MetaData metaData) {
		List<String> finalParam = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			String id = metaData.getPrimaryKey().getRealClassName() + " "
					+ metaData.getPrimaryKey().getName() + " = " + "new "
					+ metaData.getPrimaryKey().getRealClassName() + "();";
			finalParam.add(id);
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				String nameFragment = name.substring(0, 1).toUpperCase()
						+ name.substring(1);

				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				String setToId = new String();

				if (realType.equalsIgnoreCase("date")) {
					setToId = metaData.getPrimaryKey().getName() + ".set"
							+ nameFragment + "((txt" + nameFragment
							+ ".getValue())==null||(txt" + nameFragment
							+ ".getValue()).equals(\"\")?null:" + "("
							+ realType + ")txt" + nameFragment
							+ ".getValue());";
				} else {
					setToId = metaData.getPrimaryKey().getName() + ".set"
							+ nameFragment + "((txt" + nameFragment
							+ ".getValue())==null||(txt" + nameFragment
							+ ".getValue()).equals(\"\")?null:new " + realType
							+ "(txt" + nameFragment
							+ ".getValue().toString()));";
				}

				// String setToId = metaData.getPrimaryKey().getName() + ".set"
				// + nameFragment + "(new " + realType + "((String) txt"
				// + nameFragment + ".getValue()));";

				finalParam.add(setToId);
			}
		} else {
			String setToId = new String();

			setToId = metaData.getPrimaryKey().getRealClassName() + " "
					+ metaData.getPrimaryKey().getName() + " = new "
					+ metaData.getPrimaryKey().getRealClassName() + "(" + "txt"
					+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
					+ ".getValue().toString());";

			finalParam.add(setToId);
		}

		return finalParam;
	}

	public String finalParamForId(List<MetaData> list, MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				finalParam = finalParam + realType + " " + name + ", ";
			}

			String finalCharacter = ""
					+ finalParam.charAt(finalParam.length() - 2);

			finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
					finalParam.length() - 2) : finalParam;

			return finalParam;
		} else {
			finalParam = metaData.getPrimaryKey().getRealClassName() + " "
					+ metaData.getPrimaryKey().getName();
		}

		return finalParam;
	}

	public String finalParamForIdVariables(List<MetaData> list,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				finalParam = finalParam + name + ", ";
			}

			String finalCharacter = ""
					+ finalParam.charAt(finalParam.length() - 2);

			finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
					finalParam.length() - 2) : finalParam;

			return finalParam;
		} else {
			finalParam = metaData.getPrimaryKey().getName();
		}

		return finalParam;
	}

	public List<String> finalParamForIdForViewVariablesInList(
			List<MetaData> list, MetaData metaData) {

		if (metaData.getRealClassName().equalsIgnoreCase("DiaNoLaboral")) {
			String tmp = "";
			System.out.println(tmp);
		}

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();
		this.datesId = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String type = getRealClassName(field2.getType().getName());

				String tmp1 = (name.substring(0, 1)).toUpperCase()
						+ name.substring(1);

				if (type.equalsIgnoreCase("date")) {
					if (!this.dates.contains(tmp1))
						this.datesId.add(tmp1);
				} else {
					finalParam2.add(tmp1);
				}

			}
		} else {
			finalParam = metaData.getPrimaryKey().getName();
			String type = metaData.getPrimaryKey().getRealClassName();
			String tmp1 = (finalParam.substring(0, 1)).toUpperCase()
					+ finalParam.substring(1);
			if (type.equalsIgnoreCase("date")) {
				if (!this.dates.contains(tmp1))
					datesId.add(tmp1);
			} else {
				finalParam2.add(tmp1);
			}

		}

		return finalParam2;
	}

	public String[] getTypeAndvariableForManyToOneProperties(String strClass,
			List<MetaData> theMetaData) {
		String ret[] = new String[50];

		for (MetaData metaData : theMetaData) {
			if (metaData.getRealClassName().equalsIgnoreCase(strClass)) {

				manyToOneTempHash = metaData.getPrimaryKey()
						.getHashMapIdsProperties();

				if (!metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					Member member = metaData.getPrimaryKey();
					ret[0] = member.getRealClassName();
					ret[1] = member.getName() + "_"
							+ metaData.getRealClassName();
					// ret[2] = member.getGetNameOfPrimaryName();
					// ret[3] = member.getRealClassName();
				} else {
					int contTmp = 0;
					Field[] field = metaData.getComposeKey()
							.getDeclaredFields();
					for (int i = 0; i < field.length; i++) {

						Field field2 = field[i];

						String name = field2.getName();

						String realType = field2.getType().toString()
								.substring(
										(field2.getType().toString())
												.lastIndexOf(".") + 1,
										(field2.getType().toString()).length());

						ret[contTmp] = realType;
						contTmp++;
						ret[contTmp] = name + "_" + metaData.getRealClassName();
						contTmp++;
					}
				}
			}
		}

		boolean watch = false;

		for (int j = 0; j < ret.length; j++) {
			if (ret[j] != null) {
				if (!ret[j].equalsIgnoreCase(""))
					watch = true;
			}
		}

		if (watch) {
			return ret;
		} else {
			return null;
		}

	}

	public List<String> getVariableForManyToOneProperties(
			List<Member> manyToOne, List<MetaData> theMetaData) {
		List<String> finalParam = new ArrayList<String>();

		for (MetaData metaData1 : theMetaData) {
			for (Member member : manyToOne) {
				if (metaData1.getRealClassName().equalsIgnoreCase(
						member.getRealClassName())) {

					if (metaData1.getPrimaryKey().isPrimiaryKeyAComposeKey()) {

						Field[] field = metaData1.getComposeKey()
								.getDeclaredFields();

						for (int i = 0; i < field.length; i++) {

							Field field2 = field[i];

							String name = field2.getName() + "_"
									+ metaData1.getRealClassName();

							finalParam.add(name);

						}

					} else {
						finalParam.add(metaData1.getPrimaryKey().getName()
								+ "_" + metaData1.getRealClassName());
					}
				}
			}
		}

		return finalParam;
	}

	public List<String> getStringsForManyToOneProperties(
			List<Member> manyToOne, List<MetaData> theMetaData) {
		List<String> finalParam = new ArrayList<String>();

		int cont = 1;
		for (MetaData metaData1 : theMetaData) {
			for (Member member : manyToOne) {
				if (metaData1.getRealClassName().equalsIgnoreCase(
						member.getRealClassName())) {

					if (metaData1.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
						Field[] field = metaData1.getComposeKey()
								.getDeclaredFields();

						finalParam.add(metaData1.getPrimaryKey()
								.getRealClassName()
								+ " "
								+ metaData1.getPrimaryKey()
										.getRealClassVariableName()
								+ "Class = new "
								+ metaData1.getPrimaryKey().getRealClassName()
								+ "();");

						for (int i = 0; i < field.length; i++) {

							Field field2 = field[i];
							String name = field2.getName();

							// sitesId.setConame(coname);

							String build = name.substring(0, 1).toUpperCase();
							String build2 = name.substring(1, name.length());
							String build3 = build + build2;

							finalParam.add(metaData1.getPrimaryKey()
									.getRealClassVariableName()
									+ "Class.set"
									+ build3
									+ "("
									+ name
									+ "_"
									+ metaData1.getRealClassName() + ");");

						}

						finalParam
								.add(member.getRealClassName()
										+ " "
										+ member.getName()
										+ "Class = logic"
										+ member.getRealClassName()
										+ cont
										+ ".get"
										+ member.getRealClassName()
										+ "("
										+ metaData1.getPrimaryKey()
												.getRealClassVariableName()
										+ "Class);");
						cont++;

					} else {
						finalParam.add(member.getRealClassName() + " "
								+ member.getName() + "Class = logic"
								+ member.getRealClassName() + cont + ".get"
								+ member.getRealClassName() + "("
								+ metaData1.getPrimaryKey().getName() + "_"
								+ metaData1.getRealClassName() + ");");
						cont++;

					}

				}

			}
		}

		return finalParam;
	}

	public String getGetNameOfPrimaryName(String name) {
		String build = name.substring(0, 1).toUpperCase();
		String build2 = name.substring(1);
		return build + build2;
	}

	public boolean isComposedKey(Class type) {
		boolean ret = false;

		String firstLetters = type.getName();
		String[] tmp = (firstLetters.replace(".", "%")).split("%");

		if (tmp != null) {
			if ((tmp[0].equalsIgnoreCase("java") && tmp[1]
					.equalsIgnoreCase("lang"))
					|| (tmp[0].equalsIgnoreCase("java") && tmp[1]
							.equalsIgnoreCase("util"))) {
				ret = false;
			} else {
				ret = true;
			}
		}

		return ret;
	}

	public String getRealClassName(String type) {
		String typeComplete = type;
		String[] tmp = (typeComplete.replace(".", "%")).split("%");
		String realName = tmp[tmp.length - 1];
		return realName;
	}

	public boolean isFinalParamForViewDatesInList() {
		if (this.dates != null) {
			if (!this.dates.isEmpty() && this.dates.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isFinalParamForIdForViewDatesInList() {
		if (this.datesId != null) {
			if (!this.datesId.isEmpty() && this.datesId.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Object isFinalParamForIdClassAsVariablesForDates() {
		if (this.datesIdJSP != null) {
			if (!this.datesIdJSP.isEmpty() && this.datesIdJSP.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Object isFinalParamDatesAsList() {
		if (this.datesJSP != null) {
			if (!this.datesJSP.isEmpty() && this.datesJSP.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void neededIds(List<MetaData> list) {
		for (MetaData metaData : list) {
			if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {

				Field[] field = metaData.getComposeKey().getDeclaredFields();

				for (int i = 0; i < field.length; i++) {
					Field field2 = field[i];
					String name = field2.getName();

					hashMapIds.put(name + "_" + metaData.getRealClassName(),
							metaData.getPrimaryKey().getName());
				}
			}
		}
		HashMap<String, String> map = hashMapIds;
	}

	public void biuldHashToGetIdValuesLengths(List<MetaData> list) {

		for (MetaData metaData : list) {

			HashMap<String, Member> hashMapIdsProperties = new HashMap<String, Member>();

			if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {

				Class clazz = metaData.getComposeKey();
				Field[] field = metaData.getComposeKey().getDeclaredFields();

				for (Field field2 : field) {
					String realClassName = field2.getName();

					// length;
					// precision;
					// scale;
					// nullable;

					buildStringToCheckLengths(field2, clazz, realClassName);

					Member member = new SimpleMember(field2.getName(), field2
							.getName(), field2.getType(), -1);
					member.setLength(length);
					member.setPrecision(precision);
					member.setScale(scale);
					member.setNullable(nullable);

					hashMapIdsProperties.put(field2.getName(), member);

					metaData.getPrimaryKey().setHashMapIdsProperties(
							hashMapIdsProperties);
				}
			} else {

				Member member = new SimpleMember(metaData.getPrimaryKey()
						.getName(), metaData.getPrimaryKey().getName(),
						metaData.getPrimaryKey().getType(), -1);

				member.setLength(metaData.getPrimaryKey().getLength());
				member.setPrecision(metaData.getPrimaryKey().getPrecision());
				member.setScale(metaData.getPrimaryKey().getScale());
				member.setNullable(metaData.getPrimaryKey().getNullable());

				hashMapIdsProperties.put(metaData.getPrimaryKey().getName(),
						member);

				metaData.getPrimaryKey().setHashMapIdsProperties(
						hashMapIdsProperties);

			}
		}
	}

	public List<String> addVariablesValuesToListDependingOnDataType(
			List<String> finalParam2, String realClassName,
			String variableName, String precision, String scale, String length) {

		if (realClassName.equalsIgnoreCase("String")) {
			if (!length.equals("0"))
				finalParam2.add(ifcondition + variableName + "!=null && "
						+ "Utilities.checkWordAndCheckWithlength("
						+ variableName + "," + length + ")==false"
						+ ifconditionClose + throwExceptionLength + "\""
						+ variableName + "\"" + throwExceptionClose);

		}

		if (realClassName.equalsIgnoreCase("Integer")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + 0
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Double")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + scale
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Long")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + 0
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Float")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + scale
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		return finalParam2;
	}

	public List<String> addVariablesValuesToListDependingOnDataTypeForID(
			List<String> finalParam2, Field field, String variableName,
			Class clazz) {

		String realClassName = field.getType().getSimpleName();

		String variableNameFormethod = variableName.substring(0, 1)
				.toUpperCase()
				+ variableName.substring(1);

		buildStringToCheckLengths(field, clazz, variableName);

		if (realClassName.equalsIgnoreCase("String")) {
			if (!length.equals("0"))
				finalParam2.add(ifcondition + variableName + "!=null && "
						+ "Utilities.checkWordAndCheckWithlength("
						+ variableName + "," + length + ")==false"
						+ ifconditionClose + throwExceptionLength + "\""
						+ variableName + "\"" + throwExceptionClose);

		}

		if (realClassName.equalsIgnoreCase("Integer")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + 0
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Double")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + scale
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Long")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + 0
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Float")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + scale
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		return finalParam2;
	}

	public void buildStringToCheckLengths(Field field, Class clazz,
			String realClassName) {

		if (field.getAnnotations() != null && field.getAnnotations().length > 0) {
			if (field.getAnnotation(Column.class) != null) {
				Column column = field.getAnnotation(Column.class);
				length = new Long(column.length());
				precision = new Long(column.precision());
				scale = new Long(column.scale());
				nullable = new Boolean(column.nullable());

			} else {
				if (field.getAnnotation(Basic.class) != null) {
					nullable = new Boolean(field.getAnnotation(Basic.class)
							.optional());
				}
			}
		} else {
			for (Method method : clazz.getDeclaredMethods()) {

				if (method.getAnnotation(Column.class) != null) {
					String property = new String();

					if (method.getName().startsWith("get")) {
						property = method.getName().substring(3);

						if (realClassName.equalsIgnoreCase(property)) {
							Column column = method.getAnnotation(Column.class);
							length = new Long(column.length());
							precision = new Long(column.precision());
							scale = new Long(column.scale());
							nullable = new Boolean(column.nullable());

							break;
						}
					} else {
						if (method.getName().startsWith("is")) {
							property = method.getName().substring(3);

							if (realClassName.equalsIgnoreCase(property)) {
								Column column = method
										.getAnnotation(Column.class);
								length = new Long(column.length());
								precision = new Long(column.precision());
								scale = new Long(column.scale());
								nullable = new Boolean(column.nullable());

								break;
							}
						}
					}
				} else {
					if (method.getAnnotation(Basic.class) != null) {
						nullable = new Boolean(method
								.getAnnotation(Basic.class).optional());
					}
				}
			}
		}

	}

	public void buildFolders(String packageName, String hardDiskLocation,
			Integer specificityLevel, String packageOriginal) {

		// / se construye paquete
		String pckge = packageName.replace('.', '_') + "_";
		String modelPckg = packageOriginal.replace('.', '_') + "_";

		String dataAcces = pckge + "dataaccess_";
		String model = modelPckg;
		String presentation = pckge + "presentation_";
		String dao = dataAcces + "dao_";

		List<String> folderBuilder = new ArrayList<String>();

		folderBuilder.add(pckge);

		folderBuilder.add(pckge + "exceptions");

		folderBuilder.add(pckge + "utilities");

		folderBuilder.add(dao);

		folderBuilder.add(dataAcces + "daoFactory");

		folderBuilder.add(dataAcces + "entityManager");

		folderBuilder.add(model + "control");

//		if (specificityLevel.intValue() == 2) {
//			folderBuilder.add(model + "pojos");
//		}

		folderBuilder.add(model + "dto");

		folderBuilder.add(presentation + "backEndBeans");

		folderBuilder.add(presentation + "businessDelegate");

		folderBuilder.add(properties.getProperty("webRootFolderPath"));

		for (String string : folderBuilder) {
			try {
				GeneratorUtil.validateDirectory(string, hardDiskLocation);
			} catch (IOException e) {
				// TODO Poner log4j por si lanza error
				e.printStackTrace();
			}
		}

		try {
			GeneratorUtil.validateDirectory("JSPX", properties
					.getProperty("webRootFolderPath"));
			GeneratorUtil.validateDirectory("WEB-INF", properties
					.getProperty("webRootFolderPath"));
			GeneratorUtil.validateDirectory("facelets", properties
					.getProperty("webRootFolderPath")
					+ GeneratorUtil.slash + "WEB-INF");
			// WEB-INF
			GeneratorUtil.validateDirectory("META-INF", hardDiskLocation);
		} catch (IOException e) {
			// TODO Poner log4j por si lanza error
			e.printStackTrace();
		}

	}

	// if (metaData.getRealClassName().equalsIgnoreCase("TipoAlarma")) {
	// String tmp = "";
	// System.out.println(tmp);
	// }
	// if (metaData.getRealClassName().equalsIgnoreCase("comuna")) {
	// String tmp = "";
	// System.out.println(tmp);
	// }
	// if (metaData.getRealClassName().equalsIgnoreCase("DetalleCantiMuestra"))
	// {
	// String tmp = "";
	// System.out.println(tmp);
	// }

}