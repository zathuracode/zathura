package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import java.sql.SQLException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionModel;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionsUtils;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.reverse.utilities.ZathuraReverseEngineeringUtil;

import com.vortexbird.amazilia.plugin.sp.gui.WizardPageSelectStoreProcedure;

public class WizardPageSelectDBConnection extends WizardPage {

	public WizardPageDatabaseConnection wizardDatabaseConnection;
	private static List listConnections;
	private Link linkEditConnection;
	private Link linkRemoveConnection;

	public WizardPageSelectDBConnection() {
		super("wizardPage");
		setTitle("Select DB Connection");
		setDescription("Select the database connection for accessing DB schema");
		setPageComplete(false);

	}

	@Override
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		Label lblEspecifyTheLocation = new Label(container, SWT.NONE);
		lblEspecifyTheLocation.setBounds(10, 0, 534, 15);
		lblEspecifyTheLocation.setText("Specify the location of the database schema by selecting database connection");

		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 21, 554, 149);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		listConnections = new List(scrolledComposite, SWT.BORDER);
		listConnections.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					if (listConnections.getSelection().length == 0) {
						return;
					}
					linkEditConnection.setEnabled(true);
					linkRemoveConnection.setEnabled(true);
					validatePageComplete();

					String connectionSelected = (listConnections.getSelection()[0]);

					ConnectionModel connectionModel = ConnectionsUtils.getTheZathuraConnectionModel(connectionSelected);

					EclipseGeneratorUtil.connectionDriverName = connectionModel.getName();
					EclipseGeneratorUtil.connectionDriverClass = connectionModel.getDriverClassName();
					EclipseGeneratorUtil.connectionUrl = connectionModel.getUrl();
					EclipseGeneratorUtil.connectionUsername = connectionModel.getUser();
					EclipseGeneratorUtil.connectionPassword = connectionModel.getPassword();
					EclipseGeneratorUtil.connectionDriverJarPath = connectionModel.getJarPath();
					EclipseGeneratorUtil.connectionDriverTemplate = connectionModel.getDriverTemplate();

					String arrayJarList[] = { connectionModel.getJarPath() };
					EclipseGeneratorUtil.jarList = arrayJarList;

					EclipseGeneratorUtil.loadJarSystem(EclipseGeneratorUtil.connectionDriverJarPath);

					ZathuraReverseEngineeringUtil.testDriver(EclipseGeneratorUtil.connectionUrl, EclipseGeneratorUtil.connectionDriverClass,
							EclipseGeneratorUtil.connectionUsername, EclipseGeneratorUtil.connectionPassword);

					MessageDialog.openInformation(getShell(), "Driver Test", "Database connection successfully established");

					if (getNextPage() instanceof WizardPageSelectTables) {
						WizardPageSelectTables wizardSelectTables = (WizardPageSelectTables) getNextPage();
						wizardSelectTables.resetForm();
					} else if (getNextPage() instanceof WizardPageSelectStoreProcedure) {
						WizardPageSelectStoreProcedure wizardPageSelectStoreProcedure = (WizardPageSelectStoreProcedure) getNextPage();
						wizardPageSelectStoreProcedure.resetForm();
					}

					validatePageComplete();

				} catch (ClassNotFoundException e1) {
					setPageComplete(false);
					MessageDialog.openError(getShell(), "Error", e1.getMessage());
				} catch (SQLException e1) {
					setPageComplete(false);
					MessageDialog.openError(getShell(), "Error", e1.getMessage());
				} catch (Exception e1) {
					setPageComplete(false);
					MessageDialog.openError(getShell(), "Error", e1.getMessage());
				}
			}
		});

		scrolledComposite.setContent(listConnections);
		scrolledComposite.setMinSize(listConnections.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setBounds(10, 208, 554, 30);
		lblDescription.setText("The  selected  DB  connection  will  also  be  used  for  configuring  the  persistence  connection  of  the \r\napplication");

		Link linkCreateNewConnection = new Link(container, SWT.NONE);
		linkCreateNewConnection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WizardMainDatabaseConnection wizardMainSelectDBConnection = new WizardMainDatabaseConnection();
				WizardDialog dlg = new WizardDialog(getShell(), wizardMainSelectDBConnection);
				dlg.open();

			}
		});

		linkCreateNewConnection.setBounds(52, 176, 124, 15);
		linkCreateNewConnection.setText("<a>Create new Connection</a>");

		linkEditConnection = new Link(container, SWT.NONE);
		linkEditConnection.setEnabled(false);
		linkEditConnection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {

					String conectionSelected[] = listConnections.getSelection() != null ? listConnections.getSelection() : null;
					if (conectionSelected == null || conectionSelected.length == 0) {
						throw new Exception("Not DB connection selected");
					}
					String connectionName = listConnections.getSelection()[0];
					WizardMainDatabaseConnection wizardMainSelectDBConnection = new WizardMainDatabaseConnection(connectionName);
					WizardDialog dlg = new WizardDialog(getShell(), wizardMainSelectDBConnection);
					dlg.open();

				} catch (Exception e2) {
					MessageDialog.openError(getShell(), "Error", e2.getMessage());
				}
			}
		});
		linkEditConnection.setBounds(228, 176, 124, 15);
		linkEditConnection.setText("<a>Edit Connection</a>");

		linkRemoveConnection = new Link(container, SWT.NONE);
		linkRemoveConnection.setEnabled(false);
		linkRemoveConnection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String conectionSelected[] = listConnections.getSelection() != null ? listConnections.getSelection() : null;
					if (conectionSelected == null || conectionSelected.length == 0) {
						throw new Exception("Not DB connection selected");
					}
					String connectionName = listConnections.getSelection()[0];
					ConnectionsUtils.removeConnectionModel(connectionName);
					loadConnections();
				} catch (Exception e2) {
					MessageDialog.openError(getShell(), "Error", e2.getMessage());
				}
			}
		});
		linkRemoveConnection.setBounds(404, 177, 124, 13);
		linkRemoveConnection.setText("<a>Remove Connection</a>");

		loadConnections();

	}

	public static void loadConnections() {
		java.util.List<String> theConnections = ConnectionsUtils.getConnectionNames();
		if (theConnections != null && listConnections != null) {
			listConnections.removeAll();
			for (String connectionName : theConnections) {
				listConnections.add(connectionName);
			}
		}

	}

	private void validatePageComplete() {
		try {
			setErrorMessage(null);
			setPageComplete(true);
		} catch (Exception e) {
			setPageComplete(false);
			setErrorMessage(e.getMessage());
		}
	}
}
