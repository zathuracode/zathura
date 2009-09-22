package co.edu.usbcali.lidis.zathura.eclipse.gui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.ui.dialogs.PackageSelectionDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.swtdesigner.ResourceManager;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.Activator;
import co.edu.usbcali.lidis.zathura.eclipse.utilities.EclipseUtil;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.reader.IMetaDataReader;
import co.edu.usbcali.lidis.zathura.metadata.reader.MetaDataReaderFactory;

@SuppressWarnings("restriction")
public class WizardEntitySearch extends WizardPage {

	private List listAvailableEnity;
	private Text txtJavaEntityPackage;
	private Text txtClassFolder;
	
	private Button btnBrowseClassFolder;
	private Button btnBrowsePackage;
	
	private IProject project;
	
	/**
	 * Create the wizard
	 */
	public WizardEntitySearch() {
		super("wizardPage");
		setTitle("Zathura Java Code Generator");
		setDescription("Search Entity class");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.getDefault(), "icons/balvardi-Robotic7070.png"));
		setPageComplete(false);
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		//
		setControl(container);

		final Group chooseEntityClassGroup = new Group(container, SWT.NONE);
		chooseEntityClassGroup.setText("Choose Entity Class");
		chooseEntityClassGroup.setBounds(10, 10, 581, 324);

		final Label lblAvailableEntity = new Label(chooseEntityClassGroup,
				SWT.NONE);
		lblAvailableEntity.setText("Available Entity");
		lblAvailableEntity.setBounds(10, 110, 203, 17);

		final Label lblClassFolder = new Label(chooseEntityClassGroup, SWT.NONE);
		lblClassFolder.setText("Class Folder:");
		lblClassFolder.setBounds(10, 40, 84, 17);

		txtClassFolder = new Text(chooseEntityClassGroup, SWT.BORDER);
		txtClassFolder.setEditable(false);
		txtClassFolder.setBounds(145, 40, 322, 29);

		btnBrowseClassFolder = new Button(chooseEntityClassGroup,
				SWT.NONE);
		btnBrowseClassFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseClassFolder();
				handleListAvailableEnity();
			}
		});
		btnBrowseClassFolder.setText("Browse ...");
		btnBrowseClassFolder.setBounds(473, 40, 93, 29);

		final Label lblJavaEntityPackage = new Label(chooseEntityClassGroup,
				SWT.NONE);
		lblJavaEntityPackage.setText("Java Entity package:");
		lblJavaEntityPackage.setBounds(10, 75, 128, 17);

		txtJavaEntityPackage = new Text(chooseEntityClassGroup, SWT.BORDER);
		txtJavaEntityPackage.setEditable(false);
		txtJavaEntityPackage.setBounds(145, 75, 322, 29);

		final ScrolledComposite scrolledComposite = new ScrolledComposite(
				chooseEntityClassGroup, SWT.BORDER | SWT.H_SCROLL
						| SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 133, 561, 181);

		listAvailableEnity = new List(scrolledComposite, SWT.BORDER);
		listAvailableEnity.setCapture(true);
		listAvailableEnity.setSize(561, 181);
		scrolledComposite.setContent(listAvailableEnity);

		btnBrowsePackage = new Button(chooseEntityClassGroup,
				SWT.NONE);
		btnBrowsePackage.setEnabled(false);
		btnBrowsePackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowsePackage();
				handleListAvailableEnity();
			}
		});
		btnBrowsePackage.setText("Browse ...");
		btnBrowsePackage.setBounds(473, 75, 93, 29);
	}

	private void handleBrowsePackage() {

		
			Shell shell = getShell();

			IJavaProject javaProject = JavaCore.create(project);
		
			//IPackageFragmentRoot iPackageFragmentRoot = javaProject;

			//TODO se debe filtrar para que solo muestre los paquetes del proyecto y no todos hasta los de las librerias
			IJavaSearchScope iJavaSearchScope = SearchEngine.createJavaSearchScope(new IJavaElement[] {(IJavaElement) javaProject});
			//IJavaSearchScope iJavaSearchScope = SearchEngine.createJavaSearchScope(elements)

			PackageSelectionDialog packageSelectionDialog = new PackageSelectionDialog(shell, new ProgressMonitorDialog(shell),
					PackageSelectionDialog.F_REMOVE_DUPLICATES|PackageSelectionDialog.F_HIDE_EMPTY_INNER,
					iJavaSearchScope);

			packageSelectionDialog.setTitle("Package selection");
			packageSelectionDialog.setMessage("Please choose the Entity package:");

			if (packageSelectionDialog.open() == Window.OK) {
				Object results[] = packageSelectionDialog.getResult();
				if (results != null && results.length > 0) {
					PackageFragment packageFragment = (PackageFragment) results[0];
					txtJavaEntityPackage.setText(packageFragment.getElementName());
					EclipseUtil.javaEntityPackage=packageFragment.getElementName();
				}
			}
		

	}

	private void handleBrowseClassFolder() {
		String arrayPath[]=null;
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,"Select Class file container");
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				txtClassFolder.setText(((Path) result[0]).toString());
				if(txtClassFolder.getText()!=null && txtClassFolder.getText().contains("\\")){
					arrayPath = GeneratorUtil.replaceAll(txtClassFolder.getText(), "\\", "-").split("-");
				}else if(txtClassFolder.getText()!=null && txtClassFolder.getText().contains("/")){
					arrayPath = GeneratorUtil.replaceAll(txtClassFolder.getText(), "/", "-").split("-");
				}
				
				if (arrayPath != null && arrayPath.length > 0) {
					// Obtiene el nombre del proyecto
					project = ResourcesPlugin.getWorkspace().getRoot().getProject(arrayPath[1]);
					EclipseUtil.project=project;
					EclipseUtil.projectName=EclipseUtil.project.getName();
					btnBrowsePackage.setEnabled(true);					
				}
			}
		}
	}

	private void handleListAvailableEnity() {
		listAvailableEnity.removeAll();
		if (txtClassFolder != null && txtClassFolder.getText() != null
				&& txtClassFolder.getText().equals("") != true
				&& txtJavaEntityPackage != null
				&& txtJavaEntityPackage.getText() != null
				&& txtJavaEntityPackage.getText().equals("") != true) {
			try {

				// Obtiene el full path donde esta el proyecto
				String fullPathWorkspace = project.getLocation().toString().replaceAll(project.getFullPath().toString(), "");
				String path = fullPathWorkspace + txtClassFolder.getText()+ GeneratorUtil.slash;
				
				EclipseUtil.workspaceFolderPath=fullPathWorkspace;
				EclipseUtil.fullPathProject=project.getLocation().toString();
				EclipseUtil.javaClassFolderPath=path;
				

				IMetaDataReader metaDataReader;
				metaDataReader = MetaDataReaderFactory.createMetaDataReader(MetaDataReaderFactory.JPAEntityLoaderEngine);
				MetaDataModel metaDataModel = metaDataReader.loadMetaDataModel(path, txtJavaEntityPackage.getText());
				java.util.List<MetaData> theMetaData = metaDataModel.getTheMetaData();
				
				//System.out.println(lista.size());
				//Activa el next del wizard
				if(theMetaData!=null && theMetaData.size()>0){
					setPageComplete(true);
				}else{
					setPageComplete(false);
					MessageDialog.openError(getShell(), "Error No Entity Found", "This has not been properly selected.\nChoose a class folder and Entity package");
				}
				for (MetaData metaData : theMetaData) {					
					listAvailableEnity.add(metaData.getRealClassName());
				}
			} catch (MetaDataReaderNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
