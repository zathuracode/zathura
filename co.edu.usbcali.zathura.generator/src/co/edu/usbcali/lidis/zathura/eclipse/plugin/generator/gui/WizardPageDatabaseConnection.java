package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionsUtils;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ZathuraGeneratorLog;
import co.edu.usbcali.lidis.zathura.reverse.utilities.DatabaseTypeModel;
import co.edu.usbcali.lidis.zathura.reverse.utilities.ZathuraReverseEngineeringUtil;

// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see WizardPage
 */
public class WizardPageDatabaseConnection extends WizardPage {

	/** The zathura database types. */
	private HashMap<String, DatabaseTypeModel> zathuraDatabaseTypes;
	
	/** The database type model. */
	private DatabaseTypeModel databaseTypeModel;
	
	/** The txt connection url. */
	private Text txtConnectionURL;
	
	/** The txt user name. */
	private Text txtUserName;
	
	/** The txt password. */
	private Text txtPassword;
	
	/** The cmb driver template. */
	private Combo cmbDriverTemplate;
	
	/** The list ja rs. */
	private List listJARs;
	
	/** The txt driver class name. */
	private Text txtDriverClassName;
	
	/** The btn test driver. */
	private Button btnTestDriver;
	
	/** The test connection. */
	private boolean testConnection = false;
	
	/** The txt driver name. */
	private Text txtDriverName;

	/** The driver template. */
	private String driverTemplate;
	
	/** The name. */
	private String name;
	
	/** The url. */
	private String url;
	
	/** The user. */
	private String user;
	
	/** The password. */
	private String password;
	
	/** The driver class name. */
	private String driverClassName;
	
	/** The jar path. */
	private String jarPath;

	/**
	 * Create the wizard.
	 */
	public WizardPageDatabaseConnection() {
		super("wizardPage");
		setTitle("New Database Connection");
		setDescription("Create a new connection driver");
		setPageComplete(false);
	}

