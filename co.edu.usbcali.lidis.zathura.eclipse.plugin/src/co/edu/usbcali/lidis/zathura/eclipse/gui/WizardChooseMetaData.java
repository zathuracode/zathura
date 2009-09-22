package co.edu.usbcali.lidis.zathura.eclipse.gui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.swtdesigner.ResourceManager;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.Activator;
import co.edu.usbcali.lidis.zathura.eclipse.utilities.EclipseUtil;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;

public class WizardChooseMetaData extends WizardPage {

	/**
	 * Create the wizard
	 */
	public WizardChooseMetaData() {
		super("wizardPage");
		setTitle("Zathura Java Code Generator");
		setDescription("Configure the Reverse Engineering");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.getDefault(), "icons/balvardi-Robotic7070.png"));
		//Por defecto es esto cuando tengamos nuevos se debe seleccionar
		EclipseUtil.metaDataReader=MetaDataReaderFactory.JPAEntityLoaderEngine;
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		//
		setControl(container);

		final Group choiseGroup = new Group(container, SWT.NONE);
		choiseGroup.setText("Choose Meta Data Process");
		choiseGroup.setBounds(10, 10, 581, 288);

		final Button jpaReverseEngineeringButton = new Button(choiseGroup, SWT.RADIO);
		jpaReverseEngineeringButton.setEnabled(false);
		jpaReverseEngineeringButton.setText("DataBase Reverse Engineering");
		jpaReverseEngineeringButton.setBounds(10, 30, 294, 22);

		final Button jpaEntityButton = new Button(choiseGroup, SWT.RADIO);
		jpaEntityButton.setSelection(true);
		jpaEntityButton.setText("JPA Entity Class");
		jpaEntityButton.setBounds(10, 58, 294, 22);
	}

}
