package co.edu.usbcali.lidis.zathura.eclipse.plugin.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import co.edu.usbcali.lidis.zathura.eclipse.gui.reverse.jpa.WizardMainReverseJPA;
import co.edu.usbcali.lidis.zathura.eclipse.utilities.ConfigEclipsePluginPath;

public class ZathuraJPAReverseEngineeringAction implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	
	
	public ZathuraJPAReverseEngineeringAction() {
		ConfigEclipsePluginPath.getInstance();
	}

	public void dispose() {
		
		
	}

	
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	
	public void run(IAction arg0) {
		WizardMainReverseJPA wizardMainReverseJPA=new WizardMainReverseJPA();
		WizardDialog dialog = new WizardDialog(window.getShell(), wizardMainReverseJPA);
        dialog.create();
        dialog.open();
		
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// TODO Auto-generated method stub
		
	}

}