	/**
	 * Create contents of the wizard.
	 *
	 * @param parent the parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		Label lblDriverTemplate = new Label(container, SWT.NONE);
		lblDriverTemplate.setBounds(10, 13, 128, 17);
		lblDriverTemplate.setText("Driver template:");

		cmbDriverTemplate = new Combo(container, SWT.NONE);
		cmbDriverTemplate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				testConnection = false;
				String driverSelected = cmbDriverTemplate.getText();
				databaseTypeModel = zathuraDatabaseTypes.get(driverSelected);
				if (databaseTypeModel != null) {
					txtConnectionURL.setText(databaseTypeModel.getUrl());
					txtDriverClassName.setText("");
					txtDriverClassName.setText(databaseTypeModel.getDriverClassName());
					// TODO Cambiar esto para la version 2.1.1
					WizardPageSelectTables.db = databaseTypeModel.getName();
					// Valida que la paguina este completa
					validatePageComplete();
				}
			}
		});
		cmbDriverTemplate.setBounds(144, 10, 420, 27);

		Label lblConnectionUrl = new Label(container, SWT.NONE);
		lblConnectionUrl.setBounds(10, 71, 128, 17);
		lblConnectionUrl.setText("Connection URL:");

		txtConnectionURL = new Text(container, SWT.BORDER);
		txtConnectionURL.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validatePageComplete();
			}
		});
		txtConnectionURL.setBounds(144, 68, 420, 22);

		txtUserName = new Text(container, SWT.BORDER);
		txtUserName.setBounds(144, 96, 420, 22);

		txtPassword = new Text(container, SWT.BORDER);
		txtPassword.setBounds(144, 124, 420, 22);
		txtPassword.setEchoChar('*');

		Label lblUserName = new Label(container, SWT.NONE);
		lblUserName.setBounds(10, 99, 128, 17);
		lblUserName.setText("User name:");

		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setBounds(10, 127, 128, 17);
		lblPassword.setText("Password:");

		Label lineSeparatorJar = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineSeparatorJar.setBounds(10, 152, 568, 2);

		Label lblDriverJar = new Label(container, SWT.NONE);
		lblDriverJar.setBounds(10, 182, 90, 17);
		lblDriverJar.setText("Driver JARs");

		final ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 205, 451, 84);

		listJARs = new List(scrolledComposite, SWT.NONE);
		// listJARs = new List(container, SWT.BORDER | SWT.MULTI);
		listJARs.setBounds(10, 205, 451, 84);
		scrolledComposite.setContent(listJARs);

		Button btnAddJar = new Button(container, SWT.NONE);
		btnAddJar.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				testConnection = false;
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
				fd.setText("");
				fd.setFilterPath("");
				String[] filterExt = { "*.jar", "*.zip", "*.*" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				listJARs.add(selected);
				try {
					EclipseGeneratorUtil.loadJarSystem(selected);
					validatePageComplete();
				} catch (Exception e1) {
					ZathuraGeneratorLog.logError(e1);
					MessageDialog.openError(getShell(), "Error", e1.getMessage());
				}

			}
		});
		btnAddJar.setBounds(467, 205, 97, 25);
		btnAddJar.setText("Add JARs");

		Button btnRemove = new Button(container, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				testConnection = false;
				int i = listJARs.getSelectionIndex();
				listJARs.remove(i);
				validatePageComplete();
			}

		});
		btnRemove.setBounds(467, 236, 97, 25);
		btnRemove.setText("Remove");

		Label lblDriverClassName = new Label(container, SWT.NONE);
		lblDriverClassName.setBounds(10, 306, 128, 17);
		lblDriverClassName.setText("Driver classname:");

		btnTestDriver = new Button(container, SWT.NONE);
		btnTestDriver.setEnabled(false);
		btnTestDriver.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				String url = txtConnectionURL.getText();
				String driverClassName = txtDriverClassName.getText();
				String user = txtUserName.getText();
				String password = txtPassword.getText();
				try {
					ZathuraReverseEngineeringUtil.testDriver(url, driverClassName, user, password);
					MessageDialog.openInformation(getShell(), "Driver Test", "Database connection successfully established");

					EclipseGeneratorUtil.connectionDriverName = txtDriverName.getText();
					EclipseGeneratorUtil.connectionDriverClass = txtDriverClassName.getText();
					EclipseGeneratorUtil.connectionUrl = txtConnectionURL.getText();
					EclipseGeneratorUtil.connectionUsername = txtUserName.getText();
					EclipseGeneratorUtil.connectionPassword = txtPassword.getText();
					EclipseGeneratorUtil.connectionDriverJarPath = listJARs.getItem(0);

					testConnection = true;
					validatePageComplete();

				} catch (Exception e1) {
					MessageDialog.openError(getShell(), "Error", e1.getMessage());
					// ZathuraGeneratorLog.logError(e1);

				}
				validatePageComplete();
			}
		});
		btnTestDriver.setBounds(10, 329, 88, 25);
		btnTestDriver.setText("Test Driver");

		txtDriverClassName = new Text(container, SWT.BORDER);
		txtDriverClassName.setBounds(144, 301, 420, 27);

		Label lblDriverName = new Label(container, SWT.NONE);
		lblDriverName.setBounds(10, 42, 128, 17);
		lblDriverName.setText("Driver name:");

		txtDriverName = new Text(container, SWT.BORDER);
		txtDriverName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validatePageComplete();
			}
		});
		txtDriverName.setBounds(144, 39, 420, 22);

		loadCmbDriverTemplate();

		loadEditValues();

	}

	/**
	 * Load edit values.
	 */
	private void loadEditValues() {
		/*
		 * private String driverTemplate; private String name; private String
		 * url; private String user; private String password; private String
		 * driverClassName; private String jarPath;
		 */
		if (driverTemplate != null && driverTemplate.equals("") != true && name != null && name.equals("") != true && url != null && url.equals("") != true
				&& user != null && user.equals("") != true && password != null && password.equals("") != true && driverClassName != null
				&& driverClassName.equals("") != true && jarPath != null && jarPath.equals("") != true) {

			getCmbDriverTemplate().setText(driverTemplate);
			getTxtDriverName().setText(name);
			getTxtConnectionURL().setText(url);
			getTxtDriverClassName().setText(driverClassName);
			getTxtPassword().setText(password);
			getTxtUserName().setText(user);
			getListJARs().removeAll();
			getListJARs().add(jarPath);

			btnTestDriver.setEnabled(true);
		}

	}

	/**
	 * Validate page complete.
	 */
	private void validatePageComplete() {

		try {

			if (txtDriverName.getText() == null || txtDriverName.getText().equals("") == true) {
				throw new Exception("No driver name specified");
			}
			if (ConnectionsUtils.connectionExist(txtDriverName.getText()) == true && (name == null || name.equals("") == true)) {
				throw new Exception("A driver with that name already exists");
			}
			if (listJARs.getItems() == null || listJARs.getItems().length == 0) {
				throw new Exception("Driver class not found");
			}
			if (testConnection == false) {
				btnTestDriver.setEnabled(true);
				throw new Exception("Pleace Test Connection");
			}

			setErrorMessage(null);
			setPageComplete(true);
			btnTestDriver.setEnabled(true);

		} catch (Exception e) {
			setPageComplete(false);
			setErrorMessage(e.getMessage());
		}
	}

