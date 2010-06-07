package zTest;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File buildFile = new File("build.xml");
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
		
		//compile
		File buildFile2 = new File("buildCompile.xml");
		Project p2 = new Project();
		p2.setUserProperty("ant.file", buildFile2.getAbsolutePath());		
		DefaultLogger consoleLogger2 = new DefaultLogger();
		consoleLogger2.setErrorPrintStream(System.err);
		consoleLogger2.setOutputPrintStream(System.out);
		consoleLogger2.setMessageOutputLevel(Project.MSG_INFO);
		p2.addBuildListener(consoleLogger2);

		try {
			p2.fireBuildStarted();
			p2.init();
			ProjectHelper helper2 = ProjectHelper.getProjectHelper();
			p2.addReference("ant.projectHelper", helper2);
			helper2.parse(p2, buildFile2);
			p2.executeTarget(p2.getDefaultTarget());
			p2.fireBuildFinished(null);
		} catch (BuildException e) {
			p2.fireBuildFinished(e);
		}		

		
	}

}
