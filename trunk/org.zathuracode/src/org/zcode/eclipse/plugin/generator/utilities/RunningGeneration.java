package org.zcode.eclipse.plugin.generator.utilities;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.zcode.generator.exceptions.GeneratorNotFoundException;
import org.zcode.metadata.exceptions.MetaDataReaderNotFoundException;


// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see IRunnableWithProgress
 */
public class RunningGeneration implements IRunnableWithProgress {
	
	/** The shell. */
	private org.eclipse.swt.widgets.Shell shell;

	/**
	 * RunningGeneration constructor.
	 *
	 * @param shell the shell
	 */
	public RunningGeneration(Shell shell) {
		this.shell=shell;
	}

	/**
	 * Runs the long running operation.
	 *
	 * @param monitor the progress monitor
	 * @throws InvocationTargetException the invocation target exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		monitor.setTaskName("Generating Artifacts");
		monitor.beginTask("Generation in progress...", IProgressMonitor.UNKNOWN);
		try {
			EclipseGeneratorUtil.generate();

			monitor.subTask("Refresh " + EclipseGeneratorUtil.projectName + " project...");
			EclipseGeneratorUtil.project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			
			 //TODO mirar que comando se puede ejecutar para organizar los import
			
			monitor.subTask("Building " + EclipseGeneratorUtil.projectName + " project...");
			EclipseGeneratorUtil.project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
			
			
			
			//monitor.subTask("Building " + EclipseGeneratorUtil.projectName + " project...");
			
			
			
		} catch (MetaDataReaderNotFoundException e) {
			monitor.setCanceled(true);
			ZathuraGeneratorLog.logError(e);
			MessageDialog.openError(getShell(), "Error","The generation was cancellede MetaDataReaderNotFoundException"+e.getMessage());
			throw new InterruptedException("The generation was cancelled:"+e.getMessage());
		} catch (GeneratorNotFoundException e) {
			monitor.setCanceled(true);
			ZathuraGeneratorLog.logError(e);
			MessageDialog.openError(getShell(), "Error","The generation was cancellede GeneratorNotFoundException"+e.getMessage());
			throw new InterruptedException("The generation was cancelled:"+e.getMessage());
		} catch (CoreException e) {
			monitor.setCanceled(true);
			ZathuraGeneratorLog.logError(e);
			MessageDialog.openError(getShell(), "Error","The generation was cancellede CoreException"+e.getMessage());
			throw new InterruptedException("The generation was cancelled:"+e.getMessage());
		}catch (Exception e) {
			monitor.setCanceled(true);
			ZathuraGeneratorLog.logError(e);
			MessageDialog.openError(getShell(), "Error","The generation was cancellede Exception"+e.getMessage());
			throw new InterruptedException("The generation was cancelled:"+e.getMessage());
		}
		monitor.done();
		if (monitor.isCanceled()) {
			throw new InterruptedException("The generation was cancelled");
		}
	}
	
	/**
	 * Gets the shell.
	 *
	 * @return the shell
	 */
	public org.eclipse.swt.widgets.Shell getShell() {
		return shell;
	}

	/**
	 * Sets the shell.
	 *
	 * @param shell the shell
	 */
	public void setShell(org.eclipse.swt.widgets.Shell shell) {
		this.shell = shell;
	}
}
