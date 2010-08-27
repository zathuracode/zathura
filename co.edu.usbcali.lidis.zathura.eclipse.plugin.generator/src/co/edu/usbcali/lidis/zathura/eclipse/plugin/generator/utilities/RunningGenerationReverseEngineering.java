package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class RunningGenerationReverseEngineering implements IRunnableWithProgress {
	  
	  /**
	   * RunningGeneration constructor
	   * 
	   */
	  public RunningGenerationReverseEngineering() {
		  
	  }

	  /**
	   * Runs the long running operation
	   * 
	   * @param monitor the progress monitor
	   */
	  public void run(IProgressMonitor monitor) throws InvocationTargetException,InterruptedException {
		  	monitor.setTaskName("Generating Artifacts");
		  	monitor.beginTask("Generation in progress...",IProgressMonitor.UNKNOWN);
		    	try {
		    	
		    		//Genera los entity originales
		    		EclipseGeneratorUtil.generateJPAReverseEngineering();
		    		
		    		//OJO
		    		//Se generan los temporales y de estos se lee la metaData siempre
		    		EclipseGeneratorUtil.generateJPAReverseEngineeringTMP();
		    			
		    		
					
					monitor.subTask("Refresh "+EclipseGeneratorUtil.projectName+" project...");
					EclipseGeneratorUtil.project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					
					monitor.subTask("Building "+EclipseGeneratorUtil.projectName+" project...");
					EclipseGeneratorUtil.project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);	
					
					
				} catch (CoreException e) {
					monitor.setCanceled(true);
					e.printStackTrace();
					ZathuraGeneratorLog.logError(e);	
					throw new InterruptedException(e.getMessage());
				}
			monitor.done();
			if (monitor.isCanceled()){
				throw new InterruptedException("The generation was cancelled");
			}
	  }
}
