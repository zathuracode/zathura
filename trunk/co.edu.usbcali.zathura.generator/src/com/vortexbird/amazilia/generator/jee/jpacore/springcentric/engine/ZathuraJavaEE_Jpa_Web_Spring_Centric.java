package com.vortexbird.amazilia.generator.jee.jpacore.springcentric.engine;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;


import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.generator.utilities.JalopyCodeFormatter;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;

import com.sun.xml.internal.xsom.impl.FacetImpl;
import com.vortexbird.amazilia.generator.jee.jpacore.springcentric.utils.Utilities;
import com.vortexbird.amazilia.generator.jee.jpacore.springcentric.utils.IStringBuilderForId;
import com.vortexbird.amazilia.generator.jee.jpacore.springcentric.utils.IStringBuilder;
import com.vortexbird.amazilia.generator.jee.jpacore.springcentric.utils.StringBuilderForId;
import com.vortexbird.amazilia.generator.jee.jpacore.springcentric.utils.StringBuilder;



/**
 * 
 * @author M@uricio
 *
 */




public class ZathuraJavaEE_Jpa_Web_Spring_Centric implements  IZathuraTemplate,IZathuraGenerator {
	private static Logger log = Logger.getLogger(ZathuraJavaEE_Jpa_Web_Spring_Centric.class);	
	private static final String springJpa = GeneratorUtil.getSpringJpaTemplates(); 
	private String paqueteVirgen= new String();
	private VelocityEngine ve;
	private Properties properties;
	private String webRootPath;
	private String fullPath;

	@Override
	public void toGenerate(MetaDataModel metaDataModel, String projectName,
			String folderProjectPath, Properties propiedades) {

		try {

			webRootPath=(propiedades.getProperty("webRootFolderPath"));					
			properties=propiedades;

			// path del paquete donde se crean las entitys
			String nombrePaquete= propiedades.getProperty("jpaPckgName");
			Integer specificityLevel = (Integer) propiedades.get("specificityLevel");
			String  domainName= nombrePaquete.substring(0, nombrePaquete.indexOf("."));
			log.info("NombrePaquete----------------->"+nombrePaquete);
			log.info("Nivel----------------->"+specificityLevel);
			log.info("domainName----------------->"+domainName);
			log.info("hdLocation----------------->"+ folderProjectPath);
			log.info(propiedades.get("webRootFolderPath"));



			doTemplate(folderProjectPath, metaDataModel, nombrePaquete, projectName, specificityLevel, domainName);
			copyLibraries();


			List<MetaData> metaData= metaDataModel.getTheMetaData();
			IStringBuilderForId stringBuilderId= new StringBuilderForId(metaData);
			IStringBuilder stringBuilder= new StringBuilder(metaData,(StringBuilderForId) stringBuilderId);





			for (MetaData md : metaData) {
				log.info("NombreMetadata"+md.getName());
				List<String> salida= stringBuilderId.finalParamForIdClassAsVariables(metaData, md);
				List<String> salida2= stringBuilder.finalParamVariablesAsList(metaData, md);


				for (int i = 0; i < salida2.size(); i++) {

					log.info(salida2.get(i));

				}



			}





		} catch (Exception e) {
			e.printStackTrace();
		}






	}



