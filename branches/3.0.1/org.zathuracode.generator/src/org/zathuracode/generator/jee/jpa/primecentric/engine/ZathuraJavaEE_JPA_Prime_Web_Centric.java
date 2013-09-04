package org.zathuracode.generator.jee.jpa.primecentric.engine;

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
import org.zathuracode.generator.jee.jpa.primecentric.utils.*;
import org.zathuracode.generator.model.IZathuraGenerator;
import org.zathuracode.generator.utilities.GeneratorUtil;
import org.zathuracode.generator.utilities.JalopyCodeFormatter;
import org.zathuracode.metadata.model.MetaData;
import org.zathuracode.metadata.model.MetaDataModel;
import org.zathuracode.generator.jee.jpa.primecentric.utils.StringBuilder;

/**
 * Zathura Generator.
 * @author Andrés Mauricio Cárdenas Pérez (mauriciocardenasp@gmail.com)
 * @version 1.0
 */


public class ZathuraJavaEE_JPA_Prime_Web_Centric implements IZathuraTemplate,IZathuraGenerator {
	private VelocityEngine ve;
	private static final String primeCentric ;
	private  static Logger logPrime;
	// paquete virgen en el disco duro
	public String virginPackageInHd = new String();
	// propiedades de zathuraGenerator que usaran en toda la clase
	private Properties properties;
	private String webRootPath;

	static{
		primeCentric=GeneratorUtil.getPrimeCentricTemplates();
		logPrime=Logger.getLogger(ZathuraJavaEE_JPA_Prime_Web_Centric.class);
		
	}

	@Override
	public void toGenerate(MetaDataModel metaDataModel, String projectName,
			String folderProjectPath, Properties propiedades) {
		
		webRootPath=(propiedades.getProperty("webRootFolderPath"));
		properties=propiedades;
		String jpaPckName = propiedades.getProperty("jpaPckgName");
		Integer level= (Integer) propiedades.get("specificityLevel");
		doTemplate(folderProjectPath, metaDataModel,jpaPckName, projectName, level);
		copyLibrarys();

	}

	
	public void copyLibrarys(){
		String pathIndexJsp = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeCentric()+"index.jsp";
		String pathWebXml= GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeCentric()+"WEB-INF"+GeneratorUtil.slash;
		String generatorExtZathuraJavaEEWebPrimeCentricImages = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeCentric() + GeneratorUtil.slash + "images"
				+ GeneratorUtil.slash;
		String pathCss = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeCentric() + GeneratorUtil.slash + "css"
		+ GeneratorUtil.slash;
		String log4j = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeCentric() + GeneratorUtil.slash + "log4j"
				+ GeneratorUtil.slash;
		
		String pathHibernate= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimefaces()+"core-hibernate3.3"+GeneratorUtil.slash;
		String pathPrimeFaces= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimefaces()+"primeFaces3.2"+GeneratorUtil.slash;
		String pathLib= properties.getProperty("libFolderPath");
		
		// Copy Css
		GeneratorUtil.createFolder(webRootPath + "css");
		GeneratorUtil.copyFolder(pathCss, webRootPath + "css" + GeneratorUtil.slash);
		GeneratorUtil.copyFolder(pathWebXml,webRootPath+"WEB-INF"+GeneratorUtil.slash);
		//create folder images and insert .png
		GeneratorUtil.createFolder(webRootPath + "images");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebPrimeCentricImages, webRootPath + "images" + GeneratorUtil.slash);
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

			ve= new VelocityEngine();
			Properties properties =new Properties();
			properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			properties.setProperty("file.resource.loader.path", primeCentric);
			properties.setProperty("file.resource.loader.cache", "false");
			properties.setProperty("file.resource.loader.modificationCheckInterval", "2");
			ve.init(properties);
			
			VelocityContext context =new VelocityContext();

