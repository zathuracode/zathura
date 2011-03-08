package co.edu.usbcali.lidis.zathura.generator.jee.jpa.gwtcentric.engine;

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

import co.edu.usbcali.lidis.zathura.generator.jee.jpa.gwtcentric.utils.IStringBuilder;
import co.edu.usbcali.lidis.zathura.generator.jee.jpa.gwtcentric.utils.IStringBuilderForId;
import co.edu.usbcali.lidis.zathura.generator.jee.jpa.gwtcentric.utils.StringBuilder;
import co.edu.usbcali.lidis.zathura.generator.jee.jpa.gwtcentric.utils.StringBuilderForId;
import co.edu.usbcali.lidis.zathura.generator.jee.jpa.gwtcentric.utils.Utilities;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.generator.utilities.JalopyCodeFormatter;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;

/**
 * 
 * @author Ricardo Alberto Chiriboga, Juan Manuel Caicedo
 * 
 */
public class ZathuraJavaEE_Gwt_Centric implements IZathuraGenerator, IZathuraTemplate {

	private static Logger log = Logger.getLogger(ZathuraJavaEE_Gwt_Centric.class);

	private final static String gwtCentric = GeneratorUtil.getGwtCentricTemplates();

	public String virginPackageInHd = new String();
	private Properties properties;
	private VelocityEngine ve;

	public void toGenerate(MetaDataModel metaDataModel, String projectName, String folderProjectPath, Properties propiedades) {
		log.info("Begin ZathuraJavaEE_GWT_Centric generation");

		String jpaPckgName = propiedades.getProperty("jpaPckgName");
		Integer specificityLevel = (Integer) propiedades.get("specificityLevel");
		properties = propiedades;
		log.info("doTemplate ZathuraJavaEE_GWT_Centric generation");
		doTemplate(folderProjectPath, metaDataModel, jpaPckgName, projectName, specificityLevel);
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

		String generatorExtZathuraJavaEEGwtCentricImages = GeneratorUtil.getGeneratorExtZathuraJavaEEGwtCentric() + GeneratorUtil.slash + "images"
				+ GeneratorUtil.slash;
		String generatorExtZathuraJavaEEGwtCentricCSS = GeneratorUtil.getGeneratorExtZathuraJavaEEGwtCentric() + GeneratorUtil.slash + "css"
				+ GeneratorUtil.slash;
		String generatorExtZathuraJavaEEGwtCentricLOG4J = GeneratorUtil.getGeneratorExtZathuraJavaEEGwtCentric() + GeneratorUtil.slash + "log4j"
				+ GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEGwtCentricJpaHibernate = GeneratorUtil.getGeneratorLibrariesZathuraJavaEEGwtCentric() + GeneratorUtil.slash
				+ "jpa-hibernate3.2" + GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEGwtCentricGWT2 = GeneratorUtil.getGeneratorLibrariesZathuraJavaEEGwtCentric() + GeneratorUtil.slash + "gwt 2.0.4"
				+ GeneratorUtil.slash;
		String generatorLibrariesZathuraJavaEEGwtCentricSmartGwt = GeneratorUtil.getGeneratorLibrariesZathuraJavaEEGwtCentric() + GeneratorUtil.slash
				+ "smartgwt" + GeneratorUtil.slash;
		// String generatorLibrariesZathuraJavaEEGwtCentricGwtWidgets =
		// GeneratorUtil
		// .getGeneratorLibrariesZathuraJavaEEGwtCentric()
		// + GeneratorUtil.slash
		// + "gwt-widgets"
		// + GeneratorUtil.slash;

		log.info("Copy Libraries files ZathuraJavaEE_Web_Centric generation");

		// Copy Libraries
		String libFolderPath = properties.getProperty("libFolderPath");
		GeneratorUtil.copyFolder(generatorLibrariesZathuraJavaEEGwtCentricJpaHibernate, libFolderPath);
		GeneratorUtil.copyFolder(generatorLibrariesZathuraJavaEEGwtCentricGWT2, libFolderPath);
		GeneratorUtil.copyFolder(generatorLibrariesZathuraJavaEEGwtCentricSmartGwt, libFolderPath);
		// GeneratorUtil.copyFolder(generatorLibrariesZathuraJavaEEGwtCentricGwtWidgets,libFolderPath);

		// Copy Ext css
		String webRootFolderPath = properties.getProperty("webRootFolderPath");
		GeneratorUtil.createFolder(webRootFolderPath + "images");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEGwtCentricImages, webRootFolderPath + "images" + GeneratorUtil.slash);

