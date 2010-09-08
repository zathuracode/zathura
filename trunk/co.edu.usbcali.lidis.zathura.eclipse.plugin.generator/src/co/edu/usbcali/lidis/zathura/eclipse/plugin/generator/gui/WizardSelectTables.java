package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.gui;


import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.reverse.utilities.ZathuraReverseEngineeringUtil;

import com.swtdesigner.ResourceManager;
import org.eclipse.swt.widgets.Text;
/**
 * Zathura Generator
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see WizardPage
 */
public class WizardSelectTables extends WizardPage {
	
	private Combo cmbCatlogSchema;
	private List listAvailableTables;
	private List listSelectedTables;
	private Text txtTableFilter;
	private java.util.List<String> listCatalogs;
	private java.util.List<String> listSchemas;

	public WizardSelectTables() {
		super("wizardPage");
		setTitle("Zathura Reverse Engineering");
		setDescription("Generate entity beans from database explorer tables");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(ZathuraGeneratorActivator.getDefault(), "icons/NewRDBDatabaseWiz.gif"));
		setPageComplete(false);
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);	
		setControl(container);
		
		Label lblCatalogSchema = new Label(container, SWT.NONE);
		lblCatalogSchema.setBounds(10, 13, 112, 17);
		lblCatalogSchema.setText("Catalogs/Schemas:");
		
		cmbCatlogSchema = new Combo(container, SWT.NONE);
		cmbCatlogSchema.setBounds(128, 10, 332, 29);
		
		listAvailableTables = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		listAvailableTables.setBounds(10, 101, 195, 206);
		
		Label lblAvailableTables = new Label(container, SWT.NONE);
		lblAvailableTables.setBounds(10, 78, 154, 17);
		lblAvailableTables.setText("Available tables:");
		
		listSelectedTables = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		listSelectedTables.setBounds(383, 101, 195, 206);
		
		Label lblSelectedTables = new Label(container, SWT.NONE);
		lblSelectedTables.setBounds(384, 78, 194, 17);
		lblSelectedTables.setText("Selected tables:");
		
		Button btnUpdateList = new Button(container, SWT.NONE);
		btnUpdateList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try {
					txtTableFilter.setText("%");
					cmbCatlogSchema.removeAll();
					listAvailableTables.removeAll();
					listSelectedTables.removeAll();
				
					listCatalogs = ZathuraReverseEngineeringUtil.getCatalogs();
					for (String catalogSchemaName : listCatalogs) {
						cmbCatlogSchema.add(catalogSchemaName);
					}
					listSchemas = ZathuraReverseEngineeringUtil.getSchemas();
					for (String catalogSchemaName : listSchemas) {
						cmbCatlogSchema.add(catalogSchemaName);
					}
					validatePageComplete();
				} catch (SQLException e1) {	
					setPageComplete(false);
				} catch (Exception ex) {
					setPageComplete(false);
				}			
			}
		});
		btnUpdateList.setBounds(466, 10, 98, 25);
		btnUpdateList.setText("Update List");
		
		Button btnAdd = new Button(container, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectionIndices[]=listAvailableTables.getSelection();
				for (String item : selectionIndices) {
					listSelectedTables.add(item);
					listAvailableTables.remove(item);
				}
				validatePageComplete();
			}
		});
		btnAdd.setBounds(237, 137, 112, 29);
		btnAdd.setText("Add -->");
		
		Button btnAddAll = new Button(container, SWT.NONE);
		btnAddAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectionIndices[]=listAvailableTables.getItems();
				for (String item : selectionIndices) {
					listSelectedTables.add(item);
					listAvailableTables.remove(item);
				}
				validatePageComplete();
			}
		});
		btnAddAll.setBounds(237, 172, 112, 29);
		btnAddAll.setText("Add All -->");
		
		Button btnRemove = new Button(container, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectionIndices[]=listSelectedTables.getSelection();
				for (String item : selectionIndices) {
					listAvailableTables.add(item);
					listSelectedTables.remove(item);
				}
				validatePageComplete();				
			}
		});
		btnRemove.setBounds(237, 207, 112, 29);
		btnRemove.setText("<-- Remove");
		
		Button btnRemoveAll = new Button(container, SWT.NONE);
		btnRemoveAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectionIndices[]=listSelectedTables.getItems();
				for (String item : selectionIndices) {
					listAvailableTables.add(item);
					listSelectedTables.remove(item);
				}
				validatePageComplete();
			}
		});
		btnRemoveAll.setBounds(237, 242, 112, 29);
		btnRemoveAll.setText("<-- Remove All");
		
		txtTableFilter = new Text(container, SWT.BORDER);
		txtTableFilter.setText("%");
		txtTableFilter.setBounds(128, 39, 332, 23);
		
		Label lblTableFilter = new Label(container, SWT.NONE);
		lblTableFilter.setBounds(10, 42, 112, 15);
		lblTableFilter.setText("Table Filter:");
		
		Button btnTableFilterSearch = new Button(container, SWT.NONE);
		btnTableFilterSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					listAvailableTables.removeAll();
					listSelectedTables.removeAll();
					java.util.List<String> listTables=ZathuraReverseEngineeringUtil.getTables(cmbCatlogSchema.getText(),txtTableFilter.getText());
					for (String tableName : listTables) {
						listAvailableTables.add(tableName);
					}
				} catch (Exception e2) {
					//ignore
					setPageComplete(false);
				}
			}
		});
		btnTableFilterSearch.setBounds(466, 37, 98, 25);
		btnTableFilterSearch.setText("Search");
		
	}
	
	private void validatePageComplete(){
		
		
		try {
			if(listSelectedTables!=null && listSelectedTables.getItemCount()>0){
				setPageComplete(true);
				
				
				//Si es por Catalogs
				if(listCatalogs!=null && listCatalogs.size()>0){
					EclipseGeneratorUtil.defaultSchema=cmbCatlogSchema.getText();
					EclipseGeneratorUtil.matchSchemaForTables=null;
					EclipseGeneratorUtil.tablesList=null;
				}
				//Si es por schema
				if(listSchemas!=null && listSchemas.size()>0){
					EclipseGeneratorUtil.defaultSchema=null;
					EclipseGeneratorUtil.matchSchemaForTables=cmbCatlogSchema.getText();
					EclipseGeneratorUtil.tablesList=loadTablesList();
				}
				
				/*
				if(listCatalogs!=null && listCatalogs.size()>0 && listSchemas!=null && listSchemas.size()>0){
					EclipseGeneratorUtil.defaultSchema=cmbCatlogSchema.getText();
					EclipseGeneratorUtil.matchSchemaForTables=cmbCatlogSchema.getText();
					EclipseGeneratorUtil.tablesList=loadTablesList();
				}
				*/
					
				
			}else{
				throw new Exception("Table not selected");
			}
			
			setPageComplete(true);
			setErrorMessage(null);
			
		} catch (Exception e) {
			setPageComplete(false);
			setErrorMessage(e.getMessage());
		}
		
		
	}
	
	private java.util.List<String> loadTablesList(){
		java.util.List<String> tablesList=new ArrayList<String>();
		String[] tableItems=listSelectedTables.getItems();
		for (String tableName : tableItems) {
			tablesList.add(tableName);
		}
		return tablesList;
	}
}
