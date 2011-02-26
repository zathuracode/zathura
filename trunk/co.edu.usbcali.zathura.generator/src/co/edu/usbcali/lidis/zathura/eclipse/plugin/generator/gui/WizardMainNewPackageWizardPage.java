package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import org.eclipse.jdt.ui.wizards.NewPackageWizardPage;
import org.eclipse.jface.wizard.Wizard;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.swt.utilities.ResourceManager;

public class WizardMainNewPackageWizardPage extends Wizard {

	NewPackageWizardPage newPackageWizardPage=null;
	public WizardMainNewPackageWizardPage() {
		super();
		setWindowTitle("Zathura Code Generator V2.1.1 - Powered By AmaziliaSource www.vortexbird.com");
		setDefaultPageImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/balvardi-Robotic7070.jpg"));
	}
	
	
	@Override
	public void addPages() {		
		newPackageWizardPage=new NewPackageWizardPage();
		addPage(newPackageWizardPage);
		
	}
	@Override
	public boolean performFinish() {
		
		return true;
	}
	
	


	/**
	 * @return the newPackageWizardPage
	 */
	public NewPackageWizardPage getNewPackageWizardPage() {
		return newPackageWizardPage;
	}


	/**
	 * @param newPackageWizardPage the newPackageWizardPage to set
	 */
	public void setNewPackageWizardPage(NewPackageWizardPage newPackageWizardPage) {
		this.newPackageWizardPage = newPackageWizardPage;
	}
	
	

}
