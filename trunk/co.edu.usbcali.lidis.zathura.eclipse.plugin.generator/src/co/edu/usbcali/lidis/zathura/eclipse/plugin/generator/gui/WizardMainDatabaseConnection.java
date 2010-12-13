package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionModel;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionsUtils;

import com.swtdesigner.ResourceManager;

public class WizardMainDatabaseConnection extends Wizard {
	
	private WizardDatabaseConnection wizardDatabaseConnection;

	public WizardMainDatabaseConnection() {
		super();
		setWindowTitle("Zathura Code Generator V2.1.1");
		setDefaultPageImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/balvardi-Robotic7070.jpg"));
	}

	@Override
	public void addPages() {
		wizardDatabaseConnection=new WizardDatabaseConnection();
		addPage(wizardDatabaseConnection);
	}

	@Override
	public boolean performFinish() {
		try {
			ConnectionModel connectionModel=new ConnectionModel(wizardDatabaseConnection.getTxtDriverName().getText(), 
																wizardDatabaseConnection.getTxtConnectionURL().getText(), 
																wizardDatabaseConnection.getTxtUserName().getText(),
																wizardDatabaseConnection.getTxtPassword().getText(), 
																wizardDatabaseConnection.getTxtDriverClassName().getText(), 
																wizardDatabaseConnection.getListJARs().getItem(0));
			
			ConnectionsUtils.saveConnectionModel(connectionModel);
		} catch (Exception e) {			
			MessageDialog.openError(getShell(),"Error",e.getMessage());
			return false;
		}
		
		return true;
	}

}
