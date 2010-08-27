package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.ui.dialogs.PackageSelectionDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.reverse.utilities.ReverseEngineeringUtil;

import com.swtdesigner.ResourceManager;

public class WizardChooseSourceFolderAndPackage extends WizardPage {
	private Text txtPackage;
	private Text txtJavaSourceFolder;
	
	private IProject project;
	
	private Button btnPackage; 
	private Text txtWebRoot;
	private Text txtLib;
	
	/**
	 * Create the wizard
	 */
	public WizardChooseSourceFolderAndPackage() {
		super("wizardPage");
		setTitle("Zathura Reverse Engineering");
		setDescription("Generate JPA or Hibernate entity beans from database explorer tables");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/NewRDBDatabaseWiz.gif"));
		setPageComplete(false);
		EclipseGeneratorUtil.makeItXml=true;
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		//
		setControl(container);

		final Group choosePathGroup = new Group(container, SWT.NONE);
		choosePathGroup.setText("Choose Path Folder");
		choosePathGroup.setBounds(0, 0, 581, 184);

		final Label lblJavaSourceFolder = new Label(choosePathGroup, SWT.NONE);
		lblJavaSourceFolder.setText("Java src folder:");
		lblJavaSourceFolder.setBounds(10, 37, 99, 17);

		txtJavaSourceFolder = new Text(choosePathGroup, SWT.BORDER);
		txtJavaSourceFolder.setEditable(false);
		txtJavaSourceFolder.setBounds(115, 31, 363, 29);

		final Button btnJavaSourceFolder = new Button(choosePathGroup, SWT.NONE);
		btnJavaSourceFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseSourceFolder();
				validatePageComplete();
				txtPackage.setText("");
			}
		});
		btnJavaSourceFolder.setText("Browse...");
		btnJavaSourceFolder.setBounds(484, 31, 87, 29);

		final Label lblJavaPackage = new Label(choosePathGroup, SWT.NONE);
		lblJavaPackage.setText("Java package:");
		lblJavaPackage.setBounds(10, 72, 99, 17);

		txtPackage = new Text(choosePathGroup, SWT.BORDER);
		txtPackage.setEditable(false);
		txtPackage.setBounds(115, 66, 363, 29);

		btnPackage = new Button(choosePathGroup, SWT.NONE);
		btnPackage.setEnabled(false);
		btnPackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowsePackage();
				validatePageComplete();
			}
		});
		btnPackage.setText("Browse...");
		btnPackage.setBounds(484, 66, 87, 29);
		
		Label lblWebRoot = new Label(choosePathGroup, SWT.NONE);
		lblWebRoot.setText("WebContent:");
		lblWebRoot.setBounds(10, 107, 99, 17);
		
		Label lblLibraries = new Label(choosePathGroup, SWT.NONE);
		lblLibraries.setText("Libraries:");
		lblLibraries.setBounds(10, 143, 99, 17);
		
		txtWebRoot = new Text(choosePathGroup, SWT.BORDER);
		txtWebRoot.setEditable(false);
		txtWebRoot.setBounds(115, 101, 363, 29);
		
		txtLib = new Text(choosePathGroup, SWT.BORDER);
		txtLib.setEditable(false);
		txtLib.setBounds(115, 140, 363, 29);
		
		Button btnWebRoot = new Button(choosePathGroup, SWT.NONE);
		btnWebRoot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseWebRootFolder();
				validatePageComplete();
			}
		});
		btnWebRoot.setText("Browse...");
		btnWebRoot.setBounds(484, 101, 87, 29);
		
		Button btnLib = new Button(choosePathGroup, SWT.NONE);
		btnLib.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseLibFolder();
				validatePageComplete();
			}
		});
		btnLib.setText("Browse...");
		btnLib.setBounds(484, 140, 87, 29);
		
		Button bRadioJPAReverseEngineering = new Button(container, SWT.RADIO);
		bRadioJPAReverseEngineering.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDescription("Generate JPA entity beans from database explorer tables");
				EclipseGeneratorUtil.makeItXml=false;
			}
		});
		bRadioJPAReverseEngineering.setBounds(10, 218, 189, 22);
		bRadioJPAReverseEngineering.setText("JPA Reverse Engineering");
		
		Button bRadioHibernateReverseEngineering = new Button(container, SWT.RADIO);
		bRadioHibernateReverseEngineering.setSelection(true);
		bRadioHibernateReverseEngineering.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDescription("Generate Hibernate mapping and Java classes from database explorer tables");
				EclipseGeneratorUtil.makeItXml=true;
			}
		});
		bRadioHibernateReverseEngineering.setBounds(10, 190, 232, 22);
		bRadioHibernateReverseEngineering.setText("Hibernate Reverse Engineering");
		
		Button bRadioPOJOReverseEngineering = new Button(container, SWT.RADIO);
		bRadioPOJOReverseEngineering.setEnabled(false);
		bRadioPOJOReverseEngineering.setBounds(10, 248, 189, 16);
		bRadioPOJOReverseEngineering.setText("POJO Reverse Engineering");
		
		
	}
	private void handleBrowseSourceFolder() {
		String arrayPath[]=null;
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,	"Select source file container");
		
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				txtJavaSourceFolder.setText(((Path) result[0]).toString());				
				if(txtJavaSourceFolder.getText()!=null && txtJavaSourceFolder.getText().contains("\\")){
					arrayPath = EclipseGeneratorUtil.replaceAll(txtJavaSourceFolder.getText(), "\\", "-").split("-");
				}else if(txtJavaSourceFolder.getText()!=null && txtJavaSourceFolder.getText().contains("/")){
					arrayPath = EclipseGeneratorUtil.replaceAll(txtJavaSourceFolder.getText(), "/", "-").split("-");
				}
				
				if (arrayPath != null && arrayPath.length > 0) {
					// Obtiene el nombre del proyecto
					project = ResourcesPlugin.getWorkspace().getRoot().getProject(arrayPath[1]);
					String fullPathWorkspace = project.getLocation().toString().replaceAll(project.getFullPath().toString(), "");
					EclipseGeneratorUtil.fullPathProject=project.getLocation().toString();
					EclipseGeneratorUtil.project=project;
					EclipseGeneratorUtil.projectName=EclipseGeneratorUtil.project.getName();
					EclipseGeneratorUtil.workspaceFolderPath=fullPathWorkspace;
					
					EclipseGeneratorUtil.javaSourceFolderPath = EclipseGeneratorUtil.workspaceFolderPath+txtJavaSourceFolder.getText()+GeneratorUtil.slash;
					btnPackage.setEnabled(true);					
				}
			}
		}
	}
	private void handleBrowsePackage() {

		
		Shell shell = getShell();

		IJavaProject javaProject = JavaCore.create(project);
		
	
		IPackageFragmentRoot iPackageFragmentRoot = javaProject.getPackageFragmentRoot("");

		//TODO se debe filtrar para que solo muestre los paquetes del proyecto y no todos hasta los de las librerias		
		IJavaSearchScope iJavaSearchScope = SearchEngine.createJavaSearchScope(new IJavaElement[] {(IJavaElement) javaProject},false);

		PackageSelectionDialog packageSelectionDialog = new PackageSelectionDialog(shell, new ProgressMonitorDialog(shell),
				PackageSelectionDialog.F_REMOVE_DUPLICATES|PackageSelectionDialog.F_HIDE_EMPTY_INNER,iJavaSearchScope);

		packageSelectionDialog.setTitle("Package selection");
		packageSelectionDialog.setMessage("Please choose the Entity package:");

		if (packageSelectionDialog.open() == Window.OK) {
			Object results[] = packageSelectionDialog.getResult();
			if (results != null && results.length > 0) {
				PackageFragment packageFragment = (PackageFragment) results[0];
				txtPackage.setText(packageFragment.getElementName());
				EclipseGeneratorUtil.javaEntityPackage=packageFragment.getElementName();
			}
		}
	}
	
	
		//Se usa para validar si la pagina se completo y poder activar el next o el finish segun sea necesario	
		private void validatePageComplete(){			
			try {
				if(txtJavaSourceFolder==null || txtJavaSourceFolder.getText().equals("")==true){
					throw new Exception("Java source folder dont selected");
				}
				
				if(txtPackage==null ||  txtPackage.getText().equals("")==true ){
					throw new Exception("Java package dont selected");
				}
				
				if(txtWebRoot==null || txtWebRoot.getText().equals("")==true){
					throw new Exception("WebRoot folder dont selected");
				}	
				
				if(txtLib==null || txtLib.getText().equals("")==true){
					throw new Exception("Lib folder dont selected");		
				}
				
							
				
				
				//Valida si el paquete esta bien escrito porque el generador lo crea si no existe
				ReverseEngineeringUtil.validarPackage(txtPackage.getText());				
				
				EclipseGeneratorUtil.companyDomainName		=txtPackage.getText();
				EclipseGeneratorUtil.destinationDirectory	=txtJavaSourceFolder.getText();
				setPageComplete(true);
				setErrorMessage(null);
				
			} catch (Exception e) {
				setPageComplete(false);
				setErrorMessage(e.getMessage());
			}
		
		
	}
		
		
		private void handleBrowseWebRootFolder() {
			ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,	"Select WebContent Folder");
			
			if (dialog.open() == Window.OK) {
				Object[] result = dialog.getResult();
				if (result.length == 1) {
					txtWebRoot.setText(((Path) result[0]).toString());
					EclipseGeneratorUtil.webRootFolderPath=EclipseGeneratorUtil.workspaceFolderPath+txtWebRoot.getText()+GeneratorUtil.slash;
					
				}
			}
		}
		private void handleBrowseLibFolder() {
			ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,	"Select Libraries Folder");
			
			if (dialog.open() == Window.OK) {
				Object[] result = dialog.getResult();
				if (result.length == 1) {
					txtLib.setText(((Path) result[0]).toString());
					EclipseGeneratorUtil.libFolderPath=EclipseGeneratorUtil.workspaceFolderPath+txtLib.getText()+co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil.slash;				
				}
			}
		}
		//Se usa para validar si la pagina se completo y poder activar el next o el finish segun sea necesario
		/*
		private void validatePageComplete(){
			
			
		}
		*/
}
