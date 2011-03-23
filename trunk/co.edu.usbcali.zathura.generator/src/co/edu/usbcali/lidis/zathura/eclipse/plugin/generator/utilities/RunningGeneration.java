package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;

/**
 * Zathura Generator
 * 
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see IRunnableWithProgress
 */
public class RunningGeneration implements IRunnableWithProgress {
	
	private org.eclipse.swt.widgets.Shell shell;

	/**
	 * RunningGeneration constructor
	 * 
	 */
	public RunningGeneration(Shell shell) {
		this.shell=shell;
	}

	/**
	 * Runs the long running operation
	 * 
	 * @param monitor
	 *            the progress monitor
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		monitor.setTaskName("Generating Artifacts");
		monitor.beginTask("Generation in progress...", IProgressMonitor.UNKNOWN);
		try {
			EclipseGeneratorUtil.generate();

			monitor.subTask("Refresh " + EclipseGeneratorUtil.projectName + " project...");
			EclipseGeneratorUtil.project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			
			monitor.subTask("Building " + EclipseGeneratorUtil.projectName + " project...");
			EclipseGeneratorUtil.project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
			
			//monitor.subTask("Building " + EclipseGeneratorUtil.projectName + " project...");
			
			
			
		} catch (MetaDataReaderNotFoundException e) {
			monitor.setCanceled(true);
			ZathuraGeneratorLog.logError(e);
			MessageDialog.openError(getShell(), "Error",e.getMessage());
			throw new InterruptedException(e.getMessage());
		} catch (GeneratorNotFoundException e) {
			monitor.setCanceled(true);
			ZathuraGeneratorLog.logError(e);
			MessageDialog.openError(getShell(), "Error",e.getMessage());
			throw new InterruptedException(e.getMessage());
		} catch (CoreException e) {
			monitor.setCanceled(true);
			ZathuraGeneratorLog.logError(e);
			MessageDialog.openError(getShell(), "Error",e.getMessage());
			throw new InterruptedException(e.getMessage());
		}
		monitor.done();
		if (monitor.isCanceled()) {
			throw new InterruptedException("The generation was cancelled");
		}
	}
	
	public org.eclipse.swt.widgets.Shell getShell() {
		return shell;
	}

	public void setShell(org.eclipse.swt.widgets.Shell shell) {
		this.shell = shell;
	}
}
