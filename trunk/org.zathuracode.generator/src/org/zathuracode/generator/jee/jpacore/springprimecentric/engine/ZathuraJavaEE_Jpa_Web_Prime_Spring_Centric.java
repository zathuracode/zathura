package org.zathuracode.generator.jee.jpacore.springprimecentric.engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.zathuracode.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import org.zathuracode.generator.jee.jpacore.springprimecentric.utils.IStringBuilder;
import org.zathuracode.generator.jee.jpacore.springprimecentric.utils.IStringBuilderForId;
import org.zathuracode.generator.jee.jpacore.springprimecentric.utils.StringBuilder;
import org.zathuracode.generator.jee.jpacore.springprimecentric.utils.StringBuilderForId;
import org.zathuracode.generator.jee.jpacore.springprimecentric.utils.Utilities;
import org.zathuracode.generator.model.IZathuraGenerator;
import org.zathuracode.generator.utilities.GeneratorUtil;
import org.zathuracode.generator.utilities.JalopyCodeFormatter;
import org.zathuracode.metadata.model.MetaData;
import org.zathuracode.metadata.model.MetaDataModel;


/**
 * @author Andrés Mauricio Cárdenas  
 * mauriciocardenasp@gmail.com	
 */
public class ZathuraJavaEE_Jpa_Web_Prime_Spring_Centric implements IZathuraTemplate,IZathuraGenerator {
	private static Logger log;
	private static String pathTemplates;
	private String paqueteVirgen;
	private VelocityEngine ve;
	private Properties properties;
	private String webRootPath;


	static{
		log= Logger.getLogger(ZathuraJavaEE_Jpa_Web_Prime_Spring_Centric.class);
		pathTemplates= GeneratorUtil.getSpringJpaPrime();

	}

