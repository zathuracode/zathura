package co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.utils.IStringBuilder;
import co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.utils.IStringBuilderForId;
import co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.utils.StringBuilderForId;
import co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.utils.Utilities;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.generator.utilities.JalopyCodeFormatter;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.utils.StringBuilder;

/**
 * 
 * @author Ricardo Alberto Chiriboga, Juan Manuel Caicedo
 * 
 */
public class ZathuraJavaEE_Gwt_Centric implements IZathuraGenerator,
		IZathuraTemplate {

	private static Logger log = Logger
			.getLogger(ZathuraJavaEE_Gwt_Centric.class);

	private final static String gwtCentric = GeneratorUtil
			.getGwtCentricTemplates();

	public String virginPackageInHd = new String();
	private Properties properties;

	public void toGenerate(MetaDataModel metaDataModel, String projectName,
			String folderProjectPath, Properties propiedades) {
		log.info("Begin ZathuraJavaEE_GWT_Centric generation");

		String jpaPckgName = propiedades.getProperty("jpaPckgName");
		Integer specificityLevel = (Integer) propiedades.get("specificityLevel");
		properties = propiedades;
		log.info("doTemplate ZathuraJavaEE_GWT_Centric generation");
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

//		String generatorExtZathuraJavaEEGwtCentricIndexJsp = GeneratorUtil
//				.getGeneratorExtZathuraJavaEEGwtCentric()
//				+ GeneratorUtil.slash + "index.jsp";
		String generatorExtZathuraJavaEEGwtCentricImages = GeneratorUtil
				.getGeneratorExtZathuraJavaEEGwtCentric()
				+ GeneratorUtil.slash + "images" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEGwtCentricCSS = GeneratorUtil
				.getGeneratorExtZathuraJavaEEGwtCentric()
				+ GeneratorUtil.slash + "css" + GeneratorUtil.slash;
//		String generatorExtZathuraJavaEEGwtCentricXmlhttp = GeneratorUtil
//				.getGeneratorExtZathuraJavaEEGwtCentric()
//				+ GeneratorUtil.slash + "xmlhttp" + GeneratorUtil.slash;
//		String generatorExtZathuraJavaEEGwtCentricWEBXML = GeneratorUtil
//				.getGeneratorExtZathuraJavaEEGwtCentric()
//				+ GeneratorUtil.slash + "WEB-INF" + GeneratorUtil.slash;
		String generatorExtZathuraJavaEEGwtCentricLOG4J = GeneratorUtil
				.getGeneratorExtZathuraJavaEEGwtCentric()
				+ GeneratorUtil.slash + "log4j" + GeneratorUtil.slash;
//		String generatorLibrariesZathuraJavaEEGwtCentricIceFaces = GeneratorUtil
//				.getGeneratorLibrariesZathuraJavaEEGwtCentric()
//				+ GeneratorUtil.slash + "iceFaces1.8.1" + GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEGwtCentricJpaHibernate = GeneratorUtil
				.getGeneratorLibrariesZathuraJavaEEGwtCentric()
				+ GeneratorUtil.slash
				+ "jpa-hibernate3.2"
				+ GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEGwtCentricGWT2 = GeneratorUtil.getGeneratorLibrariesZathuraJavaEEGwtCentric()
		+ GeneratorUtil.slash
		+ "gwt 2.0.4"
		+ GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEGwtCentricSmartGwt = GeneratorUtil
		.getGeneratorLibrariesZathuraJavaEEGwtCentric()
		+ GeneratorUtil.slash
		+ "smartgwt"
		+ GeneratorUtil.slash;

		log.info("Copy Libraries files ZathuraJavaEE_Web_Centric generation");

		// Copy Libraries
		String libFolderPath = properties.getProperty("libFolderPath");
//		GeneratorUtil.copyFolder(
//				generatorLibrariesZathuraJavaEEGwtCentricIceFaces,
//				libFolderPath);
		GeneratorUtil.copyFolder(
				generatorLibrariesZathuraJavaEEGwtCentricJpaHibernate,
				libFolderPath);
		GeneratorUtil.copyFolder(generatorLibrariesZathuraJavaEEGwtCentricGWT2,libFolderPath);
		GeneratorUtil.copyFolder(generatorLibrariesZathuraJavaEEGwtCentricSmartGwt,libFolderPath);
				

		// Copy Ext web.xml
		String webRootFolderPath = properties.getProperty("webRootFolderPath");
//		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEGwtCentricWEBXML,
//				webRootFolderPath + "WEB-INF" + GeneratorUtil.slash);

		// Copy Ext css
		GeneratorUtil.createFolder(webRootFolderPath + "images");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEGwtCentricImages,
				webRootFolderPath + "images" + GeneratorUtil.slash);

		// Copy Ext css
		GeneratorUtil.createFolder(webRootFolderPath + "css");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEGwtCentricCSS,
				webRootFolderPath + "css" + GeneratorUtil.slash);

		// Copy Ext xmlhttp
//		GeneratorUtil.createFolder(webRootFolderPath + "xmlhttp");
//		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEGwtCentricXmlhttp,
//				webRootFolderPath + "xmlhttp" + GeneratorUtil.slash);

		// Copy Ext index.jsp
