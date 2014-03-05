package org.zathuracode.eclipse.plugin.generator.gui;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.ui.dialogs.PackageSelectionDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.zathuracode.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import org.zathuracode.generator.utilities.GeneratorUtil;
import org.zathuracode.reverse.utilities.ZathuraReverseEngineeringUtil;



// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see WizardPage
 */
@SuppressWarnings("restriction")
public class WizardPageChooseSourceFolderAndPackage extends WizardPage {
	
	/** The txt package. */
	private Text txtPackage;
	
	/** The txt java source folder. */
	private Text txtJavaSourceFolder;
	
	/** The project. */
	private IProject project;
	
	/** The btn package. */
	private Button btnPackage; 
	
	/** The txt web root. */
	private Text txtWebRoot;
	
	/** The txt lib. */
	private Text txtLib;
	
	/** The cmb project. */
	private Combo cmbProject;
	
	/** The wizard main new package wizard page. */
	private  WizardMainNewPackageWizard wizardMainNewPackageWizardPage;
	
	/** The btn new package. */
	private Button btnNewPackage;
	
	/** The btn web root. */
	private Button btnWebRoot;
	
	/** The btn lib. */
	private Button btnLib;
	
	/** The lbl lib.*/
	private Label lblLibraries;
	
	
	/**
	 * Create the wizard.
	 */
	public WizardPageChooseSourceFolderAndPackage() {
		super("wizardPage");
		setTitle("Zathura Reverse Engineering");
		setDescription("Generate JPA or Hibernate entity beans from database explorer tables");
		//setImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/NewRDBDatabaseWiz.gif"));
		//setImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/balvardi-Robotic7070.png"));
		setPageComplete(false);
		EclipseGeneratorUtil.makeItXml=true;
		
		
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

		final Group choosePathGroup = new Group(container, SWT.NONE);
		choosePathGroup.setText("Choose Path Folder");
		choosePathGroup.setBounds(0, 0, 581, 212);

		final Label lblJavaSourceFolder = new Label(choosePathGroup, SWT.NONE);
		lblJavaSourceFolder.setText("Java src folder:");
		lblJavaSourceFolder.setBounds(10, 59, 99, 17);

		txtJavaSourceFolder = new Text(choosePathGroup, SWT.BORDER);
		txtJavaSourceFolder.setEditable(false);
		txtJavaSourceFolder.setBounds(115, 52, 363, 24);

		final Button btnJavaSourceFolder = new Button(choosePathGroup, SWT.NONE);
		btnJavaSourceFolder.setEnabled(false);
		btnJavaSourceFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseSourceFolder();
				validatePageComplete();
				txtPackage.setText("");
				
			}
		});
		btnJavaSourceFolder.setText("Browse...");
		btnJavaSourceFolder.setBounds(484, 52, 87, 27);

		final Label lblJavaPackage = new Label(choosePathGroup, SWT.NONE);
		lblJavaPackage.setText("Java package:");
		lblJavaPackage.setBounds(10, 97, 99, 17);

		txtPackage = new Text(choosePathGroup, SWT.BORDER);
		txtPackage.setEditable(false);
		txtPackage.setBounds(115, 90, 272, 24);

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
		btnPackage.setBounds(484, 90, 87, 27);
		
		Label lblWebRoot = new Label(choosePathGroup, SWT.NONE);
		lblWebRoot.setText("WebContent:");
		lblWebRoot.setBounds(10, 135, 99, 17);
		
		lblLibraries = new Label(choosePathGroup, SWT.NONE);
		lblLibraries.setText("Libraries:");
		lblLibraries.setBounds(10, 173, 99, 17);
		
		txtWebRoot = new Text(choosePathGroup, SWT.BORDER);
		txtWebRoot.setEditable(false);
		txtWebRoot.setBounds(115, 129, 363, 24);
		
		txtLib = new Text(choosePathGroup, SWT.BORDER);
		txtLib.setEditable(false);
		txtLib.setBounds(115, 168, 363, 24);
		
		btnWebRoot = new Button(choosePathGroup, SWT.NONE);
		btnWebRoot.setEnabled(false);
		btnWebRoot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseWebRootFolder();
				validatePageComplete();
			}
		});
		btnWebRoot.setText("Browse...");
		btnWebRoot.setBounds(484, 128, 87, 27);
		
		btnLib = new Button(choosePathGroup, SWT.NONE);
		btnLib.setEnabled(false);
		btnLib.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseLibFolder();
				validatePageComplete();
			}
		});
		btnLib.setText("Browse...");
		btnLib.setBounds(484, 166, 87, 27);
		
		cmbProject = new Combo(choosePathGroup, SWT.NONE);
		cmbProject.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String projectSelected=cmbProject.getText();
				project=ResourcesPlugin.getWorkspace().getRoot().getProject(projectSelected);
				if(project!=null){
					btnJavaSourceFolder.setEnabled(true);
					btnWebRoot.setEnabled(true);
					
					String pathFilePOM = project.getLocation().toString() + GeneratorUtil.slash + GeneratorUtil.pomFile;
					EclipseGeneratorUtil.pomXmlFile = new File(pathFilePOM);
					EclipseGeneratorUtil.isMavenProject = EclipseGeneratorUtil.pomXmlFile.exists();
					
					if (EclipseGeneratorUtil.isMavenProject) {						
						txtLib.setText("pom.xml");
						btnLib.setEnabled(false);
						lblLibraries.setText("Maven Config File:");
						EclipseGeneratorUtil.libFolderPath = "";
						
					}else {
						txtLib.setText("");
						btnLib.setEnabled(true);
						lblLibraries.setText("Libraries:");
					}
				}			
			}
		});
		cmbProject.setBounds(115, 15, 363, 36);
		IProject projectArray[]=ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (IProject iProject : projectArray) {
				if(iProject.isOpen()==true){
					cmbProject.add(iProject.getName());
				}				
			}	
		Label lblProject = new Label(choosePathGroup, SWT.NONE);
		lblProject.setBounds(10, 21, 99, 17);
		lblProject.setText("Project:");
		
		btnNewPackage = new Button(choosePathGroup, SWT.NONE);
		btnNewPackage.setEnabled(false);
		btnNewPackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					
				wizardMainNewPackageWizardPage=new WizardMainNewPackageWizard();
			    wizardMainNewPackageWizardPage.setiProject(project);
			    
			    
			   
				 WizardDialog dlg = new WizardDialog(getShell(),wizardMainNewPackageWizardPage);
				 
				 wizardMainNewPackageWizardPage.getNewPackageWizardPage().setPageComplete(false);
				 wizardMainNewPackageWizardPage.getNewPackageWizardPage().setPackageText("",true);
				 
				 
				 int resultado=dlg.open();
				 	if(resultado!= WizardDialog.CANCEL && wizardMainNewPackageWizardPage.getNewPackageWizardPage().getPackageText()!=null && wizardMainNewPackageWizardPage.getNewPackageWizardPage().getPackageText().toString().trim().equals("")!=true){
				 		txtPackage.setText(wizardMainNewPackageWizardPage.getNewPackageWizardPage().getPackageText());
				 		EclipseGeneratorUtil.javaEntityPackage=txtPackage.getText();
				 	}
				 	
				 validatePageComplete();
				 
			}
		});
		btnNewPackage.setBounds(393, 90, 87, 27);
		btnNewPackage.setText("New...");
		
		Button bRadioJPAReverseEngineering = new Button(container, SWT.RADIO);
		bRadioJPAReverseEngineering.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDescription("Generate JPA entity beans from database explorer tables");
				EclipseGeneratorUtil.makeItXml=false;
				loadListGeneratorsNextWizard();
			}
		});
		bRadioJPAReverseEngineering.setBounds(10, 246, 189, 22);
		bRadioJPAReverseEngineering.setText("JPA Reverse Engineering");
		
		Button bRadioHibernateReverseEngineering = new Button(container, SWT.RADIO);
		bRadioHibernateReverseEngineering.setSelection(true);
		bRadioHibernateReverseEngineering.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDescription("Generate Hibernate mapping and Java classes from database explorer tables");
				EclipseGeneratorUtil.makeItXml=true;
				loadListGeneratorsNextWizard();
			}
		});
		bRadioHibernateReverseEngineering.setBounds(10, 218, 232, 22);
		bRadioHibernateReverseEngineering.setText("Hibernate Reverse Engineering");
		
		Button bRadioPOJOReverseEngineering = new Button(container, SWT.RADIO);
		bRadioPOJOReverseEngineering.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadListGeneratorsNextWizard();
			}
		});
		bRadioPOJOReverseEngineering.setEnabled(false);
		bRadioPOJOReverseEngineering.setBounds(10, 274, 189, 16);
		bRadioPOJOReverseEngineering.setText("POJO Reverse Engineering");
		
		
	}
	
	/**
	 * Handle browse source folder.
	 */
	private void handleBrowseSourceFolder() {
		
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), project, false,	"Choose a source folder:");	
		dialog.showClosedProjects(false);
		

		if (dialog.open() == Window.OK) {
		
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				
				    txtJavaSourceFolder.setText(((Path) result[0]).toString());
					//String fullPathWorkspace = GeneratorUtil.replaceAll(project.getLocation().toString(), project.getFullPath().toString(), "");
					String fullPathWorkspace = project.getLocation().toString().replaceAll(project.getFullPath().toString(), "");
					EclipseGeneratorUtil.fullPathProject=project.getLocation().toString();
					EclipseGeneratorUtil.project=project;
					EclipseGeneratorUtil.projectName=EclipseGeneratorUtil.project.getName();
					EclipseGeneratorUtil.workspaceFolderPath=fullPathWorkspace;
					
					EclipseGeneratorUtil.javaSourceFolderPath = EclipseGeneratorUtil.workspaceFolderPath+txtJavaSourceFolder.getText()+GeneratorUtil.slash;
					btnPackage.setEnabled(true);
					btnNewPackage.setEnabled(true);
				
			}
		}
	}
	
	/**
	 * Handle browse package.
	 */
	private void handleBrowsePackage() {

		
		IPackageFragment[] packages =null;
		IJavaProject javaProject = JavaCore.create(project);
		IJavaElement javaElementArray[]=null;
		ArrayList<IJavaElement>	javaElementsList=new ArrayList<IJavaElement>();
		
		
		//Si el projecto no esta abierto se cancela el proceso
		if(javaProject.isOpen()==false){
			MessageDialog.openError(getShell(), "Error", "The project is not open");
			return;
		}
		
		//Lee los paquetes solo del proyecto
		try {
			packages = javaProject.getPackageFragments();
			
			for (IPackageFragment iPackageFragment : packages) {
				if(iPackageFragment.getKind() == IPackageFragmentRoot.K_SOURCE){
					javaElementsList.add(iPackageFragment);
				}
			}		
			if(javaElementsList.size()>0){
				javaElementArray=new IJavaElement[javaElementsList.size()];
				javaElementArray=javaElementsList.toArray(javaElementArray);
			}
		} catch (JavaModelException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
		
		
			Shell shell = getShell();
			IJavaSearchScope iJavaSearchScope = SearchEngine.createJavaSearchScope(javaElementArray,false);
			PackageSelectionDialog packageSelectionDialog = new PackageSelectionDialog(shell, new ProgressMonitorDialog(shell),PackageSelectionDialog.F_REMOVE_DUPLICATES|PackageSelectionDialog.F_HIDE_EMPTY_INNER,iJavaSearchScope);
			
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
		/**
		 * Validate page complete.
		 */
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
				ZathuraReverseEngineeringUtil.validarPackage(txtPackage.getText());				
				
				EclipseGeneratorUtil.companyDomainName = txtPackage.getText();
				EclipseGeneratorUtil.destinationDirectory = txtJavaSourceFolder.getText();
				
				setPageComplete(true);
				setErrorMessage(null);
				
			} catch (Exception e) {
				setPageComplete(false);
				setErrorMessage(e.getMessage());
			}
		
		
	}
		
		
		/**
		 * Handle browse web root folder.
		 */
		private void handleBrowseWebRootFolder() {
			ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), project, false,	"Choose a WebContent folder:");
			dialog.showClosedProjects(false);
			
			if (dialog.open() == Window.OK) {
				Object[] result = dialog.getResult();
				if (result.length == 1) {
					txtWebRoot.setText(((Path) result[0]).toString());
					EclipseGeneratorUtil.webRootFolderPath=EclipseGeneratorUtil.workspaceFolderPath+txtWebRoot.getText()+GeneratorUtil.slash;
					
				}
			}
		}
		
		/**
		 * Handle browse lib folder.
		 */
		private void handleBrowseLibFolder() {
			ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), project, false,	"Choose a Libraries folder:");
			dialog.showClosedProjects(false);
			
			if (dialog.open() == Window.OK) {
				Object[] result = dialog.getResult();
				if (result.length == 1) {
					txtLib.setText(((Path) result[0]).toString());
					EclipseGeneratorUtil.libFolderPath=EclipseGeneratorUtil.workspaceFolderPath+txtLib.getText()+org.zathuracode.generator.utilities.GeneratorUtil.slash;				
				}
			}
		}
		
		/**
		 * Load list generators next wizard.
		 */
		private void loadListGeneratorsNextWizard(){
			Object object=getNextPage();
			if(object instanceof WizardPageChooseGenerator){
				WizardPageChooseGenerator wizardChooseGenerator=(WizardPageChooseGenerator)object;
				wizardChooseGenerator.loadListGeneratorsVersion3_0();
				wizardChooseGenerator.loadListGeneratorsVersion3_1();
			}
			
		}
}
