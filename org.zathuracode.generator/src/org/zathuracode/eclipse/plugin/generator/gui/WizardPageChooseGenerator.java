package org.zathuracode.eclipse.plugin.generator.gui;


import java.util.HashMap;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.zathuracode.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import org.zathuracode.generator.factory.ZathuraGeneratorFactory;
import org.zathuracode.generator.model.GeneratorModel;
import org.zathuracode.generator.utilities.GeneratorUtil;
import org.eclipse.jface.viewers.ComboViewer;


// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see WizardPage
 */
public class WizardPageChooseGenerator extends WizardPage {

	/** The list generators version 3.0  */
	private List listGeneratorsVersion3_0;
	
	/** The list generators version 3.1 */
	private List listGeneratorsVersion3_1;
	
	/** The list generators. */
	private List listGenerators;
	
	/** The bwr description. */
	private Browser bwrDescription;
	
	private ComboViewer comboViewer;
	
	// Load the zathura Generators names
	/** The generators. */
	private HashMap<String, GeneratorModel> theGenerators = ZathuraGeneratorFactory.getTheZathuraGenerators();

	/**
	 * Create the wizard.
	 */
	public WizardPageChooseGenerator() {
		super("wizardPage");
		setTitle("Zathura Java Code Generator");
		setDescription("Generate Java Application based on Open Standards and JavaEE Design Patterns");
		setPageComplete(false);
	}

	/**
	 * Create contents of the wizard.
	 *
	 * @param parent the parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		//
		setControl(container);

		final Group generatorChoiseGroup = new Group(container, SWT.NONE);
		generatorChoiseGroup.setText("Choose Generator");
		generatorChoiseGroup.setBounds(10, 10, 583, 400);
		
		final Label lblGeneratorVersion3_0 = new Label(generatorChoiseGroup, SWT.NONE);
		lblGeneratorVersion3_0.setText("Old:");
		lblGeneratorVersion3_0.setBounds(10, 23, 99, 17);
		
		listGeneratorsVersion3_0 = new List(generatorChoiseGroup, SWT.BORDER);
		listGeneratorsVersion3_0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				listGeneratorsVersion3_1.deselectAll();
				String architectureSelected = (listGeneratorsVersion3_0.getSelection()[0]);
				String architectureName = ZathuraGeneratorFactory.getGeneratorNameForGuiName(architectureSelected);
				EclipseGeneratorUtil.zathuraGeneratorName = architectureName;
				GeneratorModel generatorModel = theGenerators.get(architectureName);
				if (generatorModel != null) {
					//Se reemplaza el texto ${RELATIVE_PATH} por la ruta relativa del proyecto,
					//para que al momento de mostrar el html, se muestren las imagenes
					//Andres Puerta
					String descriptionHTML = generatorModel.getDescription().replace("${RELATIVE_PATH}", GeneratorUtil.getFullPath());					
					bwrDescription.setText(descriptionHTML);
					setPageComplete(true);
				}
			}
		});
		listGeneratorsVersion3_0.setBounds(10, 43, 213, 80);
		
		
		final Label lblGeneratorVersion3_1 = new Label(generatorChoiseGroup, SWT.NONE);
		lblGeneratorVersion3_1.setText("New:");
		lblGeneratorVersion3_1.setBounds(10, 130, 99, 17);
		
		listGeneratorsVersion3_1 = new List(generatorChoiseGroup, SWT.BORDER);
		listGeneratorsVersion3_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				listGeneratorsVersion3_0.deselectAll();
				String architectureSelected = (listGeneratorsVersion3_1.getSelection()[0]);
				String architectureName = ZathuraGeneratorFactory.getGeneratorNameForGuiName(architectureSelected);
				EclipseGeneratorUtil.zathuraGeneratorName = architectureName;
				GeneratorModel generatorModel = theGenerators.get(architectureName);
				if (generatorModel != null) {
					//Se reemplaza el texto ${RELATIVE_PATH} por la ruta relativa del proyecto,
					//para que al momento de mostrar el html, se muestren las imagenes
					//Andres Puerta
					String descriptionHTML = generatorModel.getDescription().replace("${RELATIVE_PATH}", GeneratorUtil.getFullPath());
					bwrDescription.setText(descriptionHTML);
					setPageComplete(true);
				}
			}
		});
		listGeneratorsVersion3_1.setBounds(10, 150, 213, 80);

		bwrDescription = new Browser(generatorChoiseGroup, SWT.NONE);
		bwrDescription.setBounds(227, 23, 350, 370);

		loadListGeneratorsVersion3_0();
		loadListGeneratorsVersion3_1();
	}


	/**
	 * Load list generators version 3.0.
	 */
	public void loadListGeneratorsVersion3_0() {
		if (listGeneratorsVersion3_0 != null) {
			listGeneratorsVersion3_0.removeAll();
			for (GeneratorModel generatorModel : theGenerators.values()) {
				if (generatorModel.getZathuraVersion().equals("3.0")) {
					if (EclipseGeneratorUtil.makeItXml == true && generatorModel.getPersistence().equals("hibernateCore") == true) {
						listGeneratorsVersion3_0.add(generatorModel.getGuiName());
					} else if (EclipseGeneratorUtil.makeItXml == false && generatorModel.getPersistence().equals("jpa") == true) {
						listGeneratorsVersion3_0.add(generatorModel.getGuiName());
					}
				}
			}
		}

		if (bwrDescription != null) {
			bwrDescription.setText("");
		}
	}
	
	/**
	 * Load list generators version 3.1.
	 */
	public void loadListGeneratorsVersion3_1() {
		if (listGeneratorsVersion3_1 != null) {
			listGeneratorsVersion3_1.removeAll();
			for (GeneratorModel generatorModel : theGenerators.values()) {
				if (generatorModel.getZathuraVersion().equals("3.1")) {
					if (EclipseGeneratorUtil.makeItXml == true && generatorModel.getPersistence().equals("hibernateCore") == true) {
						listGeneratorsVersion3_1.add(generatorModel.getGuiName());
					} else if (EclipseGeneratorUtil.makeItXml == false && generatorModel.getPersistence().equals("jpa") == true) {
						listGeneratorsVersion3_1.add(generatorModel.getGuiName());
					}
				}
			}
		}

		if (bwrDescription != null) {
			bwrDescription.setText("");
		}
	}
	
}