//		GeneratorUtil.copy(generatorExtZathuraJavaEEGwtCentricIndexJsp,
//				webRootFolderPath + GeneratorUtil.slash + "index.jsp");

		// Copy Ext log4j
		String folderProjectPath = properties.getProperty("folderProjectPath");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEGwtCentricLOG4J,
				folderProjectPath + GeneratorUtil.slash);

	}

	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel) {
		try {
			Properties properties = new Properties();

			properties.setProperty("file.resource.loader.description","Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			properties.setProperty("file.resource.loader.path", gwtCentric);
			properties.setProperty("file.resource.loader.cache", "false");
			properties.setProperty("file.resource.loader.modificationCheckInterval", "2");

			Velocity.init(properties);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		VelocityContext context = new VelocityContext();

		MetaDataModel dataModel = metaDataModel;
		List<MetaData> list = dataModel.getTheMetaData();

		IStringBuilderForId stringBuilderForId = new StringBuilderForId(list);
		IStringBuilder stringBuilder = new StringBuilder(list,(StringBuilderForId) stringBuilderForId);

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
		context.put("projectNameLower", projectName.toLowerCase());
		context.put("modelName", modelName);
		context.put("projectNameClass", projectNameClass);

		this.virginPackageInHd = GeneratorUtil.replaceAll(virginPackage, ".",
				GeneratorUtil.slash);

		Utilities.getInstance().buildFoldersGWT(virginPackage, hdLocation,
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

			doDao(metaData, context, hdLocation);

//			doBackEndBeans(metaData, context, hdLocation, dataModel);
//			doJsp(metaData, context, hdLocation, dataModel);
			doLogic(metaData, context, hdLocation, dataModel, modelName);
//			doDto(metaData, context, hdLocation, dataModel, modelName);
			doExceptions(context, hdLocation);
		}

		doUtilites(context, hdLocation, dataModel, modelName);
		doBusinessDelegator(context, hdLocation, dataModel);
		doDaoFactory(dataModel, context, hdLocation);
		doPersitenceXml(dataModel, context, hdLocation);
		doEntityManager(dataModel, context, hdLocation);
		doWebXML(dataModel, context, hdLocation);
//		doJspInitialMenu(dataModel, context, hdLocation);
//		doFacesConfig(dataModel, context, hdLocation);
//		doJspFacelets(context, hdLocation);
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

			String realLocation = hdLocation 
//				+ GeneratorUtil.slash
					+ virginPackageInHd 
					+ GeneratorUtil.slash +"server"+ GeneratorUtil.slash + "control" + GeneratorUtil.slash;

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
//		Template utilitiesDataPage = null;
//		Template utilitiesDataSource = null;
//		Template utilitiesPagedListDataModel = null;

		StringWriter swUtilities = new StringWriter();
//		StringWriter swUtilitiesDataPage = new StringWriter();
//		StringWriter swUtilitiesDataSource = new StringWriter();
//		StringWriter swUtilitiesPagedListDataModel = new StringWriter();

		try {
			utilities = Velocity.getTemplate("Utilities.vm");
//			utilitiesDataPage = Velocity.getTemplate("DataPage.vm");
//			utilitiesDataSource = Velocity.getTemplate("DataSource.vm");
//			utilitiesPagedListDataModel = Velocity
//					.getTemplate("PagedListDataModel.vm");
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
//			utilitiesDataPage.merge(context, swUtilitiesDataPage);
//			utilitiesDataSource.merge(context, swUtilitiesDataSource);
//			utilitiesPagedListDataModel.merge(context,
//					swUtilitiesPagedListDataModel);

			String realLocation = hdLocation + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash  +"server"+ GeneratorUtil.slash + "utilities"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "Utilities.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swUtilities.toString());
			// Close the output stream
			out.close();

//			FileWriter fstream2 = new FileWriter(realLocation + "DataPage.java");
//			BufferedWriter out2 = new BufferedWriter(fstream2);
//			out2.write(swUtilitiesDataPage.toString());
//			// Close the output stream
//			out2.close();

//			FileWriter fstream3 = new FileWriter(realLocation
//					+ "DataSource.java");
//			BufferedWriter out3 = new BufferedWriter(fstream3);
//			out3.write(swUtilitiesDataSource.toString());
//			// Close the output stream
//			out3.close();

//			FileWriter fstream4 = new FileWriter(realLocation
//					+ "PagedListDataModel.java");
//			BufferedWriter out4 = new BufferedWriter(fstream4);
//			out4.write(swUtilitiesPagedListDataModel.toString());
//			// Close the output stream
//			out4.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "Utilities.java");
//			JalopyCodeFormatter.formatJavaCodeFile(realLocation
//					+ "DataPage.java");
//			JalopyCodeFormatter.formatJavaCodeFile(realLocation
//					+ "DataSource.java");
//			JalopyCodeFormatter.formatJavaCodeFile(realLocation
//					+ "PagedListDataModel.java");

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
					+ virginPackageInHd + GeneratorUtil.slash  +"server"+  GeneratorUtil.slash + "exceptions"
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
					+ virginPackageInHd + GeneratorUtil.slash  +"server" 
//					+ GeneratorUtil.slash + "presentation"
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
					+ virginPackageInHd + GeneratorUtil.slash  +"server" + GeneratorUtil.slash + "dataaccess"
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
					+ virginPackageInHd+ GeneratorUtil.slash  +"server" + GeneratorUtil.slash + "dataaccess"
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
					+ virginPackageInHd + GeneratorUtil.slash  +"server" + GeneratorUtil.slash + "dataaccess"
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


	@Override
	public void doWebXML(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {
		log.info("Begin doWebXML");

		Template webXml = null;

		StringWriter swWebXML = new StringWriter();

		try {
			webXml = Velocity.getTemplate("WebXML.vm");
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
			webXml.merge(context, swWebXML);

			
			String realLocation = properties.getProperty("webRootFolderPath")
			+"WEB-INF"
			+ GeneratorUtil.slash;
			FileWriter fstream = new FileWriter(
					realLocation + "web.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swWebXML.toString());
			// Close the output stream
			out.close();
			JalopyCodeFormatter.formatJavaCodeFile(realLocation
					+ "web.xml");

			log.info("End doWebXML");

		} catch (Exception e) {
			log.info("Error doWebXML");
		}

		
	}

}