package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;

public class WizardPageChooseFrameworkTire extends WizardPage {

	/**
	 * Create the wizard.
	 */
	public WizardPageChooseFrameworkTire() {
		super("wizardPage");
		setTitle("Select Web Client Type, Persistence Type, Logic Type");
		setDescription("Select web client type framework, persistence type framework and logic type framework");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		Group grpWebClientType = new Group(container, SWT.NONE);
		grpWebClientType.setText("Web Client Type");
		grpWebClientType.setBounds(10, 10, 179, 262);
		
		Button btnIceFacesUno = new Button(grpWebClientType, SWT.RADIO);
		btnIceFacesUno.setSelection(true);
		btnIceFacesUno.setBounds(37, 26, 135, 16);
		btnIceFacesUno.setText("JSF 1.2 Icefaces");
		
		Button btnPrimeFacesDos = new Button(grpWebClientType, SWT.RADIO);
		btnPrimeFacesDos.setBounds(37, 50, 135, 16);
		btnPrimeFacesDos.setText("JSF 2.0 PrimeFaces");
		
		Group grpPersistenceType = new Group(container, SWT.NONE);
		grpPersistenceType.setText("Persistence Type");
		grpPersistenceType.setBounds(392, 10, 172, 262);
		
		Button btnHibernateCore = new Button(grpPersistenceType, SWT.RADIO);
		btnHibernateCore.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				EclipseGeneratorUtil.makeItXml=true;
			}
		});
		btnHibernateCore.setSelection(true);
		btnHibernateCore.setBounds(37, 26, 122, 16);
		btnHibernateCore.setText("Hibernate Core");
		
		Button btnJPAUno = new Button(grpPersistenceType, SWT.RADIO);
		btnJPAUno.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				EclipseGeneratorUtil.makeItXml=false;
			}
		});
		btnJPAUno.setBounds(37, 50, 90, 16);
		btnJPAUno.setText("JPA 1.0");
		
		Button btnJDBCSQL = new Button(grpPersistenceType, SWT.RADIO);
		btnJDBCSQL.setEnabled(false);
		btnJDBCSQL.setBounds(37, 72, 90, 16);
		btnJDBCSQL.setText("JDBC SQL");
		
		Group grpLogicType = new Group(container, SWT.NONE);
		grpLogicType.setText("Logic Type");
		grpLogicType.setBounds(200, 10, 179, 262);
		
		Button btnSpringTres = new Button(grpLogicType, SWT.RADIO);
		btnSpringTres.setSelection(true);
		btnSpringTres.setBounds(37, 26, 139, 16);
		btnSpringTres.setText("Spring framework 3.0");
		
		Button btnPOJO = new Button(grpLogicType, SWT.RADIO);
		btnPOJO.setBounds(37, 50, 90, 16);
		btnPOJO.setText("POJO");
		
		Button btnGuice = new Button(grpLogicType, SWT.RADIO);
		btnGuice.setEnabled(false);
		btnGuice.setBounds(37, 72, 90, 16);
		btnGuice.setText("Guice");
		
		
		setPageComplete(true);
	}
}