	private void copyLibraries(){

		System.out.println("");
		// path de las librerias, css, images,etc
		String pathIndexJsp = GeneratorUtil.getGeneratorExtZathuraJavaEESpringJpa()+"index.jsp";
		String pathCss= GeneratorUtil.getGeneratorExtZathuraJavaEESpringJpa()+"css"+GeneratorUtil.slash;
		String pathImages= GeneratorUtil.getGeneratorExtZathuraJavaEESpringJpa()+"images"+GeneratorUtil.slash;
		String pathWebXml= GeneratorUtil.getGeneratorExtZathuraJavaEESpringJpa()+"WEB-INF"+GeneratorUtil.slash;
		String pathXmlhttp= GeneratorUtil.getGeneratorExtZathuraJavaEESpringJpa()+"xmlhttp"+GeneratorUtil.slash;
		String pathHibernate= GeneratorUtil.getGeneratorLibrariesZathuraJavaEESpringJpa()+"core-hibernate3.3"+GeneratorUtil.slash;
		String pathIceFaces= GeneratorUtil.getGeneratorLibrariesZathuraJavaEESpringJpa()+"iceFaces1.8.1"+GeneratorUtil.slash;
		String pathSpring= GeneratorUtil.getGeneratorLibrariesZathuraJavaEESpringJpa()+"spring3.0"+GeneratorUtil.slash;
		String pathLog4J= GeneratorUtil.getGeneratorExtZathuraJavaEESpringJpa()+"log4j"+GeneratorUtil.slash;
		String pathLib= properties.getProperty("libFolderPath");
		 

		// copy css
		GeneratorUtil.createFolder(webRootPath+"css");
		GeneratorUtil.copyFolder(pathCss, webRootPath +"css"+GeneratorUtil.slash);
		// copy images
		GeneratorUtil.createFolder(webRootPath+"images");
		GeneratorUtil.copyFolder(pathImages, webRootPath +"images"+GeneratorUtil.slash);
		// copy web.xml
		GeneratorUtil.copyFolder(pathWebXml,webRootPath+"WEB-INF"+GeneratorUtil.slash);
		// copy xmlhttp
		GeneratorUtil.createFolder(webRootPath+"xmlhttp");
		GeneratorUtil.copyFolder(pathXmlhttp, webRootPath+"xmlhttp"+GeneratorUtil.slash);

		//copy libraries
		GeneratorUtil.copyFolder(pathHibernate, pathLib);
		GeneratorUtil.copyFolder(pathIceFaces, pathLib);
		GeneratorUtil.copyFolder(pathSpring, pathLib);
		
		// copy index.jsp
		GeneratorUtil.copy(pathIndexJsp,webRootPath+"index.jsp" );
		// copy Log4J
		String folderProjectPath = properties.getProperty("folderProjectPath");
		GeneratorUtil.copyFolder(pathLog4J, folderProjectPath );
		
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
			propiedadesVelocity.put("file.resource.loader.path",springJpa);
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

		}




	}

	@Override
	public void doDaoSpringXMLHibernate(MetaData metaData,
			VelocityContext context, String hdLocation) {

		try {

			log.info("Begin IDaoSpringJpa");
			Template IdaoSpringTemplate= ve.getTemplate("IDAOSpringJpa.vm");
			StringWriter swIdaoSpring= new StringWriter();
			IdaoSpringTemplate.merge(context, swIdaoSpring);

			String daoPath= hdLocation + paqueteVirgen + GeneratorUtil.slash + "dataaccess" + GeneratorUtil.slash + "dao"+ GeneratorUtil.slash; 
			FileWriter fwIdaoSpring= new FileWriter(daoPath + "I" +metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwIdaoSpring = new BufferedWriter(fwIdaoSpring);
			bwIdaoSpring.write(swIdaoSpring.toString());
			bwIdaoSpring.close();
			log.info("End IdaoSpringJpa");


			log.info("Begin DaosSpringJpa");
			Template daoSpringTemplate= ve.getTemplate("DAOSpringJpa.vm");
			StringWriter swDaosSpring= new StringWriter();
			daoSpringTemplate.merge(context, swDaosSpring);
			FileWriter fwDaoSpring = new FileWriter(daoPath + metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwDaosSpring= new BufferedWriter(fwDaoSpring);
			bwDaosSpring.write(swDaosSpring.toString());
			bwDaosSpring.close();
			log.info("EndDaoSpringJpa");

			// Se usa Jalopy para que el estilo de las clases queden con el de sun
			JalopyCodeFormatter.formatJavaCodeFile(daoPath + "I" + metaData.getRealClassName() + "DAO.java");
			JalopyCodeFormatter.formatJavaCodeFile(daoPath + metaData.getRealClassName() + "DAO.java");


		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doLogicSpringXMLHibernate(MetaData metaData,
			VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {


		try {

			log.info("Begin ILogic SpringJpa");
			Template iLogicSpringTemplate = ve.getTemplate("ILogic.vm");
			StringWriter swILogicSpring = new StringWriter();
			iLogicSpringTemplate.merge(context, swILogicSpring);

			String pathILogic= hdLocation + paqueteVirgen + GeneratorUtil.slash + modelName + GeneratorUtil.slash +"control" + GeneratorUtil.slash; 
			FileWriter fwILogic = new FileWriter(pathILogic+"I"+ metaData.getRealClassName()+"Logic.java");
			BufferedWriter bwILogic= new BufferedWriter(fwILogic);
			bwILogic.write(swILogicSpring.toString());
			bwILogic.close();
			log.info("End ILogic springJpa");


			log.info("Begin LogicSpringJpa");
			Template LogicSpringTemplate = ve.getTemplate("LogicSpringJpa.vm");
			StringWriter swLogicSpringJpa = new StringWriter();
			LogicSpringTemplate.merge(context, swLogicSpringJpa);
			FileWriter fwLogicSpringJpa = new FileWriter(pathILogic + metaData.getRealClassName() + "Logic.java");
			BufferedWriter bwLogicSpringJpa= new BufferedWriter(fwLogicSpringJpa);
			bwLogicSpringJpa.write(swLogicSpringJpa.toString());
			bwLogicSpringJpa.close();
			log.info("End Logic Spring Jpa");


			JalopyCodeFormatter.formatJavaCodeFile(pathILogic + "I" + metaData.getRealClassName() + "Logic.java");
			JalopyCodeFormatter.formatJavaCodeFile(pathILogic + metaData.getRealClassName() + "Logic.java");




		} catch (Exception e) {
			log.error(e.getMessage());
		}


	}

	@Override
	public void doBusinessDelegator(VelocityContext context, String hdLocation,
			MetaDataModel dataModel) {
		try {

			String pathBusinessDelegator = hdLocation + paqueteVirgen + GeneratorUtil.slash + "presentation"+ GeneratorUtil.slash + "businessDelegate" +GeneratorUtil.slash;

			log.info("Begin IBusinessDelegatorSpringJpa");
			Template templateIBusinessDelegatorSpringJpa= ve.getTemplate("IBusinessDelegatorView.vm");
			StringWriter swIBusinessDelegatorSpringJpa= new StringWriter();
			templateIBusinessDelegatorSpringJpa.merge(context, swIBusinessDelegatorSpringJpa);
			FileWriter fwIBusinessDelegator = new FileWriter(pathBusinessDelegator +"IBusinessDelegatorView.java");
			BufferedWriter bfIBusinessDelegator = new BufferedWriter(fwIBusinessDelegator);
			bfIBusinessDelegator.write(swIBusinessDelegatorSpringJpa.toString());
			bfIBusinessDelegator.close();
			JalopyCodeFormatter.formatJavaCodeFile(pathBusinessDelegator + "IBusinessDelegatorView.java");
			log.debug("End IBusinessDelegatorSpringJpa");


			log.info("Begin BusinessDelegatorSpringJpa");
			Template templateBusinessDelegatorSpringJpa = ve.getTemplate("BusinessDelegatorView.vm");
			StringWriter swBusinessDelegatorSpringJpa = new StringWriter();
			templateBusinessDelegatorSpringJpa.merge(context, swBusinessDelegatorSpringJpa);
			FileWriter fwBusinessDelegatorSpringJpa = new FileWriter(pathBusinessDelegator + "BusinessDelegatorView.java" );
			BufferedWriter bwBusisnessDelegatorSpringJpa = new BufferedWriter(fwBusinessDelegatorSpringJpa);
			bwBusisnessDelegatorSpringJpa.write(swBusinessDelegatorSpringJpa.toString());
			bwBusisnessDelegatorSpringJpa.close();
			JalopyCodeFormatter.formatJavaCodeFile(pathBusinessDelegator + "BusinessDelegatorView.java");
			log.info("End BusinessDelegatorSpring");






		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doJsp(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {

		try {
			String pathJspx= webRootPath +"JSPX"+GeneratorUtil.slash;
			
			log.info("Begin jspx");
			Template jspTemplate= ve.getTemplate("JSP.vm");
			StringWriter swJsp= new StringWriter();
			jspTemplate.merge(context, swJsp);
			FileWriter fwJsp= new FileWriter(pathJspx+metaData.getRealClassNameAsVariable()+".jspx");
			BufferedWriter bwJsp= new BufferedWriter(fwJsp);
			bwJsp.write(swJsp.toString());
			bwJsp.close();
			log.info("End jspx");
			
			log.info("Begin dataTable.jspx");
			Template dataTableJspx= ve.getTemplate("JSPdataTables.vm");
			StringWriter swDataTableJspx= new StringWriter();
			dataTableJspx.merge(context, swDataTableJspx);
			FileWriter fwDataTable = new FileWriter(pathJspx+ metaData.getRealClassNameAsVariable()+"ListDataTable.jspx");
			BufferedWriter bwDataTable = new BufferedWriter(fwDataTable);
			bwDataTable.write(swDataTableJspx.toString());
			bwDataTable.close();
			log.info("End ListDataTable.jspx");
			
			
			log.info("Begin jspDataTableEditable");
			Template jspTableEditable =ve.getTemplate("JSPdataTables-Editable.vm");
			StringWriter swJspTableEditable= new StringWriter();
			jspTableEditable.merge(context, swJspTableEditable);
			FileWriter fwJspTableEditable= new FileWriter(pathJspx + metaData.getRealClassNameAsVariable()+"ListDataTableEditable.jspx");
			BufferedWriter bwJspTableEditable = new BufferedWriter(fwJspTableEditable);
			bwJspTableEditable.write(swJspTableEditable.toString());
			bwJspTableEditable.close();
			log.info("End jspTableEditable");
			
			
			
			
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

			String pathJspx= webRootPath + "JSPX"+ GeneratorUtil.slash;

			log.info("Begin initialMenu.jspx");
			Template initialmenu = ve.getTemplate("JSPinitialMenu.vm");
			StringWriter swInitialMenu= new StringWriter();
			initialmenu.merge(context, swInitialMenu);
			FileWriter fwInitialMenu= new FileWriter(pathJspx+"initialMenu.jspx");
			BufferedWriter bwInitialMenu= new BufferedWriter(fwInitialMenu);
			bwInitialMenu.write(swInitialMenu.toString());
			bwInitialMenu.close();
			log.info("End initialMenu.jspx");

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context,String hdLocation) {
		try {

			log.info("Begin FacesConfigSpringJpa");
			Template facesConfigSpringJpa = ve.getTemplate("faces-config.xml.vm");
			StringWriter swFacesConfigSpringJpa= new StringWriter();
			facesConfigSpringJpa.merge(context, swFacesConfigSpringJpa);

			String pathFacesConfig= properties.getProperty("webRootFolderPath")+"WEB-INF"+ GeneratorUtil.slash;
			FileWriter fwFacesConfigSpringJpa = new FileWriter(pathFacesConfig+"faces-config.xml");
			BufferedWriter bwFacesConfigSpringJpa= new BufferedWriter(fwFacesConfigSpringJpa);
			bwFacesConfigSpringJpa.write(swFacesConfigSpringJpa.toString());
			bwFacesConfigSpringJpa.close();
			log.info("End FacesConfigSpringJpa");

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doDto(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {

		try {

			log.info("Begin DtoSpringJpa");
			Template templateDtoSpringJpa= ve.getTemplate("DtoSpringJpa.vm");
			StringWriter swTemplateDtoSpringJpa= new StringWriter();
			templateDtoSpringJpa.merge(context, swTemplateDtoSpringJpa);

			String pathDtoSpringJpa = hdLocation + paqueteVirgen + GeneratorUtil.slash + modelName + GeneratorUtil.slash + "dto"+ GeneratorUtil.slash;  
			FileWriter fwDtoSpringJpa = new FileWriter(pathDtoSpringJpa + metaData.getRealClassName() + "DTO.java");
			BufferedWriter bwDtoSpringJpa = new BufferedWriter(fwDtoSpringJpa);
			bwDtoSpringJpa.write(swTemplateDtoSpringJpa.toString());
			bwDtoSpringJpa.close();
			log.info("End DtoSpringJpa");
			JalopyCodeFormatter.formatJavaCodeFile(pathDtoSpringJpa + metaData.getRealClassName() + "DTO.java");

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doExceptions(VelocityContext context, String hdLocation) {
		try {

			log.info("Begin ExceptionSpringJpa");
			Template exceptionSpringJpa= ve.getTemplate("ZMessManager.vm");
			StringWriter swExceptionJpa= new StringWriter();
			exceptionSpringJpa.merge(context, swExceptionJpa);

			String pathExceptionSpringJpa= hdLocation + paqueteVirgen + GeneratorUtil.slash + "exceptions" + GeneratorUtil.slash;
			FileWriter fwExceptionSpringJpA = new FileWriter(pathExceptionSpringJpa + "ZMessManager.java");
			BufferedWriter bwExceptionSpringJpa= new BufferedWriter(fwExceptionSpringJpA);
			bwExceptionSpringJpa.write(swExceptionJpa.toString());
			bwExceptionSpringJpa.close();
			log.info("End exceptionSpringJpa");

			JalopyCodeFormatter.formatJavaCodeFile(pathExceptionSpringJpa+ "ZMessManager.java");

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doUtilites(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {


		try {

			log.info("Begin UtilitiesSpring Jpa");
			Template utilitiesSpringJpa = ve.getTemplate("Utilities.vm");
			StringWriter swUtilitiesSpringJpa= new StringWriter();
			utilitiesSpringJpa.merge(context, swUtilitiesSpringJpa);
			String utilitiesPath= hdLocation + paqueteVirgen + GeneratorUtil.slash + "utilities" + GeneratorUtil.slash;
			FileWriter fwUtilitiesSpringJpa= new FileWriter(utilitiesPath+"Utilities.java");
			BufferedWriter bwUtilitiesJpa = new BufferedWriter(fwUtilitiesSpringJpa);
			bwUtilitiesJpa.write(swUtilitiesSpringJpa.toString());
			bwUtilitiesJpa.close();
			log.info("End UtilitiesSpringJpa");


			log.info("Begin FacesUtilsSpringJpa");
			Template templateFacesUtilsSpringJpa= ve.getTemplate("FacesUtils.vm");
			StringWriter swFacesUtilsSpringJpa = new StringWriter();
			templateFacesUtilsSpringJpa.merge(context, swFacesUtilsSpringJpa);
			FileWriter fwFacesUtilsSpringJpa = new FileWriter(utilitiesPath + "FacesUtils.java");
			BufferedWriter bwFacesUtilsSpringJpa = new BufferedWriter(fwFacesUtilsSpringJpa);
			bwFacesUtilsSpringJpa.write(swFacesUtilsSpringJpa.toString());
			bwFacesUtilsSpringJpa.close();
			log.info("End FacesUtils SpringJpa");

			log.info("Begin DataSourceSpringJpa");
			Template dataSourceSpringJpa= ve.getTemplate("DataSource.vm");
			StringWriter swDataSourceSpringJpa= new StringWriter();
			dataSourceSpringJpa.merge(context, swDataSourceSpringJpa);
			FileWriter fwDataSourceSpringJpa = new FileWriter(utilitiesPath + "DataSource.java");
			BufferedWriter bwDataSourceSpringJpa= new BufferedWriter(fwDataSourceSpringJpa);
			bwDataSourceSpringJpa.write(swDataSourceSpringJpa.toString());
			bwDataSourceSpringJpa.close();
			log.info("End DataSourceSpringJpa");


			log.info("Begin DataPageSpringJpa");
			Template dataPageSpringJpa= ve.getTemplate("DataPage.vm");
			StringWriter swPageSpringJpa = new StringWriter();
			dataPageSpringJpa.merge(context, swPageSpringJpa);
			FileWriter fwPageSpringJpa= new FileWriter(utilitiesPath+"DataPage.java");
			BufferedWriter bwPageSpringJpa = new BufferedWriter(fwPageSpringJpa);
			bwPageSpringJpa.write(swPageSpringJpa.toString());
			bwPageSpringJpa.close();
			log.info("End DataPageSpringJpa");


			log.info("Begin PagedListDataModelSpringJpa");
			Template pageListSpringJpa= ve.getTemplate("PagedListDataModel.vm");
			StringWriter swPageListSpringJpa = new StringWriter();
			pageListSpringJpa.merge(context, swPageListSpringJpa);
			FileWriter fwPageListSpringJpa= new FileWriter(utilitiesPath+"PagedListDataModel.java");
			BufferedWriter bwPageListSpringJpa = new BufferedWriter(fwPageListSpringJpa);
			bwPageListSpringJpa.write(swPageListSpringJpa.toString());
			bwPageListSpringJpa.close();
			log.info("End PageListDataModelSpringJpa");

			JalopyCodeFormatter.formatJavaCodeFile(utilitiesPath + "Utilites.java");
			JalopyCodeFormatter.formatJavaCodeFile(utilitiesPath + "FacesUtils.java");
			JalopyCodeFormatter.formatJavaCodeFile(utilitiesPath + "DataSource.java");
			JalopyCodeFormatter.formatJavaCodeFile(utilitiesPath + "DataPage.java");
			JalopyCodeFormatter.formatJavaCodeFile(utilitiesPath + "PagedListDataModel.java");


		} catch (Exception e) {
			log.error(e.getMessage());
		}


	}

	@Override
	public void doJspFacelets(VelocityContext context, String hdLocation) {
		try {

			String pathFacelets= properties.getProperty("webRootFolderPath")+"WEB-INF"+GeneratorUtil.slash+"facelets"+GeneratorUtil.slash;

			log.info("Begin headerSpringJpa");
			Template jspHeaderSpringJpa= ve.getTemplate("JSPheader.vm");
			StringWriter swJspHeaderSpringJpa = new StringWriter();
			jspHeaderSpringJpa.merge(context, swJspHeaderSpringJpa);
			FileWriter fwJspHeaderSpringJpa = new FileWriter(pathFacelets+"header.jspx");
			BufferedWriter bwJspHeaderSpringJpa= new BufferedWriter(fwJspHeaderSpringJpa);
			bwJspHeaderSpringJpa.write(swJspHeaderSpringJpa.toString());
			bwJspHeaderSpringJpa.close();
			log.info("End JspHeaderSpringJpa");


			log.info("Begin JspMainTemplateSpringJpa");
			Template mainTemplateSpringJpa = ve.getTemplate("JSPmainTemplate.vm");
			StringWriter swMainTemplateSpringJpa= new StringWriter();
			mainTemplateSpringJpa.merge(context, swMainTemplateSpringJpa);
			FileWriter fwMainTemplateSpringJpa= new FileWriter(pathFacelets + "mainTemplate.jspx");
			BufferedWriter bwMainTemplateSpringJpa= new BufferedWriter(fwMainTemplateSpringJpa);
			bwMainTemplateSpringJpa.write(swMainTemplateSpringJpa.toString());
			bwMainTemplateSpringJpa.close();
			log.info("End JspMainTemplateSpringJpa");

			log.info("Begin JspFooterSpringJpa");
			Template footerSpringJpa = ve.getTemplate("JSPfooter.vm");
			StringWriter swFooterSpringJpa = new StringWriter();
			footerSpringJpa.merge(context, swFooterSpringJpa);
			FileWriter fwFooterSpringJpa= new FileWriter(pathFacelets+"footer.jspx");
			BufferedWriter bwFooterSpringJpa = new BufferedWriter(fwFooterSpringJpa);
			bwFooterSpringJpa.write(swFooterSpringJpa.toString());
			bwFooterSpringJpa.close();
			log.info("End JspFooterSpringJpa");




		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doSpringContextConfFiles(VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {

		try {

			log.info("Begin applicationContext.xml");
			Template applicationContextTemplate= ve.getTemplate("applicationContext.xml.vm");
			StringWriter swApplicationContext= new StringWriter();
			applicationContextTemplate.merge(context, swApplicationContext);

			FileWriter fwApplicationContext= new FileWriter(hdLocation + "applicationContext.xml");
			BufferedWriter bwApplicationContext = new BufferedWriter(fwApplicationContext);
			bwApplicationContext.write(swApplicationContext.toString());
			bwApplicationContext.close();
			log.info("End applicationContext.xml");


			log.info("Begin AopContext.xml");
			Template aopContextSpringJpa = ve.getTemplate("aopContext.xml.vm");
			StringWriter swAopContext=  new StringWriter();
			aopContextSpringJpa.merge(context, swAopContext);
			FileWriter fwAopContext= new FileWriter(hdLocation+"aopContext.xml");
			BufferedWriter bwAopContext = new BufferedWriter(fwAopContext);
			bwAopContext.write(swAopContext.toString());
			bwAopContext.close();
			log.info("End aopContext.xml");

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}



	@Override
	public void doPersitenceXml(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {



		Template templatePersistenxml= null;
		StringWriter swPersistencexml= new StringWriter();

		try {
			log.info("Begin persistence.xml");
			templatePersistenxml= ve.getTemplate("persistence.xml.vm");
			templatePersistenxml.merge(context, swPersistencexml);
			FileWriter fw = new FileWriter(hdLocation + "META-INF" + GeneratorUtil.slash + "persistence.xml");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(swPersistencexml.toString());
			bw.close();
			log.info("End Persistence.xml");


		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}



	@Override
	public void doBackingBeans(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {

		try {

			log.info("Begin BackingBeansSpringjpa");

			Template templateBackingBeanSpringJpa = ve.getTemplate("BackingBeans.vm");
			StringWriter swBackingBeanSpringJpa = new StringWriter();
			templateBackingBeanSpringJpa.merge(context, swBackingBeanSpringJpa);

			String pathBackingBeanSpringJpa= hdLocation + paqueteVirgen + GeneratorUtil.slash + "presentation" + GeneratorUtil.slash + "backingBeans" + GeneratorUtil.slash;
			FileWriter fwBackingBeanSpringJpa= new FileWriter(pathBackingBeanSpringJpa + metaData.getRealClassName() + "View.java");
			BufferedWriter bwBackingBeanSpringJpa= new BufferedWriter(fwBackingBeanSpringJpa);
			bwBackingBeanSpringJpa.write(swBackingBeanSpringJpa.toString());
			bwBackingBeanSpringJpa.close();
			log.info("End BackingBeansSpringJpa");
			JalopyCodeFormatter.formatJavaCodeFile(pathBackingBeanSpringJpa + metaData.getRealClassName() + "View.java");
			
			Utilities.getInstance().dates = null;
			Utilities.getInstance().datesId = null;




		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}




}
