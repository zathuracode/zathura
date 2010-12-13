package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.test;

import java.util.HashMap;
import java.util.List;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionModel;
import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities.ConnectionsUtils;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;

public class TestConnectionsUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GeneratorUtil.setFullPath("D:\\Workspaces\\workspaceZathura\\co.edu.usbcali.lidis.zathura.eclipse.plugin.generator\\");
		
		List<String> names=ConnectionsUtils.getConnectionNames();
		for (String name : names) {
			System.out.println(name);
			//ConnectionModel connectionModel=ConnectionsUtils.getTheZathuraConnectionModel(name);
			//System.out.println(connectionModel.getUrl());
		}
		
		saveConnection();
			HashMap<String, ConnectionModel> theZathuraConnections=ConnectionsUtils.getTheZathuraConnections();
			System.out.println(theZathuraConnections.size());
		removeConnection("as/400");
		
		
		
			
	
	}
	
	private static void saveConnection(){
		ConnectionModel connectionModel=new ConnectionModel("as/400", "urlas400", "dgomez", "sadjasdk","com.vortexbird.Connection", "d:\\");
		try {
			ConnectionsUtils.saveConnectionModel(connectionModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void removeConnection(String name){
		try {
			ConnectionsUtils.removeConnectionModel(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
