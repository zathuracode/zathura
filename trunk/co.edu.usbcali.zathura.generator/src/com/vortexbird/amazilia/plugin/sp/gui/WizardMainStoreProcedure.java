package com.vortexbird.amazilia.plugin.sp.gui;

import org.eclipse.jface.wizard.Wizard;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui.WizardPageSelectDBConnection;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.swt.utilities.ResourceManager;

public class WizardMainStoreProcedure extends Wizard {

	private WizardPageSelectDBConnection wizardSelectDBConnection;

	public WizardMainStoreProcedure() {
		setWindowTitle("Zathura Store Procedure V1.0.0");
		setDefaultPageImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/Edit_Connection.gif"));

		EclipseGeneratorUtil.reset();
		/*
		 * try { ZathuraGeneratorFactory.loadZathuraGenerators(); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } catch
		 * (XMLStreamException e) {
		 * 
		 * e.printStackTrace(); } catch (InstantiationException e) {
		 * 
		 * e.printStackTrace(); } catch (IllegalAccessException e) {
		 * 
		 * e.printStackTrace(); } catch (ClassNotFoundException e) {
		 * 
		 * e.printStackTrace(); }
		 */
	}

	@Override
	public void addPages() {
		super.addPages();

		wizardSelectDBConnection = new WizardPageSelectDBConnection();
		addPage(wizardSelectDBConnection);

		WizardPageSelectStoreProcedure wizardPageSelectStoreProcedure = new WizardPageSelectStoreProcedure();
		addPage(wizardPageSelectStoreProcedure);

	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
