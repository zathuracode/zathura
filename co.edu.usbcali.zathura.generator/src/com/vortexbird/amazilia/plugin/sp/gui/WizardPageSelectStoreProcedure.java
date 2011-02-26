package com.vortexbird.amazilia.plugin.sp.gui;


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
import org.eclipse.swt.widgets.Text;

import com.vortexbird.amazilia.sp.metadata.MetadataStoreProcedureHandler;
import com.vortexbird.amazilia.sp.metadata.MetadataStoreProcedureHandlerFactory;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import co.edu.usbcali.lidis.zathura.reverse.engine.ZathuraReverseEngineering;
import co.edu.usbcali.lidis.zathura.reverse.utilities.ZathuraReverseEngineeringUtil;
/**
 * Zathura Generator
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 * @see WizardPage
 */
public class WizardPageSelectStoreProcedure extends WizardPage {
	
	private Combo cmbCatlogSchema;
	private List listAvailableStoreProcedures;
	private List listSelectedStoreProcedures;
	private Text txtStoreProceduresFilter;
	private Label lblCatalogSchema;
	
	private Button btnStoreProcedureFilterSearch;
	private String[] listCatalogs=null;
	private String[] listSchemas=null;
	private String catalogName=null;
	private Boolean isSchema=null;
	
	
	///Se debe cambiar por fabrica de obejtos para la version 2.1.1
	//TODO revisar esta locura
	public static String db=null;
	
	

	public WizardPageSelectStoreProcedure() {
		super("wizardPage");
		setTitle("Zathura Reverse Engineering");
		setDescription("Generate Hibernate or JDBC Store Procedure Call");
		setPageComplete(false);
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);	
		setControl(container);
		
		lblCatalogSchema = new Label(container, SWT.NONE);
		lblCatalogSchema.setBounds(10, 13, 112, 17);
		lblCatalogSchema.setText("Catalogs/Schemas:");
		
		cmbCatlogSchema = new Combo(container, SWT.NONE);
		cmbCatlogSchema.setEnabled(false);
		cmbCatlogSchema.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				txtStoreProceduresFilter.setText("%");
				listAvailableStoreProcedures.removeAll();
				listSelectedStoreProcedures.removeAll();
				btnStoreProcedureFilterSearch.setEnabled(true);
				