			//MetaDataModel dataModel = metaDataModel;
			//List<MetaData> list = dataModel.getTheMetaData();
			List<MetaData> list = metaDataModel.getTheMetaData();
			
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
					logPrime.error(e.getMessage());
				}
			} else {
				try {
					packageOriginal = jpaPckgName;

					int lastIndexOf2 = packageOriginal.lastIndexOf(".") + 1;
					modelName = jpaPckgName.substring(lastIndexOf2);

					int virginLastIndexOf = packageOriginal.lastIndexOf(".");
					virginPackage = packageOriginal.substring(0, virginLastIndexOf);
				} catch (Exception e) {
					logPrime.error(e.getMessage());
				}
			}

			/***
			 *  packageOriginal es el nombre del paquete donde se encuentra las entitys com.mauricio.demogenerator.model
			 *  package  es el path donde se generan las entitys /home/mauricio/Workspaces/zathura/DemoGenerator/srs/com/mauricio/demogenerator/model
			 *  proyectName DemoGenerator
			 *  domainName el nombre del dominio para este ejemplo seria com
			 *  modelName el este caso seria model
			 *  VirginPackage  com.mauricio.demogenerator
			 *  projectNameClass nombre del proyecto  como si fuera una clase  Ej:DemoGenerator
			 *  virginPackageInHd ruta del virginPackage  com/mauricio/demogenerator/
			 */
			String projectNameClass = (projectName.substring(0, 1)).toUpperCase() + projectName.substring(1, projectName.length());
			context.put("packageOriginal", packageOriginal);
			context.put("virginPackage", virginPackage);
			context.put("package", jpaPckgName);
			context.put("projectName", projectName);
			context.put("modelName", modelName);
			context.put("projectNameClass", projectNameClass);


			this.virginPackageInHd = GeneratorUtil.replaceAll(virginPackage, ".", GeneratorUtil.slash);
			//Contruye los folder del proyect (dataacces.dao,exception,model,modelo.control,model.dto,presentation.backendbean,presentation.bussinessDelegator,utilities)

			Utilities.getInstance().buildFolders(virginPackage, hdLocation, specificityLevel, packageOriginal, this.properties);
			Utilities.getInstance().biuldHashToGetIdValuesLengths(list);

			for (MetaData metaData : list) {

				List<String> imports = Utilities.getInstance().getRelatedClasses(metaData, metaDataModel);

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
				// generacion persistence.xml
				context.put("connectionUrl", EclipseGeneratorUtil.connectionUrl);
				context.put("connectionDriverClass", EclipseGeneratorUtil.connectionDriverClass);
				context.put("connectionUsername", EclipseGeneratorUtil.connectionUsername);
				context.put("connectionPassword", EclipseGeneratorUtil.connectionPassword);

				context.put("finalParamForView", stringBuilder.finalParamForView(list, metaData));
				context.put("finalParamForDtoUpdate", stringBuilder.finalParamForDtoUpdate(list, metaData));
				context.put("finalParamForDtoUpdateOnlyVariables", stringBuilder.finalParamForDtoUpdateOnlyVariables(list, metaData));
				
				// hidrata dates en el utilities
				context.put("finalParamForViewVariablesInList", stringBuilder.finalParamForViewVariablesInList(list, metaData));
				
				// abajo
				context.put("isFinalParamForViewDatesInList", Utilities.getInstance().isFinalParamForViewDatesInList());
				//abajo
				context.put("finalParamForViewDatesInList", Utilities.getInstance().dates);
				
				context.put("finalParamForIdForViewVariablesInList", stringBuilderForId.finalParamForIdForViewVariablesInList(list, metaData));
				//abajo
				context.put("isFinalParamForIdForViewDatesInList", Utilities.getInstance().isFinalParamForIdForViewDatesInList());
				//abajo
				
				context.put("finalParamForIdForViewDatesInList", Utilities.getInstance().datesId);
				context.put("finalParamForIdForViewClass", stringBuilderForId.finalParamForIdForViewClass(list, metaData));
				context.put("finalParamForIdClassAsVariablesAsString", stringBuilderForId.finalParamForIdClassAsVariablesAsString(list, metaData));
				context.put("finalParamForViewForSetsVariablesInList", stringBuilder.finalParamForViewForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdForDtoForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoForSetsVariablesInList(list, metaData));
				context.put("finalParamForDtoForSetsVariablesInList", stringBuilder.finalParamForDtoForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdForDtoInViewForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoInViewForSetsVariablesInList(list,metaData));
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
				context.put("dataModel", metaDataModel);

				doLogic(metaData, context, hdLocation, metaDataModel, modelName);
				doBackingBeans(metaData, context, hdLocation, metaDataModel);
				doDto(metaData, context, hdLocation, metaDataModel, modelName);
				doDao(metaData, context, hdLocation);
				doJsp(metaData, context, hdLocation, metaDataModel);
				
			}

			doPersitenceXml(metaDataModel, context, hdLocation);
			doEntityManager(metaDataModel, context, hdLocation);
			doDaoFactory(metaDataModel, context, hdLocation);
			doExceptions(context, hdLocation);
			doBusinessDelegator(context, hdLocation, metaDataModel);
			doUtilites(context, hdLocation, metaDataModel, modelName);
			doFacesConfig(metaDataModel, context, hdLocation);
			doJspInitialMenu(metaDataModel, context, hdLocation);
			doJspFacelets(context, hdLocation);
			
		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}





	}

	@Override
	public void doDao(MetaData metaData, VelocityContext context,
			String hdLocation) {
		try {

			logPrime.info("Begin IdaoPrime");
			Template iDaoPrime= ve.getTemplate("IDAOPrime.vm");
			StringWriter swIdaoPrime= new StringWriter();
			iDaoPrime.merge(context, swIdaoPrime);
			String path=hdLocation+virginPackageInHd+GeneratorUtil.slash+"dataaccess"+GeneratorUtil.slash+"dao"+GeneratorUtil.slash;
			FileWriter fwIdaoPrime = new FileWriter(path+"I"+metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwIdaoPrime = new BufferedWriter(fwIdaoPrime);
			bwIdaoPrime.write(swIdaoPrime.toString());
			bwIdaoPrime.close();
			fwIdaoPrime.close();
			logPrime.info("End IdaoPrime");

			logPrime.info("Begin  DaoPrime");
			Template daoPrime = ve.getTemplate("DAOPrime.vm");
			StringWriter swDaoPrime=new StringWriter();
			daoPrime.merge(context, swDaoPrime);
			FileWriter fwDaoPrime= new FileWriter(path+metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwDaoPrime = new BufferedWriter(fwDaoPrime);
			bwDaoPrime.write(swDaoPrime.toString());
			bwDaoPrime.close();
			fwDaoPrime.close();
			logPrime.info("End DaoPrime");

			JalopyCodeFormatter.formatJavaCodeFile(path+"I"+metaData.getRealClassName()+"DAO.java");
			JalopyCodeFormatter.formatJavaCodeFile(path+metaData.getRealClassName()+"DAO.java");



		} catch (Exception e) {
			logPrime.error(e.getMessage());

		}

	}

	@Override
	public void doPersitenceXml(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {
		try {

			logPrime.info("Begin persistence.xml");
			Template persistence= ve.getTemplate("persistence.xml.vm");
			StringWriter swPersistence= new StringWriter();
			persistence.merge(context, swPersistence);

			String path = hdLocation+"META-INF"+GeneratorUtil.slash;
			FileWriter fwPersistencePrime = new FileWriter(path+"persistence.xml");
			BufferedWriter bwPersistencePrime= new BufferedWriter(fwPersistencePrime);
			bwPersistencePrime.write(swPersistence.toString());
			bwPersistencePrime.close();
			fwPersistencePrime.close();
			logPrime.info("End persistence.xml");


		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doEntityManager(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {

		try {
			logPrime.info("Begin entityManagerHelper");
			Template entityManager = ve.getTemplate("EntityManagerHelper.vm");
			StringWriter swEntityManager = new StringWriter();
			entityManager.merge(context, swEntityManager);

			String path=hdLocation+virginPackageInHd+GeneratorUtil.slash+"dataaccess"+GeneratorUtil.slash+"entityManager"+GeneratorUtil.slash;
			FileWriter fwEntityManager= new FileWriter(path+"EntityManagerHelper.java");
			BufferedWriter bwEntityManager = new BufferedWriter(fwEntityManager);
			bwEntityManager.write(swEntityManager.toString());
			bwEntityManager.close();
			fwEntityManager.close();
			logPrime.info("End entityManagerHelper");

			JalopyCodeFormatter.formatJavaCodeFile(path+"entityManagerHelper.java");

		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doDaoFactory(MetaDataModel metaData, VelocityContext context,
			String hdLocation) {
		try {

			logPrime.info("Begin DaoFactory");
			Template daoFactory= ve.getTemplate("JPADaoFactory.vm");
			StringWriter swDaoFactory= new StringWriter();
			daoFactory.merge(context, swDaoFactory);

			String path=hdLocation+virginPackageInHd+GeneratorUtil.slash+"dataaccess"+GeneratorUtil.slash+"daoFactory"+GeneratorUtil.slash;
			FileWriter fwDaoFactory= new FileWriter(path+"JPADaoFactory.java");
			BufferedWriter bwDaoFactory= new BufferedWriter(fwDaoFactory);
			bwDaoFactory.write(swDaoFactory.toString());
			bwDaoFactory.close();
			fwDaoFactory.close();
			logPrime.info("End DaoFactory");

		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doLogic(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {

		try {

			logPrime.info("Begin ILogic Prime");
			Template iLogicPrime= ve.getTemplate("ILogicPrime.vm");
			StringWriter swILogicPrime = new StringWriter();
			iLogicPrime.merge(context, swILogicPrime);
			String path=hdLocation+virginPackageInHd+GeneratorUtil.slash+modelName+GeneratorUtil.slash+"control"+GeneratorUtil.slash;
			FileWriter fwILogicPrime = new FileWriter(path+"I"+metaData.getRealClassName()+"Logic.java");
			BufferedWriter bwILogicPrime= new BufferedWriter(fwILogicPrime);
			bwILogicPrime.write(swILogicPrime.toString());
			bwILogicPrime.close();
			fwILogicPrime.close();
			logPrime.info("End ILogic Prime");

			logPrime.info("Begin Logic Prime");
			Template logicPrime= ve.getTemplate("LogicPrime.vm");
			StringWriter swLogicPrime = new StringWriter();
			logicPrime.merge(context, swLogicPrime);
			FileWriter fwLogicPrime = new FileWriter(path+metaData.getRealClassName()+"Logic.java");
			BufferedWriter bwLogicPrime= new BufferedWriter(fwLogicPrime);
			bwLogicPrime.write(swLogicPrime.toString());
			bwLogicPrime.close();
			fwLogicPrime.close();
			logPrime.info("End Logic Prime");

			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "Logic.java");
			JalopyCodeFormatter.formatJavaCodeFile(path + "I" + metaData.getRealClassName() + "Logic.java");

		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}


	}

	@Override
	public void doBusinessDelegator(VelocityContext context, String hdLocation,
			MetaDataModel dataModel) {
		try {
			logPrime.info("Begin BusinessDelegate Prime");
			Template businesDelegate = ve.getTemplate("BusinessDelegatorViewPrime.vm");
			StringWriter swBusinessDelegate = new StringWriter();
			businesDelegate.merge(context, swBusinessDelegate);

			String path = hdLocation+virginPackageInHd+ GeneratorUtil.slash+"presentation"+GeneratorUtil.slash+"businessDelegate"+GeneratorUtil.slash;
			FileWriter fwBusinessDelegate = new FileWriter(path+"BusinessDelegatorView.java");
			BufferedWriter bwBusinessDelegate = new BufferedWriter(fwBusinessDelegate);
			bwBusinessDelegate.write(swBusinessDelegate.toString());
			bwBusinessDelegate.close();
			fwBusinessDelegate.close();
			logPrime.info("End BusinessDelegate Prime");
			JalopyCodeFormatter.formatJavaCodeFile(path+"BusinessDelegatorView.java");

		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doJsp(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {
		
		try {
			
			logPrime.info("Begin xhtml Prime");
			Template xhtmlTemplate= ve.getTemplate("JSPPrime.vm");
			StringWriter swXhtml= new StringWriter();
			xhtmlTemplate.merge(context, swXhtml);
			
			String path=properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			FileWriter fwXhtml = new FileWriter(path+metaData.getRealClassNameAsVariable()+".xhtml");
			BufferedWriter bwXhtml = new BufferedWriter(fwXhtml);
			bwXhtml.write(swXhtml.toString());
			bwXhtml.close();
			swXhtml.close();
			
			logPrime.info("Begin dataTable Prime");
			Template dataTableTemplate = ve.getTemplate("JSPdataTablesPrime.vm");
			StringWriter dataTableSw= new StringWriter();
			dataTableTemplate.merge(context, dataTableSw);
			
			FileWriter dataTableFw= new FileWriter(path+metaData.getRealClassNameAsVariable()+"ListDataTable.xhtml");
			BufferedWriter dataTableBw = new BufferedWriter(dataTableFw);
			dataTableBw.write(dataTableSw.toString());
			dataTableBw.close();
			dataTableFw.close();
			
			logPrime.info("End ListDataTable Prime");
			
			logPrime.info("Begin DataTableEditable Prime");
			Template dataTableEditableTemplate= ve.getTemplate("JSPdataTableEditablePrime.vm");
			StringWriter swDataTableEditable = new StringWriter();
			dataTableEditableTemplate.merge(context, swDataTableEditable);
			
			FileWriter fwDataTableEditable= new FileWriter(path+metaData.getRealClassNameAsVariable()+"ListDataTableEditable.xhtml");
			BufferedWriter bwDataTableEditable = new BufferedWriter(fwDataTableEditable);
			bwDataTableEditable.write(swDataTableEditable.toString());
			bwDataTableEditable.close();
			fwDataTableEditable.close();
			
			logPrime.info("End DataTableEditable prime");
			
			

			Utilities.getInstance().datesJSP = null;
			Utilities.getInstance().datesIdJSP = null;

			logPrime.info("End xhtml Prime");
			
			
		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doJspInitialMenu(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {
		try {
			
			logPrime.info("Begin JspInitialMenu PrimeFaces");
			
			Template jspInitial= ve.getTemplate("JSPinitialMenu.vm");
			StringWriter swInitialMenu= new StringWriter();
			jspInitial.merge(context, swInitialMenu);
			
			String path=properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			FileWriter fwInitialMenu= new FileWriter(path+"initialMenu.xhtml");
			BufferedWriter bwInitialMenu= new BufferedWriter(fwInitialMenu);
			bwInitialMenu.write(swInitialMenu.toString());
			bwInitialMenu.close();
			fwInitialMenu.close();
			
			logPrime.info("End JspInitialMenu");
			
		} catch (Exception e) {
			logPrime.info(e.getMessage());
		}

	}

	@Override
	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context,
			String hdLocation) {
		
		try {
			logPrime.info("Begin FacesConfig");
			Template facesConfigPrimeTemplate = ve.getTemplate("faces-configPrime.xml.vm");
			StringWriter swFacesConfig= new StringWriter();
			facesConfigPrimeTemplate.merge(context, swFacesConfig);
			
			String path = properties.getProperty("webRootFolderPath") + "WEB-INF" + GeneratorUtil.slash ;
			
			FileWriter fwFacesConfig= new FileWriter(path+"faces-config.xml");
			BufferedWriter bwFacesConfig = new BufferedWriter(fwFacesConfig);
			bwFacesConfig.write(swFacesConfig.toString());
			bwFacesConfig.close();
			fwFacesConfig.close();
			logPrime.info("End FacesConfig");
			
		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doDto(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {
		try {
			logPrime.info("Begin Dto Prime ");
			Template dtoPrime= ve.getTemplate("DtoPrime.vm");
			StringWriter swDtoPrime = new StringWriter();
			dtoPrime.merge(context, swDtoPrime);

			String path = hdLocation+virginPackageInHd+GeneratorUtil.slash+modelName+GeneratorUtil.slash+"dto"+GeneratorUtil.slash;
			FileWriter fwDtoPrime = new FileWriter(path+metaData.getRealClassName()+"DTO.java");
			BufferedWriter bwDtoPrime = new BufferedWriter(fwDtoPrime);
			bwDtoPrime.write(swDtoPrime.toString());
			bwDtoPrime.close();
			fwDtoPrime.close();
			logPrime.info("End Dto Prime ");

			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "DTO.java");

		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doExceptions(VelocityContext context, String hdLocation) {
		try {

			logPrime.info("Begin Exception Prime");
			Template exceptionTemplate = ve.getTemplate("ZMessManager.vm");
			StringWriter swException= new StringWriter();
			exceptionTemplate.merge(context, swException);
			String path= hdLocation + virginPackageInHd+GeneratorUtil.slash+"exceptions"+GeneratorUtil.slash;
			FileWriter fwException = new FileWriter(path+"ZMessManager.java");
			BufferedWriter bwException = new BufferedWriter(fwException);
			bwException.write(swException.toString());
			bwException.close();
			fwException.close();

			JalopyCodeFormatter.formatJavaCodeFile(path+"ZMessManager.java");



		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doUtilites(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {
			try {
				logPrime.info("Begin Utilities Prime");
				Template utilities= ve.getTemplate("Utilities.vm");
				StringWriter swUtilities = new StringWriter();
				utilities.merge(context, swUtilities);
				
				String path = hdLocation+virginPackageInHd+GeneratorUtil.slash+"utilities"+GeneratorUtil.slash;
				FileWriter fwUtilities = new FileWriter(path+"Utilities.java");
				BufferedWriter bwUtilities = new BufferedWriter(fwUtilities);
				bwUtilities.write(swUtilities.toString());
				bwUtilities.close();
				fwUtilities.close();
				
				Template facesUtilsTemplate= ve.getTemplate("FacesUtils.vm");
				StringWriter swFacesUtils= new StringWriter();
				facesUtilsTemplate.merge(context, swFacesUtils);
				
				FileWriter fwFacesUtils= new FileWriter(path+"FacesUtils.java");
				BufferedWriter bfFacesUtils= new BufferedWriter(fwFacesUtils);
				bfFacesUtils.write(swFacesUtils.toString());
				bfFacesUtils.close();
				fwFacesUtils.close();
				
				JalopyCodeFormatter.formatJavaCodeFile(path + "Utilites.java");
				JalopyCodeFormatter.formatJavaCodeFile(path + "FacesUtils.java");
				
				logPrime.info("End Utilities Prime");
				
			} catch (Exception e) {
				logPrime.error(e.getMessage());
				
			}

	}

	@Override
	public void doJspFacelets(VelocityContext context, String hdLocation) {
		try {
			
			String path =  properties.getProperty("webRootFolderPath") + "WEB-INF" + GeneratorUtil.slash + "facelets" + GeneratorUtil.slash;
			
			logPrime.info("Begin Header PrimeFaces");
			Template templateHeader = ve.getTemplate("JSPheader.vm");
			StringWriter swHeader = new StringWriter();
			templateHeader.merge(context, swHeader);
			FileWriter fwHeader = new FileWriter(path+ "header.jspx");
			BufferedWriter bwHeader = new BufferedWriter(fwHeader);
			bwHeader.write(swHeader.toString());
			bwHeader.close();
			fwHeader.close();
			logPrime.info("End Header PrimeFaces");
			
			logPrime.info("Begin Footer PrimeFaces");
			Template templateFooter = ve.getTemplate("JSPfooter.vm");
			StringWriter swFooter = new StringWriter();
			templateFooter.merge(context, swFooter);
			FileWriter fwFooter = new FileWriter(path + "footer.jspx");
			BufferedWriter bwFooter = new BufferedWriter(fwFooter);
			bwFooter.write(swFooter.toString());
			bwFooter.close();
			fwFooter.close();
			logPrime.info("Begin Footer PrimeFaces");
			
			logPrime.info("Begin Footer initialMenu");
			Template footerInitialMenu = ve.getTemplate("footerInitialMenu.vm");
			StringWriter swFooterInitialMenu = new StringWriter();
			footerInitialMenu.merge(context, swFooterInitialMenu);
			FileWriter fwFooterInitialMenu = new FileWriter(path+ "footerInitialMenu.jspx");
			BufferedWriter bwFooterInitialMenu = new BufferedWriter(fwFooterInitialMenu);
			bwFooterInitialMenu.write(swFooterInitialMenu.toString());
			bwFooterInitialMenu.close();
			fwFooterInitialMenu.close();
			logPrime.info("Close Footer initialMenu");
			
			String path2= properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			
			logPrime.info("Begin CommonsColumnsConten");
			Template templateCommonsColumns = ve.getTemplate("CommonColumnsContent.vm");
			StringWriter swCommonsColumns = new StringWriter();
			templateCommonsColumns.merge(context, swCommonsColumns);
			FileWriter fwCommonsColumns = new FileWriter(path2+"CommonColumnsContent.xhtml");
			BufferedWriter bwCommonsColumns = new BufferedWriter(fwCommonsColumns);
			bwCommonsColumns.write(swCommonsColumns.toString());
			bwCommonsColumns.close();
			fwCommonsColumns.close();
			logPrime.info("End CommonsColumnsConten");
			
			logPrime.info("Begin CommonLayout");
			Template templateCommonLayout = ve.getTemplate("CommonLayout.vm");
			StringWriter swCommonLayout = new StringWriter();
			templateCommonLayout.merge(context, swCommonLayout);
			FileWriter fwCommonLayout = new FileWriter(path2+"CommonLayout.xhtml" );
			BufferedWriter bwCommonLayout = new BufferedWriter(fwCommonLayout);
			bwCommonLayout.write(swCommonLayout.toString());
			bwCommonLayout.close();
			fwCommonLayout.close();
			logPrime.info("End CommonLayout");
			
			
		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

	@Override
	public void doBackingBeans(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {

		try {
			logPrime.info("Begin BackEndBean Prime");
			Template backEndBean= ve.getTemplate("BackingBeansPrime.vm");
			StringWriter swBackEndBean = new StringWriter();
			backEndBean.merge(context, swBackEndBean);
			String path=hdLocation+virginPackageInHd+GeneratorUtil.slash+"presentation"+GeneratorUtil.slash+"backingBeans"+GeneratorUtil.slash;
			FileWriter fwBackEndBean = new FileWriter(path+metaData.getRealClassName()+"View.java");
			BufferedWriter bwBackEndBean= new BufferedWriter(fwBackEndBean);
			bwBackEndBean.write(swBackEndBean.toString());
			bwBackEndBean.close();
			fwBackEndBean.close();
			logPrime.info("End BackEndBean Prime");
			
			JalopyCodeFormatter.formatJavaCodeFile(path+metaData.getRealClassName()+"View.java");
			Utilities.getInstance().dates = null;
			Utilities.getInstance().datesId = null;

		} catch (Exception e) {
			logPrime.error(e.getMessage());
		}

	}

}
