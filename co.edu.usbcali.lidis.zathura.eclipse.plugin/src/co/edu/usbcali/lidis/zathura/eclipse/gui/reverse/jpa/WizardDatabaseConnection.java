package co.edu.usbcali.lidis.zathura.eclipse.gui.reverse.jpa;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.Activator;
import co.edu.usbcali.lidis.zathura.eclipse.utilities.EclipseUtil;
import co.edu.usbcali.lidis.zathura.jpareverse.utilities.DatabaseTypeModel;
import co.edu.usbcali.lidis.zathura.jpareverse.utilities.JPAReverseEngineeringUtil;

import com.swtdesigner.ResourceManager;


public class WizardDatabaseConnection extends WizardPage {
	
	
	private HashMap<String, DatabaseTypeModel> zathuraDatabaseTypes;
	private DatabaseTypeModel databaseTypeModel;
	private Text txtDriverName;
	private Text txtConnectionURL;
	private Text txtUserName;
	private Text txtPassword;
	private Combo cmbDriverTemplate;
	private List listJARs;
	private Text txtDriverClassName;

	/**
	 * Create the wizard
	 */
	public WizardDatabaseConnection() {
		super("wizardPage");
		setTitle("New Database Connection");
		setDescription("Create a new connection driver");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.getDefault(), "icons/balvardi-Robotic7070.png"));
		setPageComplete(false);
		
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);	
		setControl(container);
		
		Label lblDriverTemplate = new Label(container, SWT.NONE);
		lblDriverTemplate.setBounds(10, 16, 128, 17);
		lblDriverTemplate.setText("Driver template:");
		
		cmbDriverTemplate = new Combo(container, SWT.NONE);		
		cmbDriverTemplate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String driverSelected=cmbDriverTemplate.getText();
				databaseTypeModel=zathuraDatabaseTypes.get(driverSelected);
				if(databaseTypeModel!=null){
					txtConnectionURL.setText(databaseTypeModel.getUrl());
					txtDriverClassName.setText("");
					txtDriverClassName.setText(databaseTypeModel.getDriverClassName());
					
				}
			}
		});
		cmbDriverTemplate.setBounds(144, 10, 434, 29);
		
		Label lblDriverName = new Label(container, SWT.NONE);
		lblDriverName.setBounds(10, 50, 128, 17);
		lblDriverName.setText("Driver name:");
		
		txtDriverName = new Text(container, SWT.BORDER);
		txtDriverName.setBounds(144, 45, 434, 27);
		
		Label lblConnectionUrl = new Label(container, SWT.NONE);
		lblConnectionUrl.setBounds(10, 83, 128, 17);
		lblConnectionUrl.setText("Connection URL:");
		
		txtConnectionURL = new Text(container, SWT.BORDER);
		txtConnectionURL.setBounds(144, 78, 434, 27);
		
		txtUserName = new Text(container, SWT.BORDER);
		txtUserName.setBounds(144, 111, 434, 27);
		
		txtPassword = new Text(container, SWT.BORDER);
		txtPassword.setBounds(144, 144, 434, 27);
		
		Label lblUserName = new Label(container, SWT.NONE);
		lblUserName.setBounds(10, 116, 128, 17);
		lblUserName.setText("User name:");
		
		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setBounds(10, 149, 128, 17);
		lblPassword.setText("Password:");
		
		Label lineSeparatorJar = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineSeparatorJar.setBounds(10, 177, 568, 2);
		
		Label lblDriverJar = new Label(container, SWT.NONE);
		lblDriverJar.setBounds(10, 182, 90, 17);
		lblDriverJar.setText("Driver JARs");
		
		final ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL| SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 205, 451, 84);
		
		listJARs = new List(scrolledComposite, SWT.NONE);
		//listJARs = new List(container, SWT.BORDER | SWT.MULTI);
		listJARs.setBounds(10, 205, 451, 84);
		scrolledComposite.setContent(listJARs);
		
		Button btnAddJar = new Button(container, SWT.NONE);
		btnAddJar.addSelectionListener(new SelectionAdapter() {
		
			public void widgetSelected(SelectionEvent e) {
				
			 	FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
			        fd.setText("");
			        fd.setFilterPath("");
			        String[] filterExt = { "*.jar", "*.zip","*.*" };
			        fd.setFilterExtensions(filterExt);
			        String selected = fd.open();			    
			        listJARs.add(selected);
			        try {			        	
						EclipseUtil.loadJar(selected);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			        
			}
		});
		btnAddJar.setBounds(467, 205, 111, 29);
		btnAddJar.setText("Add JARs");
		
		Button btnRemove = new Button(container, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
		
			public void widgetSelected(SelectionEvent e) {
				int i=listJARs.getSelectionIndex();
				listJARs.remove(i);
			}
		});
		btnRemove.setBounds(467, 240, 111, 29);
		btnRemove.setText("Remove");
		
		Label lblDriverClassName = new Label(container, SWT.NONE);
		lblDriverClassName.setBounds(10, 306, 128, 17);
		lblDriverClassName.setText("Driver classname:");
		
		Button btnTestDriver = new Button(container, SWT.NONE);
		btnTestDriver.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				String url=txtConnectionURL.getText();
				String driverClassName=txtDriverClassName.getText();
				String user=txtUserName.getText();
				String password=txtPassword.getText();
				try {
					JPAReverseEngineeringUtil.testDriver(url, driverClassName, user, password);
					MessageDialog.openInformation(getShell(), "Driver Test", "Database connection successfully established");
					setPageComplete(true);
				} catch (Exception e1) {
					MessageDialog.openError(getShell(), "Error", e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		btnTestDriver.setBounds(10, 329, 88, 29);
		btnTestDriver.setText("Test Driver");
		
		txtDriverClassName = new Text(container, SWT.BORDER);
		txtDriverClassName.setBounds(144, 301, 434, 27);
		
		loadCmbDriverTemplate();
		
	}
	
	private void loadCmbDriverTemplate(){
		
		try {
		
			if(zathuraDatabaseTypes==null){
				zathuraDatabaseTypes =JPAReverseEngineeringUtil.loadZathuraDatabaseTypes();
			}
			
			for (DatabaseTypeModel databaseTypeModel : zathuraDatabaseTypes.values()) {
				cmbDriverTemplate.add(databaseTypeModel.getName());
			}
		} catch (FileNotFoundException e) {
			// TODO Lanzar con panel de eclipse
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Lanzar con panel de eclipse
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Lanzar con panel de eclipse
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Lanzar con panel de eclipse
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Lanzar con panel de eclipse
			e.printStackTrace();
		}		
	}
}