				validatePageComplete();
				
				
			}
		});
		cmbCatlogSchema.setBounds(128, 10, 332, 29);
		
		listAvailableStoreProcedures = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		listAvailableStoreProcedures.setBounds(10, 101, 195, 206);
		
		Label lblAvailableStoreProcedures = new Label(container, SWT.NONE);
		lblAvailableStoreProcedures.setBounds(10, 78, 154, 17);
		lblAvailableStoreProcedures.setText("Available Store Procedure:");
		
		listSelectedStoreProcedures = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		listSelectedStoreProcedures.setBounds(383, 101, 195, 206);
		
		Label lblSelectedStoreProcedures = new Label(container, SWT.NONE);
		lblSelectedStoreProcedures.setBounds(384, 78, 194, 17);
		lblSelectedStoreProcedures.setText("Selected Store Procedure:");
		
		Button btnUpdateList = new Button(container, SWT.NONE);
		btnUpdateList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try {
					resetForm();
				
					
					listCatalogs = ZathuraReverseEngineeringUtil.getCatalogs();
					listSchemas = ZathuraReverseEngineeringUtil.getSchemas();
					
					if(listSchemas!=null && listSchemas.length>1){
						if(listCatalogs!=null && listCatalogs.length==1){
							catalogName=listCatalogs[0];
						}else{
							catalogName=null;
						}
						
						isSchema=true;
						lblCatalogSchema.setText("Schemas:");
						for (String catalogSchemaName : listSchemas) {
							cmbCatlogSchema.add(catalogSchemaName);
						}
					}else if(listCatalogs!=null && listCatalogs.length>0){
						isSchema=false;
						lblCatalogSchema.setText("Catalogs:");
						for (String catalogSchemaName : listCatalogs) {
							cmbCatlogSchema.add(catalogSchemaName);
						}
						
					}
					if(cmbCatlogSchema.getItems()!=null && cmbCatlogSchema.getItems().length>0){
						cmbCatlogSchema.setEnabled(true);
						cmbCatlogSchema.select(0);
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
				String selectionIndices[]=listAvailableStoreProcedures.getSelection();
				for (String item : selectionIndices) {
					listSelectedStoreProcedures.add(item);
					listAvailableStoreProcedures.remove(item);
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
				String selectionIndices[]=listAvailableStoreProcedures.getItems();
				for (String item : selectionIndices) {
					listSelectedStoreProcedures.add(item);
					listAvailableStoreProcedures.remove(item);
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
				String selectionIndices[]=listSelectedStoreProcedures.getSelection();
				for (String item : selectionIndices) {
					listAvailableStoreProcedures.add(item);
					listSelectedStoreProcedures.remove(item);
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
				String selectionIndices[]=listSelectedStoreProcedures.getItems();
				for (String item : selectionIndices) {
					listAvailableStoreProcedures.add(item);
					listSelectedStoreProcedures.remove(item);
				}
				validatePageComplete();
			}
		});
		btnRemoveAll.setBounds(237, 242, 112, 29);
		btnRemoveAll.setText("<-- Remove All");
		
		txtStoreProceduresFilter = new Text(container, SWT.BORDER);
		txtStoreProceduresFilter.setText("%");
		txtStoreProceduresFilter.setBounds(128, 39, 332, 23);
		
		Label lblStoreProcedureFilter = new Label(container, SWT.NONE);
		lblStoreProcedureFilter.setBounds(10, 42, 112, 15);
		lblStoreProcedureFilter.setText("Store Procedure Filter:");
		
		btnStoreProcedureFilterSearch = new Button(container, SWT.NONE);
		btnStoreProcedureFilterSearch.setEnabled(false);
		btnStoreProcedureFilterSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					listAvailableStoreProcedures.removeAll();
					listSelectedStoreProcedures.removeAll();
					
					//Only Oracle database
					if(isSchema==true && EclipseGeneratorUtil.connectionDriverTemplate.contains("Oracle")==true){
						 MetadataStoreProcedureHandler metadataStoreProcedureHandler =MetadataStoreProcedureHandlerFactory.getInstace().getHandler(EclipseGeneratorUtil.connectionDriverTemplate);
						 java.util.List<String> storeProcedureNames=metadataStoreProcedureHandler.getStoredProcedureNames(ZathuraReverseEngineeringUtil.getSqlConnection().getConnection(), cmbCatlogSchema.getText(), txtStoreProceduresFilter.getText());
						 for (String nameStoreProcedure : storeProcedureNames) {
							 listAvailableStoreProcedures.add(nameStoreProcedure);
						 }
					}else{
						
						/*
						ITableInfo[] tableInfos=ZathuraReverseEngineeringUtil.getTables(cmbCatlogSchema.getText(), null, txtStoreProceduresFilter.getText());
						for (ITableInfo tableInfo : tableInfos) {
							listAvailableStoreProcedures.add(tableInfo.getSimpleName());
						}
						*/
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					setPageComplete(false);
				}
			}
		});
		btnStoreProcedureFilterSearch.setBounds(466, 37, 98, 25);
		btnStoreProcedureFilterSearch.setText("Search");
		
	}
	
	private void validatePageComplete(){
		
		
		try {
			if(listSelectedStoreProcedures!=null && listSelectedStoreProcedures.getItemCount()>0){
				setPageComplete(true);
				
				
				//Si es por Catalogs
				if(listCatalogs!=null && listCatalogs.length>0){
					
					EclipseGeneratorUtil.catalog=cmbCatlogSchema.getText();
					EclipseGeneratorUtil.schema=null;
					EclipseGeneratorUtil.catalogAndSchema=ZathuraReverseEngineering.CATALOG;
					
					EclipseGeneratorUtil.tablesList=loadTablesList();
					
				}
				//Si es por schema
				if(listSchemas!=null && listSchemas.length>0){
					
					EclipseGeneratorUtil.catalog=null;
					EclipseGeneratorUtil.schema=cmbCatlogSchema.getText();
					EclipseGeneratorUtil.catalogAndSchema=ZathuraReverseEngineering.SCHEMA;
					
					EclipseGeneratorUtil.tablesList=loadTablesList();					
				}
				
				
				
				//Ambos para db2/400 usa catalog y schema
				if(listCatalogs!=null && listCatalogs.length>0 && listSchemas!=null && listSchemas.length>0 && db.equals("JTOpen(AS/400)")==true){
					
					EclipseGeneratorUtil.catalog=catalogName;
					EclipseGeneratorUtil.schema=cmbCatlogSchema.getText();
					EclipseGeneratorUtil.catalogAndSchema=ZathuraReverseEngineering.CATALOG_SCHEMA;					
					EclipseGeneratorUtil.tablesList=loadTablesList();				
				}
				
					
				
			}else{
				throw new Exception("Store Procedure not selected");
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
		String[] tableItems=listSelectedStoreProcedures.getItems();
		for (String tableName : tableItems) {
			tablesList.add(tableName);
		}
		return tablesList;
	}
	public void resetForm() {
		txtStoreProceduresFilter.setText("%");
		cmbCatlogSchema.removeAll();
		listAvailableStoreProcedures.removeAll();
		listSelectedStoreProcedures.removeAll();
		cmbCatlogSchema.setEnabled(false);
		lblCatalogSchema.setText("Catalogs/Schemas:");
		setPageComplete(false);
		btnStoreProcedureFilterSearch.setEnabled(false);
	}
}
