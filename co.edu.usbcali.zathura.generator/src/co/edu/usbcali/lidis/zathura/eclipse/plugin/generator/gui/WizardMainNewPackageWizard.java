package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.wizards.NewPackageWizardPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.swt.utilities.ResourceManager;

// TODO: Auto-generated Javadoc
/**
 * The Class WizardMainNewPackageWizard.
 */
public class WizardMainNewPackageWizard extends Wizard {

	
	/** The i project. */
	private IProject iProject;
	
	/** The new package wizard page. */
	private NewPackageWizardPage newPackageWizardPage=null;
	
	
	/**
	 * The Constructor.
	 */
	public WizardMainNewPackageWizard() {
		super();
		
		setWindowTitle("Zathura Code Generator V2.1.1 - Powered By AmaziliaSource www.vortexbird.com");
		setDefaultPageImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/balvardi-Robotic7070.jpg"));
		newPackageWizardPage=new NewPackageWizardPage();
		newPackageWizardPage.setPageComplete(false);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {		

		addPage(newPackageWizardPage);
		newPackageWizardPage.setPageComplete(false);
		newPackageWizardPage.setPackageText("",true);
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			if(newPackageWizardPage.getPackageText().trim().equals("")==true){
				return false;
			}
				
				
			newPackageWizardPage.createPackage(null);
			return true;
		} catch (CoreException e) {
			MessageDialog.openError(getShell(),"Error",e.getMessage());
		} catch (InterruptedException e) {
			MessageDialog.openError(getShell(),"Error",e.getMessage());
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		IWizardPage page = getContainer().getCurrentPage();
		if(page == newPackageWizardPage && newPackageWizardPage.isPageComplete()==true){
			return true;
		}
		return false;
	}
	
	


	/**
	 * Gets the new package wizard page.
	 *
	 * @return the newPackageWizardPage
	 */
	public NewPackageWizardPage getNewPackageWizardPage() {
		return newPackageWizardPage;
	}


	/**
	 * Sets the new package wizard page.
	 *
	 * @param newPackageWizardPage the newPackageWizardPage to set
	 */
	public void setNewPackageWizardPage(NewPackageWizardPage newPackageWizardPage) {
		this.newPackageWizardPage = newPackageWizardPage;
	}


	/**
	 * Geti project.
	 *
	 * @return the i project
	 */
	public IProject getiProject() {
		return iProject;
	}


	/**
	 * Seti project.
	 *
	 * @param iProject the i project
	 */
	public void setiProject(IProject iProject) {
		this.iProject = iProject;
	}
	
	

}
