package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import com.swtdesigner.ResourceManager;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.RunningGeneration;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ZathuraGeneratorLog;
/**
 * Zathura Generator
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see Wizard
 */
public class WizardMain extends Wizard {
	
	//WizardPage
	public WizardChooseGenerator wizardGeneratorChose;
	
	//WizardPage Generator
	public WizardSelectDBConnection wizardSelectDBConnection;
	public WizardDatabaseConnection wizardDatabaseConnection;
	public WizardSelectTables wizardSelectTables;
	public WizardChooseSourceFolderAndPackage wizardChooseSourceFolderAndPackage;
	
	


	public WizardMain() {
		super();
		setWindowTitle("Zathura Code Generator V2.1.1");
		setDefaultPageImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/balvardi-Robotic7070.jpg"));
		EclipseGeneratorUtil.reset();
		EclipseGeneratorUtil.wizardMain=this;		
	}
	
	@Override
	public void addPages() {
		super.addPages();
		//Creo el wizard y lo asigno al administrado
		
		wizardSelectDBConnection=new WizardSelectDBConnection();
		addPage(wizardSelectDBConnection);
		
		//wizardDatabaseConnection=new WizardDatabaseConnection();
		//addPage(wizardDatabaseConnection);
		
		wizardSelectTables=new WizardSelectTables();
		addPage(wizardSelectTables);
		
		wizardChooseSourceFolderAndPackage=new WizardChooseSourceFolderAndPackage();
		addPage(wizardChooseSourceFolderAndPackage);		
		
		wizardGeneratorChose=new WizardChooseGenerator();
		addPage(wizardGeneratorChose);
		
		//wizardGenerationProcessProgress=new WizardGenerationProcessProgress();
		//addPage(wizardGenerationProcessProgress);
		
	}

	@Override
	public boolean performFinish() {
		try {
			
			ProgressMonitorDialog progressMonitorDialogReverseEngineering=new ProgressMonitorDialog(getShell());
			progressMonitorDialogReverseEngineering.run(true, true,new co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.RunningGenerationReverseEngineering());
			
			ProgressMonitorDialog progressMonitorDialogGeneration=new ProgressMonitorDialog(getShell());
			progressMonitorDialogGeneration.run(true, true,new RunningGeneration());
			
			
		} catch (InvocationTargetException e) {
          MessageDialog.openError(getShell(), "Error", e.getMessage());
          ZathuraGeneratorLog.logError(e);
        } catch (InterruptedException e) {
          MessageDialog.openInformation(getShell(), "Cancelled", e.getMessage());
          ZathuraGeneratorLog.logError(e);
        }			
		return true;
	}
	
	/**
	 * Se usa para saber si se puede finalizar el Wizard
	 */
	@Override
	public boolean canFinish(){
		IWizardPage page = getContainer().getCurrentPage();
		if(page == wizardGeneratorChose){
			return wizardGeneratorChose.isPageComplete();
		}
		return false;
	}

}