		// Copy Ext css
		GeneratorUtil.createFolder(webRootFolderPath + "css");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEGwtCentricCSS, webRootFolderPath + "css" + GeneratorUtil.slash);

		// Copy Ext log4j
		String folderProjectPath = properties.getProperty("folderProjectPath");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEGwtCentricLOG4J, folderProjectPath + GeneratorUtil.slash);

	}

	public void doTemplate(String hdLocation, MetaDataModel metaDataModel, String jpaPckgName, String projectName, Integer specificityLevel) {
		try {
			ve = new VelocityEngine();

			Properties properties = new Properties();
			properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			properties.setProperty("file.resource.loader.path", gwtCentric);
			properties.setProperty("file.resource.loader.cache", "false");
			properties.setProperty("file.resource.loader.modificationCheckInterval", "2");

			log.info("Templates:" + gwtCentric);

			ve.init(properties);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

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

		String projectNameClass = (projectName.substring(0, 1)).toUpperCase() + projectName.substring(1, projectName.length());

		context.put("packageOriginal", packageOriginal);
		context.put("virginPackage", virginPackage);
		context.put("package", jpaPckgName);
		context.put("projectName", projectName);
		context.put("projectNameLower", projectName.toLowerCase());
		context.put("modelName", modelName);
		context.put("projectNameClass", projectNameClass);

		this.virginPackageInHd = GeneratorUtil.replaceAll(virginPackage, ".", GeneratorUtil.slash);

		Utilities.getInstance().buildFoldersGWT(virginPackage, hdLocation, specificityLevel, packageOriginal, properties);

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

			context.put("finalParamForIdForDtoForSetsVariablesInListGWT", stringBuilderForId.finalParamForIdForDtoForSetsVariablesInListGWT(list, metaData));

			context.put("finalParamForDtoForSetsVariablesInList", stringBuilder.finalParamForDtoForSetsVariablesInList(list, metaData));

			context.put("finalParamForDtoForSetsVariablesInListGWT", stringBuilder.finalParamForDtoForSetsVariablesInListGWT(list, metaData));

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

			context.put("finalMemberForId", Utilities.getInstance().finalMemberForId(list, metaData));
			context.put("finalMembers", Utilities.getInstance().getFinalParamMembers(list, metaData));
			context.put("finalMembersWithoutId", Utilities.getInstance().finalMemberWithoutId(list, metaData));

			if (metaData.isGetManyToOneProperties()) {
				context.put("getVariableForManyToOneProperties", stringBuilder.getVariableForManyToOneProperties(metaData.getManyToOneProperties(), list));

				context.put("getStringsToEntityForManyToOneProperties", stringBuilder.getStringsToEntityForManyToOneProperties(metaData
						.getManyToOneProperties(), list));

				context.put("getStringsForManyToOneProperties", stringBuilder.getStringsForManyToOneProperties(metaData.getManyToOneProperties(), list));
			}

			context.put("composedKey", false);

			if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
				context.put("composedKey", true);
				context.put("finalParamForIdClass", stringBuilderForId.finalParamForIdClass(list, metaData));
			}

			context.put("metaData", metaData);

			context.put("dataModel", dataModel);

			doDao(metaData, context, hdLocation);
			doLogic(metaData, context, hdLocation, dataModel, modelName);
			doExceptions(context, hdLocation);
			doGwtXml(metaData, context, hdLocation, dataModel, projectName);
			doDto(metaData, context, hdLocation, dataModel);
			doDataService(metaData, context, hdLocation, dataModel, modelName);
			doDataServiceAsync(metaData, context, hdLocation, dataModel, modelName);
			doEntryPoint(metaData, context, hdLocation, dataModel, projectName);

			doSmartGWTDataSource(metaData, context, hdLocation, dataModel, modelName);
			doDataServiceImpl(metaData, context, hdLocation, dataModel);
		}
		doGeneralEntryPoint(context, hdLocation, dataModel, projectName);
		doUtilites(context, hdLocation, dataModel, modelName);
		doBusinessDelegator(context, hdLocation, dataModel);
		doDaoFactory(dataModel, context, hdLocation);
		doPersitenceXml(dataModel, context, hdLocation);
		doEntityManager(dataModel, context, hdLocation);
		doWebXML(dataModel, context, hdLocation);
		doAbstractDataSource(context, hdLocation, dataModel, modelName);
		doHTML(context, hdLocation, dataModel, projectName);

	}

	public void doLogic(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doLogic");

		Template ilogic = null;
		Template logic = null;

		StringWriter swIlogic = new StringWriter();
		StringWriter swLogic = new StringWriter();

		try {
			ilogic = ve.getTemplate("ILogic.vm");
			logic = ve.getTemplate("Logic.vm");
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
			// + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "server" + GeneratorUtil.slash + "control" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "I" + metaData.getRealClassName() + "Logic.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swIlogic.toString());
			// Close the output stream
			out.close();

			FileWriter fstream1 = new FileWriter(realLocation + metaData.getRealClassName() + "Logic.java");
			BufferedWriter out1 = new BufferedWriter(fstream1);
			out1.write(swLogic.toString());
			// Close the output stream
			out1.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + metaData.getRealClassName() + "Logic.java");
			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "I" + metaData.getRealClassName() + "Logic.java");

			log.info("End doLogic");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doDataService(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doDataServices");

		Template dataService = null;

		StringWriter swDataService = new StringWriter();

		try {
			dataService = ve.getTemplate("DataService.vm");
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

			dataService.merge(context, swDataService);
			// System.out.println(swIdao);
			// System.out.println(swdao);

			String realLocation = hdLocation
			// + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "client" + GeneratorUtil.slash + "dataservice" + GeneratorUtil.slash;

			FileWriter fstream1 = new FileWriter(realLocation + "DataService" + metaData.getRealClassName() + ".java");
			BufferedWriter out1 = new BufferedWriter(fstream1);
			out1.write(swDataService.toString());
			// Close the output stream
			out1.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "DataService" + metaData.getRealClassName() + ".java");

			log.info("End doDataServices");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doDataServiceAsync(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doDataServicesAsync");

		Template dataServiceAsync = null;

		StringWriter swDataServiceAsync = new StringWriter();

		try {
			dataServiceAsync = ve.getTemplate("DataServiceAsync.vm");
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

			dataServiceAsync.merge(context, swDataServiceAsync);
			// System.out.println(swIdao);
			// System.out.println(swdao);

			String realLocation = hdLocation
			// + GeneratorUtil.slash
					+ virginPackageInHd + GeneratorUtil.slash + "client" + GeneratorUtil.slash + "dataservice" + GeneratorUtil.slash;

			FileWriter fstream1 = new FileWriter(realLocation + "DataService" + metaData.getRealClassName() + "Async.java");
			BufferedWriter out1 = new BufferedWriter(fstream1);
			out1.write(swDataServiceAsync.toString());
			// Close the output stream
			out1.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "DataService" + metaData.getRealClassName() + "Async.java");

			log.info("End doDataServicesAsync");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doDto(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel) {
		log.info("Begin doDTO");

		Template dto = null;

		StringWriter swDto = new StringWriter();

		try {
			dto = ve.getTemplate("DTO.vm");
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

			String realLocation = hdLocation + virginPackageInHd + GeneratorUtil.slash + GeneratorUtil.slash + "client" + GeneratorUtil.slash + "dto"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + metaData.getRealClassName() + "DTO.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swDto.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + metaData.getRealClassName() + "DTO.java");

			log.info("End doDTO");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public void doAbstractDataSource(VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doAbstractDataSource");

		Template abstractDataSoruce = null;

		StringWriter swAbstractDataSource = new StringWriter();

		try {
			abstractDataSoruce = ve.getTemplate("AbstractGWTRPCDataSource.vm");
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

			abstractDataSoruce.merge(context, swAbstractDataSource);

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "client" + GeneratorUtil.slash + "smartds"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "AbstractGWTRPCDataSource.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swAbstractDataSource.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "AbstractGWTRPCDataSource.java");

			log.info("End doAbstractDataSource");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doSmartGWTDataSource(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doSmartGWTDataSource");

		Template smartGWTDataSource = null;

		StringWriter swSmartGWTDataSource = new StringWriter();

		try {
			smartGWTDataSource = ve.getTemplate("SmartGWTDataSource.vm");
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

			smartGWTDataSource.merge(context, swSmartGWTDataSource);

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "client" + GeneratorUtil.slash + "smartds"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "SmartGWTDataSource" + metaData.getRealClassName() + ".java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swSmartGWTDataSource.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "SmartGWTDataSource" + metaData.getRealClassName() + ".java");

			log.info("End doSmartGWTDataSource");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doUtilites(VelocityContext context, String hdLocation, MetaDataModel dataModel, String modelName) {
		log.info("Begin doUtilites");

		Template utilities = null;
		// Template utilitiesDataPage = null;
		// Template utilitiesDataSource = null;
		// Template utilitiesPagedListDataModel = null;

		StringWriter swUtilities = new StringWriter();
		// StringWriter swUtilitiesDataPage = new StringWriter();
		// StringWriter swUtilitiesDataSource = new StringWriter();
		// StringWriter swUtilitiesPagedListDataModel = new StringWriter();

		try {
			utilities = ve.getTemplate("Utilities.vm");
			// utilitiesDataPage = Velocity.getTemplate("DataPage.vm");
			// utilitiesDataSource = Velocity.getTemplate("DataSource.vm");
			// utilitiesPagedListDataModel = Velocity
			// .getTemplate("PagedListDataModel.vm");
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
			// utilitiesDataPage.merge(context, swUtilitiesDataPage);
			// utilitiesDataSource.merge(context, swUtilitiesDataSource);
			// utilitiesPagedListDataModel.merge(context,
			// swUtilitiesPagedListDataModel);

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "server" + GeneratorUtil.slash + "utilities"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "Utilities.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swUtilities.toString());
			// Close the output stream
			out.close();

			// FileWriter fstream2 = new FileWriter(realLocation +
			// "DataPage.java");
			// BufferedWriter out2 = new BufferedWriter(fstream2);
			// out2.write(swUtilitiesDataPage.toString());
			// // Close the output stream
			// out2.close();

			// FileWriter fstream3 = new FileWriter(realLocation
			// + "DataSource.java");
			// BufferedWriter out3 = new BufferedWriter(fstream3);
			// out3.write(swUtilitiesDataSource.toString());
			// // Close the output stream
			// out3.close();

			// FileWriter fstream4 = new FileWriter(realLocation
			// + "PagedListDataModel.java");
			// BufferedWriter out4 = new BufferedWriter(fstream4);
			// out4.write(swUtilitiesPagedListDataModel.toString());
			// // Close the output stream
			// out4.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "Utilities.java");
			// JalopyCodeFormatter.formatJavaCodeFile(realLocation
			// + "DataPage.java");
			// JalopyCodeFormatter.formatJavaCodeFile(realLocation
			// + "DataSource.java");
			// JalopyCodeFormatter.formatJavaCodeFile(realLocation
			// + "PagedListDataModel.java");

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

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "server" + GeneratorUtil.slash
					+ GeneratorUtil.slash + "exceptions" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "ZMessManager.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swExceptions.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "ZMessManager.java");

			log.info("End doException");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doBusinessDelegator(VelocityContext context, String hdLocation, MetaDataModel dataModel) {

		log.info("Begin doBusinessDelegator");

		Template businessDelegator = null;

		StringWriter swBusinessDelegator = new StringWriter();

		try {
			businessDelegator = ve.getTemplate("BusinessDelegatorView.vm");
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

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "server"
			// + GeneratorUtil.slash + "presentation"
					+ GeneratorUtil.slash + "businessdelegate" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "BusinessDelegatorView" + ".java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swBusinessDelegator.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "BusinessDelegatorView" + ".java");

			log.info("End doBusinessDelegator");

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}

	}

	public void doDao(MetaData metaData, VelocityContext context, String hdLocation) {

		log.info("Begin doDao");

		Template idao = null;
		Template dao = null;

		StringWriter swIdao = new StringWriter();
		StringWriter swDao = new StringWriter();

		try {
			idao = ve.getTemplate("IDAO2.vm");
			dao = ve.getTemplate("DAO2.vm");
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

			String daoLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "server" + GeneratorUtil.slash + "dataaccess"
					+ GeneratorUtil.slash + "dao" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(daoLocation + "I" + metaData.getRealClassName() + "DAO.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swIdao.toString());
			// Close the output stream
			out.close();

			FileWriter fstream1 = new FileWriter(daoLocation + metaData.getRealClassName() + "DAO.java");
			BufferedWriter out1 = new BufferedWriter(fstream1);
			out1.write(swDao.toString());
			// Close the output stream
			out1.close();

			JalopyCodeFormatter.formatJavaCodeFile(daoLocation + "I" + metaData.getRealClassName() + "DAO.java");

			JalopyCodeFormatter.formatJavaCodeFile(daoLocation + metaData.getRealClassName() + "DAO.java");

			log.info("End doDao");

		} catch (Exception e) {
			log.info("Error doDao");
		}

	}

	public void doDaoFactory(MetaDataModel metaData, VelocityContext context, String hdLocation) {

		log.info("Begin doDaoFactory");

		Template daoFactory = null;
		StringWriter swDaoFactory = new StringWriter();

		try {
			daoFactory = ve.getTemplate("JPADaoFactory.vm");
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

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "server" + GeneratorUtil.slash + "dataaccess"
					+ GeneratorUtil.slash + "daofactory" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "JPADaoFactory.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swDaoFactory.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "JPADaoFactory.java");

			log.info("End doDaoFactory");

		} catch (Exception e) {
			log.info("Error doDaoFactory");
		}

	}

	public void doPersitenceXml(MetaDataModel dataModel, VelocityContext context, String hdLocation) {

		log.info("Begin doPersitenceXml");

		Template persistence = null;
		StringWriter swPersistence = new StringWriter();

		try {
			persistence = ve.getTemplate("persistence.xml.vm");
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

			FileWriter fstream = new FileWriter(hdLocation + "META-INF" + GeneratorUtil.slash + "persistence.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swPersistence.toString());
			// Close the output stream
			out.close();

			log.info("End doPersitenceXml");

		} catch (Exception e) {
			log.info("Error doPersitenceXml");
		}

	}

	public void doEntityManager(MetaDataModel dataModel, VelocityContext context, String hdLocation) {

		log.info("Begin doEntityManager");

		Template entityManager = null;
		StringWriter swEntityManager = new StringWriter();

		try {
			entityManager = ve.getTemplate("EntityManagerHelper.vm");
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

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "server" + GeneratorUtil.slash + "dataaccess"
					+ GeneratorUtil.slash + "entitymanager" + GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "EntityManagerHelper.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swEntityManager.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "EntityManagerHelper.java");

			log.info("End doEntityManager");

		} catch (Exception e) {
			log.info("Error doEntityManager");
		}

	}

	@Override
	public void doWebXML(MetaDataModel dataModel, VelocityContext context, String hdLocation) {
		log.info("Begin doWebXML");

		Template webXml = null;

		StringWriter swWebXML = new StringWriter();

		try {
			webXml = ve.getTemplate("WebXML.vm");
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

			String realLocation = properties.getProperty("webRootFolderPath") + "WEB-INF" + GeneratorUtil.slash;
			FileWriter fstream = new FileWriter(realLocation + "web.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swWebXML.toString());
			// Close the output stream
			out.close();
			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "web.xml");

			log.info("End doWebXML");

		} catch (Exception e) {
			log.info("Error doWebXML");
		}

	}

	@Override
	public void doGeneralEntryPoint(VelocityContext context, String hdLocation, MetaDataModel dataModel, String projectName) {
		log.info("Begin doGeneralEntryPoint");

		Template entryPoint = null;

		StringWriter swEntryPoint = new StringWriter();

		try {
			entryPoint = ve.getTemplate("GeneralEntryPoint.vm");
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
			entryPoint.merge(context, swEntryPoint);

			String entryLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "client" + GeneratorUtil.slash + "entrypoint"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(entryLocation + projectName + ".java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swEntryPoint.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(entryLocation + projectName + ".java");

			log.info("End doGeneralEntryPoint");

		} catch (Exception e) {
			log.info("Error doGeneralEntryPoint");
		}

	}

	@Override
	public void doEntryPoint(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String projectName) {
		log.info("Begin doEntryPoint");

		Template entryPoint = null;

		StringWriter swEntryPoint = new StringWriter();

		try {
			entryPoint = ve.getTemplate("EntryPoint.vm");
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
			entryPoint.merge(context, swEntryPoint);

			String entryLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "client" + GeneratorUtil.slash + "entrypoint"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(entryLocation + metaData.getRealClassName() + "EP.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swEntryPoint.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(entryLocation + metaData.getRealClassName() + "EP.java");

			log.info("End doEntryPoint");

		} catch (Exception e) {
			log.info("Error doEntryPoint");
		}
	}

	public void doGwtXml(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel, String projectName) {

		log.info("Begin doGwtXml");

		Template gwtXml = null;

		StringWriter swGwtXml = new StringWriter();

		try {
			gwtXml = ve.getTemplate("GWTXML.vm");
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
			gwtXml.merge(context, swGwtXml);

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash;
			FileWriter fstream = new FileWriter(realLocation + projectName + ".gwt.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swGwtXml.toString());
			// Close the output stream
			out.close();
			JalopyCodeFormatter.formatJavaCodeFile(realLocation + projectName + ".gwt.xml");

			log.info("End doGwtXml");

		} catch (Exception e) {
			log.info("Error doGwtXml");
		}
	}

	@Override
	public void doHTML(VelocityContext context, String hdLocation, MetaDataModel dataModel, String projectName) {

		log.info("Begin doHTML");

		Template html = null;

		StringWriter swHtml = new StringWriter();

		try {
			html = ve.getTemplate("HTML.vm");
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
			html.merge(context, swHtml);

			String realLocation = properties.getProperty("webRootFolderPath") + GeneratorUtil.slash;
			FileWriter fstream = new FileWriter(realLocation + projectName + ".html");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swHtml.toString());
			// Close the output stream
			out.close();
			JalopyCodeFormatter.formatJavaCodeFile(realLocation + projectName + ".html");

			log.info("End doHTML");

		} catch (Exception e) {
			log.info("Error doHTML");
		}
	}

	@Override
	public void doDataServiceImpl(MetaData metaData, VelocityContext context, String hdLocation, MetaDataModel dataModel) {
		log.info("Begin doDataServiceImpl");

		Template dataServiceImpl = null;

		StringWriter swDataServiceImpl = new StringWriter();

		try {
			dataServiceImpl = ve.getTemplate("DataServiceImpl.vm");
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
			dataServiceImpl.merge(context, swDataServiceImpl);

			String realLocation = hdLocation + GeneratorUtil.slash + virginPackageInHd + GeneratorUtil.slash + "server" + GeneratorUtil.slash + "dataservice"
					+ GeneratorUtil.slash;

			FileWriter fstream = new FileWriter(realLocation + "DataService" + metaData.getRealClassName() + "Impl.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swDataServiceImpl.toString());
			// Close the output stream
			out.close();

			JalopyCodeFormatter.formatJavaCodeFile(realLocation + "DataService" + metaData.getRealClassName() + "Impl.java");

			log.info("End doDataServiceImpl");

		} catch (Exception e) {
			log.info("Error doDataServiceImpl");
		}

	}

}