	@Override
	public void toGenerate(MetaDataModel metaDataModel, String projectName,
			String folderProjectPath, Properties propiedades) {

		webRootPath=(propiedades.getProperty("webRootFolderPath"));					
		properties=propiedades;
		String nombrePaquete= propiedades.getProperty("jpaPckgName");
		Integer specificityLevel = (Integer) propiedades.get("specificityLevel");
		String  domainName= nombrePaquete.substring(0, nombrePaquete.indexOf("."));
		doTemplate(folderProjectPath, metaDataModel, nombrePaquete, projectName, specificityLevel, domainName);
		copyLibraries();

	}
	
	
	public void copyLibraries(){
		
		String pathIndexJsp = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringJpaCentric()+"index.jsp";
		String pathWebXml= GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringJpaCentric()+"WEB-INF"+GeneratorUtil.slash;
		String generatorExtZathuraJavaEEWebSpringPrimeJpaCentricImages = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringJpaCentric() + GeneratorUtil.slash + "images"
		+ GeneratorUtil.slash;
		String log4j = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringJpaCentric() + GeneratorUtil.slash + "log4j"
				+ GeneratorUtil.slash;
		
		String pathHibernate= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimeSpringJpa()+"core-hibernate3.3"+GeneratorUtil.slash;
		String pathPrimeFaces= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimeSpringJpa()+"primeFaces2.2.1"+GeneratorUtil.slash;
		String pathSpring= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimeSpringJpa()+"spring3.0"+GeneratorUtil.slash;
		String pathLib= properties.getProperty("libFolderPath");
		
		String pathCss = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringJpaCentric() + GeneratorUtil.slash + "css"
		+ GeneratorUtil.slash;
		// Copy Css
		GeneratorUtil.createFolder(webRootPath + "css");
		GeneratorUtil.copyFolder(pathCss, webRootPath + "css" + GeneratorUtil.slash);
		
		GeneratorUtil.copyFolder(pathWebXml,webRootPath+"WEB-INF"+GeneratorUtil.slash);
		//create folder images and insert .png
		GeneratorUtil.createFolder(webRootPath + "images");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebSpringPrimeJpaCentricImages, webRootPath + "images" + GeneratorUtil.slash);
		// create index.jsp
		GeneratorUtil.copy(pathIndexJsp,webRootPath+"index.jsp" );
		//copy libraries
		GeneratorUtil.copyFolder(pathHibernate, pathLib);
		GeneratorUtil.copyFolder(pathPrimeFaces, pathLib);
		GeneratorUtil.copyFolder(pathSpring, pathLib);
		//copy log4j
		String folderProjectPath = properties.getProperty("folderProjectPath");
		GeneratorUtil.copyFolder(log4j, folderProjectPath + GeneratorUtil.slash);

	}
	
	@Override
	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel,
			String domainName) {

		try {
			ve= new VelocityEngine();
			VelocityContext velocityContext= new VelocityContext();
			Properties propiedadesVelocity= new Properties();
			propiedadesVelocity.put("file.resource.loader.description", "Velocity File Resource Loader");
			propiedadesVelocity.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			propiedadesVelocity.put("file.resource.loader.path",pathTemplates);
			propiedadesVelocity.put("file.resource.loader.cache", "false");
			propiedadesVelocity.put("file.resource.loader.modificationCheckInterval", "2");
			ve.init(propiedadesVelocity);

			List<MetaData> listMetaData= metaDataModel.getTheMetaData();

			String packageOriginal = null;
			String virginPackage = null;
			String modelName = null;

			IStringBuilderForId stringBuilderForId = new StringBuilderForId(listMetaData);
			IStringBuilder stringBuilder = new StringBuilder(listMetaData, (StringBuilderForId) stringBuilderForId);

			if (specificityLevel.intValue() == 2) {
				try {
					int lastIndexOf = jpaPckgName.lastIndexOf(".");
					packageOriginal = jpaPckgName.substring(0, lastIndexOf);

					int lastIndexOf2 = packageOriginal.lastIndexOf(".") + 1;
					modelName = packageOriginal.substring(lastIndexOf2);

					int virginLastIndexOf = packageOriginal.lastIndexOf(".");
					virginPackage = packageOriginal.substring(0, virginLastIndexOf);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			} else {
				try {
					packageOriginal = jpaPckgName;

					int lastIndexOf2 = packageOriginal.lastIndexOf(".") + 1;
					modelName = jpaPckgName.substring(lastIndexOf2);

					int virginLastIndexOf = packageOriginal.lastIndexOf(".");
					virginPackage = packageOriginal.substring(0, virginLastIndexOf);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}

			/***
			 *  packageOriginal es el nombre del paquete donde se encuentra las entitys com.mauricio.demogenerator.model
			 *  package  es el path donde se generan las entitys /home/mauricio/Workspaces/zathura/DemoGenerator/srs/com/mauricio/demogenerator/model
			 *  proyectName obvio
			 *  domainName el nombre del dominio para este ejemplo seria com
			 *  modelName el este caso seria model
			 */

			velocityContext.put("packageOriginal", packageOriginal);
			velocityContext.put("virginPackage", virginPackage);
			velocityContext.put("package", jpaPckgName);
			velocityContext.put("projectName", projectName);
			velocityContext.put("domainName", domainName);
			velocityContext.put("modelName", modelName);
			//Variables para generar el persistence.xml
			velocityContext.put("connectionUrl", EclipseGeneratorUtil.connectionUrl);
			velocityContext.put("connectionDriverClass", EclipseGeneratorUtil.connectionDriverClass);
			velocityContext.put("connectionUsername", EclipseGeneratorUtil.connectionUsername);
			velocityContext.put("connectionPassword", EclipseGeneratorUtil.connectionPassword);

			this.paqueteVirgen = GeneratorUtil.replaceAll(virginPackage, ".", GeneratorUtil.slash);
			Utilities.getInstance().buildFolders(virginPackage, hdLocation, specificityLevel, packageOriginal, properties);
			Utilities.getInstance().biuldHashToGetIdValuesLengths(listMetaData);

			for (MetaData metaData : listMetaData) {

				log.info(metaData.getRealClassName());
				log.info(Utilities.getInstance().isFinalParamForViewDatesInList());
				log.info("prueba");


				String var= metaData.getPrimaryKey().getName();
				velocityContext.put("var", var);
				log.info(metaData.getPrimaryKey().getName());


				velocityContext.put("composedKey", false);

				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					velocityContext.put("composedKey", true);
					velocityContext.put("finalParamForIdClass", stringBuilderForId.finalParamForIdClass(listMetaData, metaData));
				}

				if (metaData.isGetManyToOneProperties()) {
					velocityContext.put("getVariableForManyToOneProperties", stringBuilder.getVariableForManyToOneProperties(metaData.getManyToOneProperties(), listMetaData));
					velocityContext.put("getStringsForManyToOneProperties", stringBuilder.getStringsForManyToOneProperties(metaData.getManyToOneProperties(), listMetaData));
				}

				// generacion de nuevos dto
				velocityContext.put("variableDto", stringBuilder.getPropertiesDto(listMetaData, metaData));
				velocityContext.put("propertiesDto",Utilities.getInstance().dtoProperties);
				velocityContext.put("memberDto",Utilities.getInstance().nameMemberToDto);
				// generacion de la nueva logica 
				velocityContext.put("dtoConvert", stringBuilderForId.dtoConvert(listMetaData,metaData));
				velocityContext.put("dtoConvert2", stringBuilder.dtoConvert2(listMetaData, metaData));

				velocityContext.put("finalParamForIdVariablesAsList", stringBuilderForId.finalParamForIdVariablesAsList(listMetaData, metaData));
				velocityContext.put("finalParamForIdClassAsVariables", stringBuilderForId.finalParamForIdClassAsVariables(listMetaData, metaData));

				velocityContext.put("finalParamForViewVariablesInList", stringBuilder.finalParamForViewVariablesInList(listMetaData, metaData));
				velocityContext.put("isFinalParamForViewDatesInList", Utilities.getInstance().isFinalParamForViewDatesInList());
				velocityContext.put("isFinalParamForIdForViewDatesInList", Utilities.getInstance().isFinalParamForIdForViewDatesInList());

				velocityContext.put("finalParamForDtoUpdate", stringBuilder.finalParamForDtoUpdate(listMetaData, metaData));
				velocityContext.put("finalParamForIdForDtoForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoForSetsVariablesInList(listMetaData, metaData));
				velocityContext.put("finalParamForDtoForSetsVariablesInList", stringBuilder.finalParamForDtoForSetsVariablesInList(listMetaData, metaData));
				velocityContext.put("finalParamForIdForDtoForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoForSetsVariablesInList(listMetaData, metaData));
				velocityContext.put("finalParamForIdForViewVariablesInList", stringBuilderForId.finalParamForIdForViewVariablesInList(listMetaData, metaData));
				velocityContext.put("isFinalParamForIdForViewDatesInList", Utilities.getInstance().isFinalParamForIdForViewDatesInList());
				velocityContext.put("finalParamForIdForViewClass", stringBuilderForId.finalParamForIdForViewClass(listMetaData, metaData));
				velocityContext.put("finalParamForViewForSetsVariablesInList", stringBuilder.finalParamForViewForSetsVariablesInList(listMetaData, metaData));
				velocityContext.put("finalParamForView", stringBuilder.finalParamForView(listMetaData, metaData));
				velocityContext.put("finalParamForIdClassAsVariablesAsString", stringBuilderForId.finalParamForIdClassAsVariablesAsString(listMetaData, metaData));
				velocityContext.put("finalParamForDtoUpdateOnlyVariables", stringBuilder.finalParamForDtoUpdateOnlyVariables(listMetaData, metaData));
				velocityContext.put("finalParamForIdForDtoInViewForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoInViewForSetsVariablesInList(listMetaData,metaData));
				velocityContext.put("finalParamForDtoInViewForSetsVariablesInList", stringBuilder.finalParamForDtoInViewForSetsVariablesInList(listMetaData, metaData));
				velocityContext.put("finalParamForIdForViewDatesInList", Utilities.getInstance().datesId);
				velocityContext.put("finalParamForViewDatesInList", Utilities.getInstance().dates);
				velocityContext.put("isFinalParamForIdClassAsVariablesForDates", Utilities.getInstance().isFinalParamForIdClassAsVariablesForDates());
				velocityContext.put("finalParamForIdClassAsVariablesForDates", Utilities.getInstance().datesIdJSP);
				velocityContext.put("finalParamVariablesAsList", stringBuilder.finalParamVariablesAsList(listMetaData, metaData));
				velocityContext.put("isFinalParamDatesAsList", Utilities.getInstance().isFinalParamDatesAsList());
				velocityContext.put("finalParamDatesAsList", Utilities.getInstance().datesJSP);
				velocityContext.put("finalParamForIdClassAsVariables2", stringBuilderForId.finalParamForIdClassAsVariables2(listMetaData, metaData));
				velocityContext.put("finalParamForVariablesDataTablesForIdAsList", stringBuilderForId.finalParamForVariablesDataTablesForIdAsList(listMetaData, metaData));
				velocityContext.put("finalParamVariablesAsList2", stringBuilder.finalParamVariablesAsList2(listMetaData, metaData));
				velocityContext.put("finalParamForVariablesDataTablesAsList", stringBuilder.finalParamForVariablesDataTablesAsList(listMetaData, metaData));
				velocityContext.put("finalParamForIdForViewForSetsVariablesInList", stringBuilderForId.finalParamForIdForViewForSetsVariablesInList(listMetaData, metaData));
				velocityContext.put("finalParamVariablesDatesAsList2", stringBuilder.finalParamVariablesDatesAsList2(listMetaData, metaData));
				velocityContext.put("dataModel", metaDataModel);
				velocityContext.put("metaData", metaData);

				String finalParam = stringBuilder.finalParam(listMetaData, metaData);
				velocityContext.put("finalParam", finalParam);
				metaData.setFinamParam(finalParam);

				String finalParamForId= stringBuilderForId.finalParamForId(listMetaData, metaData);
				velocityContext.put("finalParamForId", finalParamForId);
				metaData.setFinalParamForId(finalParamForId);

				String finalParamVariables = stringBuilder.finalParamVariables(listMetaData, metaData);
				velocityContext.put("finalParamVariables", finalParamVariables);
				metaData.setFinamParamVariables(finalParamVariables);

				String finalParamForIdVariables = stringBuilderForId.finalParamForIdVariables(listMetaData, metaData);
				velocityContext.put("finalParamForIdVariables", finalParamForIdVariables);
				metaData.setFinalParamForIdVariables(finalParamForIdVariables);

				doDaoSpringXMLHibernate(metaData, velocityContext, hdLocation);
				doLogicSpringXMLHibernate(metaData, velocityContext, hdLocation, metaDataModel, modelName);
				doDto(metaData, velocityContext, hdLocation, metaDataModel, modelName);
				doBackingBeans(metaData, velocityContext, hdLocation, metaDataModel);
				doJsp(metaData, velocityContext, hdLocation, metaDataModel);
			}


					    doExceptions(velocityContext, hdLocation);
						doUtilites(velocityContext, hdLocation, metaDataModel, modelName);
						doPersitenceXml(metaDataModel, velocityContext, hdLocation);
						doSpringContextConfFiles(velocityContext, hdLocation, metaDataModel, modelName);
						doBusinessDelegator(velocityContext, hdLocation, metaDataModel);
						doFacesConfig(metaDataModel, velocityContext, hdLocation);
						doJspFacelets(velocityContext, hdLocation);
						doJspInitialMenu(metaDataModel, velocityContext, hdLocation);


		} catch (Exception e) {
			log.error(e.getMessage()); 
		}


	}

	@Override
	public void doDaoSpringXMLHibernate(MetaData metaData,
			VelocityContext context, String hdLocation) {
		try {
			
			String path =hdLocation + paqueteVirgen + GeneratorUtil.slash + "dataaccess" + GeneratorUtil.slash + "dao"+ GeneratorUtil.slash;
			log.info("Begin IDao");
			Template templateIDao = ve.getTemplate("IDAOSpringJpaPrime.vm");
			StringWriter swIdao = new StringWriter();
			templateIDao.merge(context, swIdao);
			FileWriter fwIdao = new FileWriter(path+ "I" +metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwIdao = new BufferedWriter(fwIdao);
			bwIdao.write(swIdao.toString());
			bwIdao.close();
			fwIdao.close();
			log.info("End IDao");
			JalopyCodeFormatter.formatJavaCodeFile(path + "I" + metaData.getRealClassName() + "DAO.java");
			
			log.info("Begin Dao");
			Template templateDao = ve.getTemplate("DAOSpringJpaPrime.vm");
			StringWriter swDao = new StringWriter();
			templateDao.merge(context, swDao);
			FileWriter fwDao = new FileWriter(path+ metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwDao = new BufferedWriter(fwDao);
			bwDao.write(swDao.toString());
			bwDao.close();
			fwDao.close();
			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "DAO.java");
			log.info("End Dao");
			
		} catch (Exception e) {
			log.error(e.getMessage());
			
		}

	}

	@Override
	public void doLogicSpringXMLHibernate(MetaData metaData,
			VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {
		try {
			String path=hdLocation + paqueteVirgen + GeneratorUtil.slash + modelName + GeneratorUtil.slash +"control" + GeneratorUtil.slash;

			log.info("Begin ILogic");
			Template templateIlogic = ve.getTemplate("ILogicSpringJpaPrimeFaces.vm");
			StringWriter swIlogic = new StringWriter();
			templateIlogic.merge(context, swIlogic);

			FileWriter fwIlogic = new FileWriter(path+"I"+ metaData.getRealClassName()+"Logic.java");
			BufferedWriter bwIlogic = new BufferedWriter(fwIlogic);
			bwIlogic.write(swIlogic.toString());
			bwIlogic.close();
			fwIlogic.close();
			log.info("End ILogic");

			log.info("Begin Logic");
			Template templateLogic = ve.getTemplate("LogicSpringJpaPrimeFaces.vm");
			StringWriter swLogic= new StringWriter();
			templateLogic.merge(context, swLogic);
			FileWriter fwLogic = new FileWriter(path+ metaData.getRealClassName() + "Logic.java");
			BufferedWriter bwLogic = new BufferedWriter(fwLogic);
			bwLogic.write(swLogic.toString());
			bwLogic.close();
			fwLogic.close();
			log.info("End Logic");
			
			JalopyCodeFormatter.formatJavaCodeFile(path + "I" + metaData.getRealClassName() + "Logic.java");
			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "Logic.java");




		} catch (Exception e) {
			log.error(e.getMessage());
		}


	}

	@Override
	public void doBusinessDelegator(VelocityContext context, String hdLocation,
			MetaDataModel dataModel) {
		try {
			
			String path = hdLocation + paqueteVirgen + GeneratorUtil.slash + "presentation"+ GeneratorUtil.slash + "businessDelegate" +GeneratorUtil.slash;
			log.info("Begin IBusinessDelegate");
			Template templateIbusinessDelegate= ve.getTemplate("IBusinessDelegatorView.vm");
			StringWriter swIbusinessDelegate = new StringWriter();
			templateIbusinessDelegate.merge(context, swIbusinessDelegate);
			FileWriter fwIbusinessDelegate = new FileWriter(path+"IBusinessDelegatorView.java");
			BufferedWriter bwIbusinessDelegate = new BufferedWriter(fwIbusinessDelegate);
			bwIbusinessDelegate.write(swIbusinessDelegate.toString());
			bwIbusinessDelegate.close();
			fwIbusinessDelegate.close();
			log.info("End IBusinessDelegate");
			
			log.info("Begin BusinessDelegate");
			Template templateBusinessDelegate = ve.getTemplate("BusinessDelegatorView.vm");
			StringWriter swBusinessDelegate = new StringWriter();
			templateBusinessDelegate.merge(context, swBusinessDelegate);
			FileWriter fwBusinessDelegate = new FileWriter(path+"BusinessDelegatorView.java");
			BufferedWriter bwBusinessDelegate = new BufferedWriter(fwBusinessDelegate);
			bwBusinessDelegate.write(swBusinessDelegate.toString());
			bwBusinessDelegate.close();
			fwBusinessDelegate.close();
			log.info("End BusinessDelegate");
			
			JalopyCodeFormatter.formatJavaCodeFile(path + "IBusinessDelegatorView.java");
			JalopyCodeFormatter.formatJavaCodeFile(path + "BusinessDelegatorView.java");
			
		} catch (Exception e) {
			log.error(e.getMessage());
			
		}

	}

	@Override
	public void doJsp(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {
		try {
			
			String path = properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			
			log.info("Begin XHTML");
			Template templateXhtml = ve.getTemplate("XHTMLSpringJpaPrime.vm");
			StringWriter swXhtml = new StringWriter();
			templateXhtml.merge(context, swXhtml);
			
			FileWriter fwXhtml = new FileWriter(path+metaData.getRealClassNameAsVariable()+".xhtml");
			BufferedWriter bwXhtml = new BufferedWriter(fwXhtml);
			bwXhtml.write(swXhtml.toString());
			bwXhtml.close();
			fwXhtml.close();
			log.info("End XHTML");
			
			log.info("Begin DataTable");
			Template templateDataTable = ve.getTemplate("XHTMLdataTablesJpaPrime.vm");
			StringWriter swDataTable = new StringWriter();
			templateDataTable.merge(context, swDataTable);
			FileWriter fwDataTable = new FileWriter(path+metaData.getRealClassNameAsVariable()+"ListDataTable.xhtml");
			BufferedWriter bwDataTable = new BufferedWriter(fwDataTable);
			bwDataTable.write(swDataTable.toString());
			bwDataTable.close();
			fwDataTable.close();
			log.info("End DataTable");
			
			log.info("Begin DataTableEditable");
			Template templateDataTableEditable = ve.getTemplate("XHTMLdataTableEditableJpaPrime.vm");
			StringWriter swDataTableEditable = new StringWriter();
			templateDataTableEditable.merge(context, swDataTableEditable);
			FileWriter fwDataTableEditable = new FileWriter(path+ metaData.getRealClassNameAsVariable()+"ListDataTableEditable.xhtml");
			BufferedWriter bwDataTableEditable = new BufferedWriter(fwDataTableEditable);
			bwDataTableEditable.write(swDataTableEditable.toString());
			bwDataTableEditable.close();
			fwDataTableEditable.close();
			log.info("End DataTableEditable");
			
			Utilities.getInstance().datesJSP = null;
			Utilities.getInstance().datesIdJSP = null;
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doJspInitialMenu(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {
		try {
			String path = properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			
			log.info("Begin InitialMaenu");
			Template templateInitialMenu = ve.getTemplate("XHTMLinitialMenu.vm");
			StringWriter swInitialMenu = new StringWriter();
			templateInitialMenu.merge(context, swInitialMenu);
			FileWriter fwInitialMenu = new FileWriter(path+"initialMenu.xhtml");
			BufferedWriter bwInitialMenu = new BufferedWriter(fwInitialMenu);
			bwInitialMenu.write(swInitialMenu.toString());
			bwInitialMenu.close();
			fwInitialMenu.close();
			log.info("Begin InitialMaenu");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context,
			String hdLocation) {
		try {
			
			String path = properties.getProperty("webRootFolderPath")+"WEB-INF"+ GeneratorUtil.slash;
			log.info("Begin FacesConfig");
			Template templateFacesConfig = ve.getTemplate("faces-configSpringPrimeJpa.xml.vm");
			StringWriter swFacesConfig = new StringWriter();
			templateFacesConfig.merge(context, swFacesConfig);
			
			FileWriter fwFacesConfig = new FileWriter(path+"faces-config.xml");
			BufferedWriter bwFacesConfig = new BufferedWriter(fwFacesConfig);
			bwFacesConfig.write(swFacesConfig.toString());
			bwFacesConfig.close();
			fwFacesConfig.close();
			log.info("Begin FacesConfig");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doDto(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {
		try {

			log.info("Begin Dto");
			Template dtoTemplate = ve.getTemplate("DtoPrimeSpringJpa.vm");
			StringWriter swDto= new StringWriter();
			dtoTemplate.merge(context, swDto);
			String path= hdLocation + paqueteVirgen + GeneratorUtil.slash + modelName + GeneratorUtil.slash + "dto"+ GeneratorUtil.slash;
			FileWriter fwDto = new FileWriter(path+ metaData.getRealClassName() + "DTO.java");
			BufferedWriter bwDto = new BufferedWriter(fwDto);
			bwDto.write(swDto.toString());
			bwDto.close();
			fwDto.close();
			log.info("End Dto");
			
			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "DTO.java");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doExceptions(VelocityContext context, String hdLocation) {
		try {

			String path=hdLocation + paqueteVirgen + GeneratorUtil.slash + "exceptions" + GeneratorUtil.slash;
			log.info("Begin Exception");
			Template templateException = ve.getTemplate("ZMessManager.vm");
			StringWriter swException = new StringWriter();
			templateException.merge(context, swException);
			FileWriter fwException = new FileWriter(path+ "ZMessManager.java");
			BufferedWriter bwException = new BufferedWriter(fwException);
			bwException.write(swException.toString());
			bwException.close();
			fwException.close();
			log.info("End Exception");
			JalopyCodeFormatter.formatJavaCodeFile(path+ "ZMessManager.java");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doUtilites(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {

		try {
			
			String path=hdLocation + paqueteVirgen + GeneratorUtil.slash + "utilities" + GeneratorUtil.slash;
			log.info("Begin Utilities");
			Template templateUtilities = ve.getTemplate("Utilities.vm");
			StringWriter swUtilities= new StringWriter();
			templateUtilities.merge(context, swUtilities);
			FileWriter fwUtilities = new FileWriter(path+"Utilities.java");
			BufferedWriter bwUtilities = new BufferedWriter(fwUtilities);
			bwUtilities.write(swUtilities.toString());
			bwUtilities.close();
			fwUtilities.close();
			log.info("Begin Utilities");
			
			log.info("Begin FacesUtils ");
			Template templateFacesUtils = ve.getTemplate("FacesUtils.vm");
			StringWriter swFacesUtils = new StringWriter();
			templateFacesUtils.merge(context, swFacesUtils);
			FileWriter fwFacesUtils = new FileWriter(path+"FacesUtils.java");
			BufferedWriter bwFacesUtils = new BufferedWriter(fwFacesUtils);
			bwFacesUtils.write(swFacesUtils.toString());
			bwFacesUtils.close();
			fwFacesUtils.close();
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doJspFacelets(VelocityContext context, String hdLocation) {
		try {
			String pathFaceletes =  properties.getProperty("webRootFolderPath") + "WEB-INF" + GeneratorUtil.slash + "facelets" + GeneratorUtil.slash;
			
			log.info("Begin Header");
			Template templateHeader = ve.getTemplate("JSPheader.vm");
			StringWriter swHeader = new StringWriter();
			templateHeader.merge(context, swHeader);
			FileWriter fwHeader = new FileWriter(pathFaceletes+"header.jspx");
			BufferedWriter bwHeader = new BufferedWriter(fwHeader);
			bwHeader.write(swHeader.toString());
			bwHeader.close();
			fwHeader.close();
			log.info("End Header");
			
			log.info("Begin Footer");
			Template templateFooter = ve.getTemplate("JSPfooter.vm");
			StringWriter swFooter = new StringWriter();
			templateFooter.merge(context, swFooter);
			FileWriter fwFooter = new FileWriter(pathFaceletes+ "footer.jspx");
			BufferedWriter bwFooter = new BufferedWriter(fwFooter);
			bwFooter.write(swFooter.toString());
			bwFooter.close();
			fwFooter.close();
			log.info("End Footer");
			
			log.info("Begin Footer initialMenu");
			Template footerInitialMenu = ve.getTemplate("footerInitialMenu.vm");
			StringWriter swFooterInitialMenu = new StringWriter();
			footerInitialMenu.merge(context, swFooterInitialMenu);
			FileWriter fwFooterInitialMenu = new FileWriter(pathFaceletes+"footerInitialMenu.jspx");
			BufferedWriter bwFooterInitialMenu = new BufferedWriter(fwFooterInitialMenu);
			bwFooterInitialMenu.write(swFooterInitialMenu.toString());
			bwFooterInitialMenu.close();
			fwFooterInitialMenu.close();
			log.info("End Footer initialMenu");
			
			String pathCommons= properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			log.info("Begin CommonsColumsContent");
			Template templateCommonsColumns = ve.getTemplate("CommonColumnsContent.vm");
			StringWriter swCommonsColumns = new StringWriter();
			templateCommonsColumns.merge(context, swCommonsColumns);
			FileWriter fwCommonsColumns = new FileWriter(pathCommons+"CommonColumnsContent.xhtml");
			BufferedWriter bwCommonsColumns = new BufferedWriter(fwCommonsColumns);
			bwCommonsColumns.write(swCommonsColumns.toString());
			bwCommonsColumns.close();
			fwCommonsColumns.close();
			log.info("End CommonColumsContent");
			
			log.info("Begin CommonLayout");
			Template templateCommonLayout = ve.getTemplate("CommonLayout.vm");
			StringWriter swCommonLayout = new StringWriter();
			templateCommonLayout.merge(context, swCommonLayout);
			FileWriter fwCommonLayout = new FileWriter(pathCommons+"CommonLayout.xhtml");
			BufferedWriter bwCommonLayout = new BufferedWriter(fwCommonLayout);
			bwCommonLayout.write(swCommonLayout.toString());
			bwCommonLayout.close();
			fwCommonLayout.close();
			log.info("End CommonLayout");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doSpringContextConfFiles(VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {
		try {
			log.info("Begin applicationContext.xml");
			Template templateApplicationContext = ve.getTemplate("applicationContext.xml.vm");
			StringWriter swApplicationContext = new StringWriter();
			templateApplicationContext.merge(context, swApplicationContext);
			FileWriter fwApplicationContext = new FileWriter(hdLocation + "applicationContext.xml");
			BufferedWriter bwApplicationContext = new BufferedWriter(fwApplicationContext);
			bwApplicationContext.write(swApplicationContext.toString());
			bwApplicationContext.close();
			fwApplicationContext.close();
			log.info("End applicationContext.xml");
			
			log.info("Begin aopContext.xml");
			Template templateAopContext = ve.getTemplate("aopContext.xml.vm");
			StringWriter swAopContext = new StringWriter();
			templateAopContext.merge(context, swAopContext);
			FileWriter fwAopContext = new FileWriter(hdLocation+"aopContext.xml");
			BufferedWriter bwAopContext = new BufferedWriter(fwAopContext);
			bwAopContext.write(swAopContext.toString());
			bwAopContext.close();
			fwAopContext.close();
			log.info("End aopContext.xml");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doPersitenceXml(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {
		
		try {
			
			log.info("Begin persistnece.xml");
			Template templatePersistence = ve.getTemplate("persistence.xml.vm");
			StringWriter swPersistence = new StringWriter();
			templatePersistence.merge(context, swPersistence);
			FileWriter fwPersistence = new FileWriter(hdLocation + "META-INF" + GeneratorUtil.slash + "persistence.xml");
			BufferedWriter bwPersistence = new BufferedWriter(fwPersistence);
			bwPersistence.write(swPersistence.toString());
			bwPersistence.close();
			fwPersistence.close();
			log.info("Begin persistnece.xml");
		
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doBackingBeans(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {
		try {
			String path =hdLocation + paqueteVirgen + GeneratorUtil.slash + "presentation" + GeneratorUtil.slash + "backingBeans" + GeneratorUtil.slash;
			log.info("Begin BackEndBean ");
			Template templateBackEndBean= ve.getTemplate("BackingBeansSpringJpaPrime.vm");
			StringWriter swBackEndBean = new StringWriter();
			templateBackEndBean.merge(context, swBackEndBean);
			FileWriter fwBackEndBean = new FileWriter(path+ metaData.getRealClassName() + "View.java");
			BufferedWriter bwBackEndBean = new BufferedWriter(fwBackEndBean);
			bwBackEndBean.write(swBackEndBean.toString());
			bwBackEndBean.close();
			fwBackEndBean.close();
			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "View.java");
			Utilities.getInstance().dates = null;
			Utilities.getInstance().datesId = null;
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

}
