package co.edu.usbcali.lidis.zathura.jpareverse.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import co.edu.usbcali.lidis.zathura.jpareverse.utilities.ZathuraJarLoader;
import co.edu.usbcali.lidis.zathura.jpareverse.utilities.JPAReverseEngineeringUtil;

/**
 * 
 * @author zathuratools
 *
 */

public class ZathuraJPAReverseEngineering implements IZathuraJPAReverseEngineering {

	private static Logger log = Logger.getLogger(ZathuraJPAReverseEngineering.class);

	private final static String reverseTemplatesPath = JPAReverseEngineeringUtil.getReverseTemplates();

	private final static String tempFiles = JPAReverseEngineeringUtil.getTempFilesPath();

	private String connectionDriverClass;
	private String connectionUrl;
	private String connectionUsername;
	private String defaultSchema;
	private String connectionPassword;
	private String companyDomainName;
	private String companyDomainNameForPojoLocation;
	private String connectionDriverJarPath;
	private String destinationDirectory;

	private String matchSchemaForTables;
	private List<String> tablesList;

	public void makePojosJPA_V1_0(Properties connectionProperties, List<String> tables) {
		
		log.info("Begin ZathuraReverseMappingTool (Pojo Making & compilation)");

		connectionDriverClass = connectionProperties.getProperty("connectionDriverClass");
		connectionUrl = connectionProperties.getProperty("connectionUrl");
		defaultSchema = connectionProperties.getProperty("defaultSchema");
		connectionUsername = connectionProperties.getProperty("connectionUsername");
		connectionPassword = connectionProperties.getProperty("connectionPassword");
		companyDomainName = connectionProperties.getProperty("companyDomainName");
		companyDomainNameForPojoLocation = JPAReverseEngineeringUtil.fixDomain(companyDomainName);
		connectionDriverJarPath = connectionProperties.getProperty("connectionDriverJarPath");
		destinationDirectory = connectionProperties.getProperty("destinationDirectory");
		matchSchemaForTables = connectionProperties.getProperty("matchSchemaForTables");

		this.tablesList = tables;
		
		//Llama los templates que generan los archivos de configuracion para HibernateTools
		doTemplate();

		try {
			ZathuraJarLoader.loadJar2(connectionDriverJarPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//LLama el proceso de ANT con los archivos generados
		callAntProcess();
		
		log.info("End ZathuraReverseMappingTool (Pojo Making & compilation)");
	}

	private void doTemplate() {
		try {
			Properties properties = new Properties();

			properties.setProperty("file.resource.loader.description","Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			properties.setProperty("file.resource.loader.path",reverseTemplatesPath);
			properties.setProperty("file.resource.loader.cache", "false");
			properties.setProperty("file.resource.loader.modificationCheckInterval", "2");

			Velocity.init(properties);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		VelocityContext context = new VelocityContext();

		context.put("connectionDriverClass", connectionDriverClass);
		context.put("connectionUrl", connectionUrl);
		context.put("defaultSchema", defaultSchema);
		context.put("connectionUsername", connectionUsername);
		context.put("connectionPassword", connectionPassword);
		context.put("companyDomainName", companyDomainName);
		context.put("companyDomainNameForPojoLocation",companyDomainNameForPojoLocation);
		context.put("matchSchemaForTables", matchSchemaForTables);
		context.put("connectionDriverJarPath", connectionDriverJarPath);
		context.put("destinationDirectory", destinationDirectory);		
		context.put("tablesList", tablesList);		
		context.put("isTableList", JPAReverseEngineeringUtil.validationsList(tablesList));

		
		doCfg(context);
		doBuild(context);
		doRevEng(context);

	}

	private void doCfg(VelocityContext context) {
		log.info("Begin doCfg");
		try {
			Template cfg = null;
			StringWriter swCfg = new StringWriter();

			try {
				cfg = Velocity.getTemplate("hibernate.cfg.xml.vm");
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

			cfg.merge(context, swCfg);

			FileWriter fstream = new FileWriter(tempFiles + "hibernate.cfg.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swCfg.toString());
			// Close the output stream
			out.close();

			log.info("End doCfg");

		} catch (Exception e) {
			log.error("Error doCfg",e);
		}
	}

	/**
	 * 
	 * @param context
	 */
	private void doBuild(VelocityContext context) {
		log.info("Begin doBuild");
		try {
			Template build = null;
			StringWriter swBuild = new StringWriter();

			try {
				build = Velocity.getTemplate("build.xml.vm");
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

			build.merge(context, swBuild);

			FileWriter fstream = new FileWriter(tempFiles + "build.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swBuild.toString());
			// Close the output stream
			out.close();

			log.info("End doBuild");

		} catch (Exception e) {
			log.error("Error doBuild",e);
		}
	}

	/**
	 * 
	 * @param context
	 */
	private void doRevEng(VelocityContext context) {
		log.info("Begin doRevEng");
		try {
			Template revEng = null;
			StringWriter swRevEng = new StringWriter();

			try {
				revEng = Velocity.getTemplate("hibernate.reveng.xml.vm");
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

			revEng.merge(context, swRevEng);

			FileWriter fstream = new FileWriter(tempFiles
					+ "hibernate.reveng.xml");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swRevEng.toString());
			// Close the output stream
			out.close();

			log.info("End doRevEng");

		} catch (Exception e) {
			log.error("Error doRevEng",e);
		}
	}

	/**
	 * 
	 */
	public static void callAntProcess() {
		log.info("Begin Ant");
			File buildFile = new File(JPAReverseEngineeringUtil.getTempFileBuildPath());
			Project p = new Project();
			p.setUserProperty("ant.file", buildFile.getAbsolutePath());
			DefaultLogger consoleLogger = new DefaultLogger();
			consoleLogger.setErrorPrintStream(System.err);
			consoleLogger.setOutputPrintStream(System.out);
			consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
			p.addBuildListener(consoleLogger);
	
			try {
				p.fireBuildStarted();
				p.init();
				ProjectHelper helper = ProjectHelper.getProjectHelper();
				p.addReference("ant.projectHelper", helper);
				helper.parse(p, buildFile);
				p.executeTarget(p.getDefaultTarget());
				p.fireBuildFinished(null);
			} catch (BuildException e) {
				p.fireBuildFinished(e);
			}
		log.info("End Ant");
	}

}