	/**
	 * Load cmb driver template.
	 */
	private void loadCmbDriverTemplate() {
		try {
			if (zathuraDatabaseTypes == null) {
				zathuraDatabaseTypes = ZathuraReverseEngineeringUtil.loadZathuraDatabaseTypes();
			}

			for (DatabaseTypeModel databaseTypeModel : zathuraDatabaseTypes.values()) {
				cmbDriverTemplate.add(databaseTypeModel.getName());
			}
		} catch (FileNotFoundException e) {
			MessageDialog.openInformation(getShell(), "Driver Template", e.getMessage());
			ZathuraGeneratorLog.logError("Driver Template", e);
			e.printStackTrace();
		} catch (XMLStreamException e) {
			MessageDialog.openInformation(getShell(), "Driver Template", e.getMessage());
			ZathuraGeneratorLog.logError("Driver Template", e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			MessageDialog.openInformation(getShell(), "Driver Template", e.getMessage());
			ZathuraGeneratorLog.logError("Driver Template", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			MessageDialog.openInformation(getShell(), "Driver Template", e.getMessage());
			ZathuraGeneratorLog.logError("Driver Template", e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			MessageDialog.openInformation(getShell(), "Driver Template", e.getMessage());
			ZathuraGeneratorLog.logError("Driver Template", e);
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#setPageComplete(boolean)
	 */
	@Override
	public void setPageComplete(boolean complete) {
		super.setPageComplete(complete);
		if (complete == true) {
			EclipseGeneratorUtil.jarList = listJARs.getItems();

		}
	}

	/**
	 * Gets the txt connection url.
	 *
	 * @return the txt connection url
	 */
	public Text getTxtConnectionURL() {
		return txtConnectionURL;
	}

	/**
	 * Sets the txt connection url.
	 *
	 * @param txtConnectionURL the txt connection url
	 */
	public void setTxtConnectionURL(Text txtConnectionURL) {
		this.txtConnectionURL = txtConnectionURL;
	}

	/**
	 * Gets the txt user name.
	 *
	 * @return the txt user name
	 */
	public Text getTxtUserName() {
		return txtUserName;
	}

	/**
	 * Sets the txt user name.
	 *
	 * @param txtUserName the txt user name
	 */
	public void setTxtUserName(Text txtUserName) {
		this.txtUserName = txtUserName;
	}

	/**
	 * Gets the txt password.
	 *
	 * @return the txt password
	 */
	public Text getTxtPassword() {
		return txtPassword;
	}

	/**
	 * Sets the txt password.
	 *
	 * @param txtPassword the txt password
	 */
	public void setTxtPassword(Text txtPassword) {
		this.txtPassword = txtPassword;
	}

	/**
	 * Gets the list ja rs.
	 *
	 * @return the list ja rs
	 */
	public List getListJARs() {
		return listJARs;
	}

	/**
	 * Sets the list ja rs.
	 *
	 * @param listJARs the list ja rs
	 */
	public void setListJARs(List listJARs) {
		this.listJARs = listJARs;
	}

	/**
	 * Gets the txt driver class name.
	 *
	 * @return the txt driver class name
	 */
	public Text getTxtDriverClassName() {
		return txtDriverClassName;
	}

	/**
	 * Sets the txt driver class name.
	 *
	 * @param txtDriverClassName the txt driver class name
	 */
	public void setTxtDriverClassName(Text txtDriverClassName) {
		this.txtDriverClassName = txtDriverClassName;
	}

	/**
	 * Gets the txt driver name.
	 *
	 * @return the txt driver name
	 */
	public Text getTxtDriverName() {
		return txtDriverName;
	}

	/**
	 * Sets the txt driver name.
	 *
	 * @param txtDriverName the txt driver name
	 */
	public void setTxtDriverName(Text txtDriverName) {
		this.txtDriverName = txtDriverName;
	}

	/**
	 * Gets the cmb driver template.
	 *
	 * @return the cmb driver template
	 */
	public Combo getCmbDriverTemplate() {
		return cmbDriverTemplate;
	}

	/**
	 * Sets the cmb driver template.
	 *
	 * @param cmbDriverTemplate the cmb driver template
	 */
	public void setCmbDriverTemplate(Combo cmbDriverTemplate) {
		this.cmbDriverTemplate = cmbDriverTemplate;
	}

	/**
	 * Gets the driver template.
	 *
	 * @return the driver template
	 */
	public String getDriverTemplate() {
		return driverTemplate;
	}

	/**
	 * Sets the driver template.
	 *
	 * @param driverTemplate the driver template
	 */
	public void setDriverTemplate(String driverTemplate) {
		this.driverTemplate = driverTemplate;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the driver class name.
	 *
	 * @return the driver class name
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * Sets the driver class name.
	 *
	 * @param driverClassName the driver class name
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * Gets the jar path.
	 *
	 * @return the jar path
	 */
	public String getJarPath() {
		return jarPath;
	}

	/**
	 * Sets the jar path.
	 *
	 * @param jarPath the jar path
	 */
	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

}
