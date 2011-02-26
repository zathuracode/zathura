package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionModel;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionsUtils;
import co.edu.usbcali.lidis.zathura.swt.utilities.ResourceManager;


public class WizardMainDatabaseConnection extends Wizard {
	
	private WizardPageDatabaseConnection wizardDatabaseConnection;

	
	
	public WizardMainDatabaseConnection() {
		super();
		setWindowTitle("Zathura Code Generator V2.1.1 - Powered By AmaziliaSource www.vortexbird.com");
		setDefaultPageImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/balvardi-Robotic7070.jpg"));
		wizardDatabaseConnection=new WizardPageDatabaseConnection();
	}
	
	
	
	
	/*
	 * 
	 * private Text txtConnectionURL;
	private Text txtUserName;
	private Text txtPassword;
	private Combo cmbDriverTemplate;
	private List listJARs;
	private Text txtDriverClassName;
	private Button btnTestDriver;
	private boolean testConnection=false;
	private Text txtDriverName;
	
	 */
	public WizardMainDatabaseConnection(String connectionName) {
		super();
		setWindowTitle("Zathura Code Generator V2.1.1");
		setDefaultPageImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/balvardi-Robotic7070.jpg"));
		ConnectionModel connectionModel= ConnectionsUtils.getTheZathuraConnectionModel(connectionName);
		
		wizardDatabaseConnection=new WizardPageDatabaseConnection();
		wizardDatabaseConnection.setDriverTemplate(connectionModel.getDriverTemplate());
		wizardDatabaseConnection.setName(connectionModel.getName());
		wizardDatabaseConnection.setUrl(connectionModel.getUrl());
		wizardDatabaseConnection.setUser(connectionModel.getUser());
		wizardDatabaseConnection.setPassword(connectionModel.getPassword());
		wizardDatabaseConnection.setDriverClassName(connectionModel.getDriverClassName());
		wizardDatabaseConnection.setJarPath(connectionModel.getJarPath());
		wizardDatabaseConnection.setTitle("Edit Database Connection");
		wizardDatabaseConnection.setDescription("Edit a connection driver");
		
	}
	

	@Override
	public void addPages() {
		
		addPage(wizardDatabaseConnection);
	}

	@Override
	public boolean performFinish() {
		try {
			ConnectionModel connectionModel=new ConnectionModel(wizardDatabaseConnection.getCmbDriverTemplate().getText(),
																wizardDatabaseConnection.getTxtDriverName().getText(), 
																wizardDatabaseConnection.getTxtConnectionURL().getText(), 
																wizardDatabaseConnection.getTxtUserName().getText(),
																wizardDatabaseConnection.getTxtPassword().getText(), 
																wizardDatabaseConnection.getTxtDriverClassName().getText(), 
																wizardDatabaseConnection.getListJARs().getItem(0));
			
			ConnectionsUtils.saveConnectionModel(connectionModel);
			WizardPageSelectDBConnection.loadConnections();
		} catch (Exception e) {			
			MessageDialog.openError(getShell(),"Error",e.getMessage());
			return false;
		}
		
		return true;
	}

	public WizardPageDatabaseConnection getWizardDatabaseConnection() {
		return wizardDatabaseConnection;
	}

	public void setWizardDatabaseConnection(
			WizardPageDatabaseConnection wizardDatabaseConnection) {
		this.wizardDatabaseConnection = wizardDatabaseConnection;
	}
	
}
