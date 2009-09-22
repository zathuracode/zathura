package co.edu.usbcali.lidis.zathura.eclipse.gui;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.Activator;
import co.edu.usbcali.lidis.zathura.eclipse.utilities.EclipseUtil;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;

import com.swtdesigner.ResourceManager;

public class WizardChoosePath extends WizardPage {

	private Text txtLib;
	private Text txtWebRoot;
	private Text txtJavaSourceFolder;
	
	
	/**
	 * Create the wizard
	 */
	public WizardChoosePath() {
		super("wizardPage");
		setTitle("Zathura Java Code Generator");
		setDescription("Configure Path Folder for Src, WebContent and Libraries ");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.getDefault(), "icons/balvardi-Robotic7070.png"));
		setPageComplete(false);
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
		choosePathGroup.setBounds(0, 0, 581, 155);

		final Label lblJavaSourceFolder = new Label(choosePathGroup, SWT.NONE);
		lblJavaSourceFolder.setText("Java Source:");
		lblJavaSourceFolder.setBounds(10, 31, 99, 17);

		txtJavaSourceFolder = new Text(choosePathGroup, SWT.BORDER);
		txtJavaSourceFolder.setEditable(false);
		txtJavaSourceFolder.setBounds(115, 31, 363, 29);

		final Button btnJavaSourceFolder = new Button(choosePathGroup, SWT.NONE);
		btnJavaSourceFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseSourceFolder();
				validatePageComplete();
			}
		});
		btnJavaSourceFolder.setText("Browse...");
		btnJavaSourceFolder.setBounds(484, 31, 87, 29);

		final Label lblWebRoot = new Label(choosePathGroup, SWT.NONE);
		lblWebRoot.setText("WebContent:");
		lblWebRoot.setBounds(10, 66, 87, 17);

		txtWebRoot = new Text(choosePathGroup, SWT.BORDER);
		txtWebRoot.setEditable(false);
		txtWebRoot.setBounds(115, 66, 363, 29);

		final Button btnWebRoot = new Button(choosePathGroup, SWT.NONE);
		btnWebRoot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseWebRootFolder();
				validatePageComplete();
			}
		});
		btnWebRoot.setText("Browse...");
		btnWebRoot.setBounds(484, 66, 87, 29);

		txtLib = new Text(choosePathGroup, SWT.BORDER);
		txtLib.setEditable(false);
		txtLib.setBounds(115, 105, 363, 29);

		final Button btnLib = new Button(choosePathGroup, SWT.NONE);
		btnLib.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseLibFolder();
				validatePageComplete();
			}
		});
		btnLib.setText("Browse...");
		btnLib.setBounds(484, 105, 87, 29);

		final Label lblLib = new Label(choosePathGroup, SWT.NONE);
		lblLib.setText("Libraries:");
		lblLib.setBounds(10, 105, 87, 17);
		
		
	}
	private void handleBrowseSourceFolder() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,	"Select source file container");
		
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				txtJavaSourceFolder.setText(((Path) result[0]).toString());
				EclipseUtil.javaSourceFolderPath = EclipseUtil.workspaceFolderPath+txtJavaSourceFolder.getText()+GeneratorUtil.slash;				
			}
		}
	}
	private void handleBrowseWebRootFolder() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,	"Select WebContent Folder");
		
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				txtWebRoot.setText(((Path) result[0]).toString());
				EclipseUtil.webRootFolderPath=EclipseUtil.workspaceFolderPath+txtWebRoot.getText()+GeneratorUtil.slash;
			}
		}
	}
	private void handleBrowseLibFolder() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,	"Select Libraries Folder");
		
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				txtLib.setText(((Path) result[0]).toString());
				EclipseUtil.libFolderPath=EclipseUtil.workspaceFolderPath+txtLib.getText()+GeneratorUtil.slash;				
			}
		}
	}
	//Se usa para validar si la pagina se completo y poder activar el next o el finish segun sea necesario
	private void validatePageComplete(){
		
		if(txtLib!=null && txtWebRoot!=null && txtJavaSourceFolder!=null && 
		   txtLib.getText().equals("")!=true && txtWebRoot.getText().equals("")!=true && txtJavaSourceFolder.getText().equals("")!=true){
			setPageComplete(true);
		}else{
			setPageComplete(false);
		}
	}

}
