package co.edu.usbcali.lidis.zathura.reverse.utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReverseUtil {

	private static String fullPath = "";
	public static String slash = System.getProperty("file.separator");
	public static String revEngFileName = "hibernate.reveng.xml";

	private static String reverseTemplates = "reverseTemplates"
			+ ReverseUtil.slash;
	private static String tempFiles = "tempFiles" + ReverseUtil.slash;
	private static String antRevEng = "antBuild-revEng" + ReverseUtil.slash;
	
	public static String getFullPath() {
		return fullPath;
	}
	public static void setFullPath(String fullPath) {
		ReverseUtil.fullPath = fullPath;
	}

	public static String getReverseTemplates() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + reverseTemplates;
		}
		return reverseTemplates;
	}

	public static String getTempFilesPath() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + tempFiles;
		}
		return reverseTemplates;
	}

	public static String getAntBuildRevEngPath() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + antRevEng;
		}
		return reverseTemplates;
	}

	public static String fixDomain(String domainName) {
		String retFixDomian = null;
		if(domainName == null){
			return "";
		}
		if(domainName.length()>0){
			retFixDomian = replaceAll(domainName, ".", ReverseUtil.slash);
		}
		return retFixDomian;
	}
	
	/**
	 * 
	 * @param cadena
	 * @param old
	 * @param snew
	 * @return
	 */
	public static String replaceAll (String cadena,String old,String snew) {
		StringBuffer replace= new StringBuffer();
		String aux;
	
		for (int i = 0; i < cadena.length(); i++) {
			if ( (i+old.length()) <cadena.length() ){
				aux =  cadena.substring(i,i+old.length());
				if (aux.equals(old)){
					replace.append(snew);
					i+=old.length()-1;
				}else{
					replace.append(cadena.substring(i,i+1));
				}
			}else
				replace.append(cadena.substring(i,i+1));
		}
		return replace.toString();
	}
	
	public static String projectPathInConsole(){
		URL url = ReverseUtil.class.getResource("ReverseUtil.class");
		String classPath = url.getPath().substring(1);
		int tmp = classPath.indexOf("zathura-ReverseMappingTool")+26;
		
		String inconmpletePath =  classPath.substring(0, tmp);
		String cPath = ""+classPath.charAt(tmp);
		String projectPath = null;
		
		if(cPath.equals("2")){
			projectPath = inconmpletePath+"2"+ReverseUtil.slash;
		}else{
			projectPath = inconmpletePath+ReverseUtil.slash;
		}		
		return projectPath;
	}
	
	public static boolean validationsList(List list) {
		if (list != null) {
			if (!list.isEmpty() && list.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static List<String> fillTableList(){
		List<String> tablesList = new ArrayList<String>();
		
		tablesList.add("CLIENTES");
		tablesList.add("CONSIGNACIONES");
		tablesList.add("CUENTAS");
		tablesList.add("RETIROS");
		tablesList.add("TIPOS_DOCUMENTOS");
		tablesList.add("TIPOS_USUARIOS");
		tablesList.add("USUARIOS");
		
		return tablesList;
	}

}
