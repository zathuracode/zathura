package co.edu.usbcali.lidis.zathura.eclipse.gui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import co.edu.usbcali.lidis.zathura.eclipse.utilities.RunningGeneration;
import co.edu.usbcali.lidis.zathura.eclipse.utilities.EclipseUtil;

public class WizardMain extends Wizard {
	
	//WizardPage
	private WizardChooseGenerator wizardGeneratorChose;
	private WizardChooseMetaData wizardMetaDataChose;
	private WizardEntitySearch wizardEntitySearch;
	private WizardChoosePath wizardPathChose;
	//private WizardGenerationProcessProgress wizardGenerationProcessProgress;


	public WizardMain() {
		super();
		setWindowTitle("LIDIS Zathura Code Generator V2.0");
		EclipseUtil.wizardMain=this;
	}
	
	@Override
	public void addPages() {
		super.addPages();
		//Creo el wizard y lo asigno al administrado
		
		
		wizardGeneratorChose=new WizardChooseGenerator();
		addPage(wizardGeneratorChose);
		
		wizardMetaDataChose=new WizardChooseMetaData();
		addPage(wizardMetaDataChose);
		
		wizardEntitySearch=new WizardEntitySearch();
		addPage(wizardEntitySearch);
		
		wizardPathChose=new WizardChoosePath();
		addPage(wizardPathChose);
		
		//wizardGenerationProcessProgress=new WizardGenerationProcessProgress();
		//addPage(wizardGenerationProcessProgress);
		
	}

	@Override
	public boolean performFinish() {
		try {			
			ProgressMonitorDialog p=new ProgressMonitorDialog(getShell());
			p.run(true, true,new RunningGeneration());
		} catch (InvocationTargetException e) {
          MessageDialog.openError(getShell(), "Error", e.getMessage());
        } catch (InterruptedException e) {
          MessageDialog.openInformation(getShell(), "Cancelled", e.getMessage());
        }			
		return true;
	}
	
	/**
	 * Se usa para saber si se puede finalizar el Wizard
	 */
	@Override
	public boolean canFinish(){
		IWizardPage page = getContainer().getCurrentPage();
		if(page == wizardPathChose){
			return wizardPathChose.isPageComplete();
		}
		return false;
	}

}
