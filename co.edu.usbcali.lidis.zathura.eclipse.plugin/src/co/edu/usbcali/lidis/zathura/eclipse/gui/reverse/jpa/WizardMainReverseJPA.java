package co.edu.usbcali.lidis.zathura.eclipse.gui.reverse.jpa;

import org.eclipse.jface.wizard.Wizard;

public class WizardMainReverseJPA extends Wizard {
	
	
	//WizardPage
	WizardDatabaseConnection wizardDatabaseConnection;
	
	public WizardMainReverseJPA() {
		setWindowTitle("Zathura Code Generator V2.1.0");
	}

	@Override
	public boolean performFinish() {
		return false;
	}
	
	public void addPages() {
		super.addPages();
		//Creo el wizard y lo asigno al administrado
		
		wizardDatabaseConnection=new WizardDatabaseConnection();
		addPage(wizardDatabaseConnection);
		
		WizardSelectTables wizardSelectTables=new WizardSelectTables();
		addPage(wizardSelectTables);
		
	}

}
