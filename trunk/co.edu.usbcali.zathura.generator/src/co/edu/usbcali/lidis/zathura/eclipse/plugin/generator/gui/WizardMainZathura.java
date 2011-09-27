package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;


import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.RunningGeneration;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ZathuraGeneratorLog;
import co.edu.usbcali.lidis.zathura.swt.utilities.ResourceManager;
// TODO: Auto-generated Javadoc

/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see Wizard
 */
public class WizardMainZathura extends Wizard {
	
	//WizardPage
	/** The wizard generator chose. */
	public WizardPageChooseGenerator wizardGeneratorChose;
	
	//WizardPage Generator
	/** The wizard select db connection. */
	public WizardPageSelectDBConnection wizardSelectDBConnection;
	
	/** The wizard database connection. */
	public WizardPageDatabaseConnection wizardDatabaseConnection;
	
	/** The wizard select tables. */
	public WizardPageSelectTables wizardSelectTables;
	
	/** The wizard choose source folder and package. */
	public WizardPageChooseSourceFolderAndPackage wizardChooseSourceFolderAndPackage;
	
	public WizardPageChooseFrameworkTire wizardPageChooseFrameworkTire;

	/**
	 * The Constructor.
	 */
	public WizardMainZathura() {
		super();
		setWindowTitle("Zathura Code Generator V3.0 - Powered By Vortexbird www.vortexbird.com");
		setDefaultPageImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/balvardi-Robotic7070.jpg"));
		EclipseGeneratorUtil.reset();
		EclipseGeneratorUtil.wizardMain=this;		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();
		//Creo el wizard y lo asigno al administrado
		
		wizardSelectDBConnection=new WizardPageSelectDBConnection();
		addPage(wizardSelectDBConnection);
		
		wizardSelectTables=new WizardPageSelectTables();
		addPage(wizardSelectTables);
		
		wizardChooseSourceFolderAndPackage=new WizardPageChooseSourceFolderAndPackage();
		addPage(wizardChooseSourceFolderAndPackage);
		
		
		wizardPageChooseFrameworkTire =new WizardPageChooseFrameworkTire();
		addPage(wizardPageChooseFrameworkTire);

		//wizardGeneratorChose=new WizardPageChooseGenerator();
		//addPage(wizardGeneratorChose);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			
			ProgressMonitorDialog progressMonitorDialogReverseEngineering=new ProgressMonitorDialog(getShell());
			progressMonitorDialogReverseEngineering.run(true, true,new co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.RunningGenerationReverseEngineering(getShell()));
			
			ProgressMonitorDialog progressMonitorDialogGeneration=new ProgressMonitorDialog(getShell());
			progressMonitorDialogGeneration.run(true, true,new RunningGeneration(getShell()));
			
			
			
			
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
	 * Se usa para saber si se puede finalizar el Wizard.
	 *
	 * @return true, if can finish
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
