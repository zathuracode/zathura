package co.edu.usbcali.lidis.zathura.metadata.utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.apache.log4j.Logger;

import co.edu.usbcali.lidis.zathura.metadata.engine.JPAEntityLoaderEngine;
import co.edu.usbcali.lidis.zathura.metadata.model.Member;

/**
 * 
 * @author diegomez
 *
 */
public class MetaDataUtil {


	private static Logger log=Logger.getLogger(JPAEntityLoaderEngine.class);

	/**
	 * 
	 * @param theMembers
	 * @param memberName
	 */
	public static void removeMember(List<Member> theMembers, String memberName) {
		for(Member member:theMembers){
			if(member.getName().equals(memberName)){
				theMembers.remove(member);
				break;
			}
		}
	}	
	/**
	 * 
	 * @param pckgname
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static List<Class> findEntityInPackage(final String pckgName) throws ClassNotFoundException {        
		List<Class>  ret= new ArrayList<Class>();
		String name = new String(pckgName);
		if (!name.startsWith("/")) {
			name = "/" + name;
		}

		name = name.replace('.','/');

		final URL url = MetaDataUtil.class.getResource(name);
		log.info("Pack URL:"+url);
		final File directory = new File(url.getFile());

		if (directory.exists()) {
			final String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				if (files[i].endsWith(".class")) {
					Class clazz=Class.forName(pckgName+"."+files[i].substring(0,files[i].length()-6));
					if(clazz.getAnnotation(Entity.class)!=null){
						ret.add(clazz);
					}	        	
				}
			}
		}
		return ret;
	}
	/**
	 * 
	 * @param pathName
	 * @param pckgName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Class> findEntityInFolder(final String pathName,final String pckgName) throws ClassNotFoundException, MalformedURLException {
		List<Class>  ret= new ArrayList<Class>();
		
		String realPath=pathName+pckgName.replace('.',File.separatorChar);
		final File directory = new File(realPath);
		log.info("Folder path:"+directory);		
		if (directory.exists()) {
			final String[] files = directory.list();			
			for (int i = 0; i < files.length; i++) {
				if (files[i].endsWith(".class")) {
					Class clazz=loadClassInFolder(pathName,pckgName+"."+files[i].substring(0,files[i].length()-6));
					//Class clazz=Class.forName(pckgName+"."+files[i].substring(0,files[i].length()-6));				
					
					if(clazz.getAnnotation(Entity.class)!=null){
						ret.add(clazz);
					}						
				}
			}
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static Class findClassIdInFolder(final String pathName,final String pckgName, final String className) throws ClassNotFoundException, MalformedURLException {
		Class ret = null;
		
		String realPath=pathName+pckgName.replace('.',File.separatorChar)+File.separatorChar+className+".class";
		final File directory = new File(realPath);
		log.info("Folder path:"+directory);		
		if (directory.exists()) {
			Class clazz=loadClassInFolder(pathName,pckgName+"."+className);
			//Class clazz=Class.forName(pckgName+"."+files[i].substring(0,files[i].length()-6));
			ret = clazz;
		}
		return ret;
	}	
	/**
	 * 
	 * @param pathName
	 * @param clazzName
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static Class loadClassInJar(String pathName,String clazzName) {		
	    try {
	        // Convert File to a URL
	    	File file=new File(pathName);
	        URL url =file.toURL();
	        URL[] urls = new URL[]{url};
	        
	        // Create a new class loader with the directory
	        ClassLoader cl1=MetaDataUtil.class.getClassLoader();
	        ClassLoader cl = new URLClassLoader(urls,cl1);
	        
	        //The clazzName is with package example co.edu.usbcali.zathura.modelo.Cliente
	        Class clazz = cl.loadClass(clazzName);
	        return clazz;
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param pathName
	 * @param clazzName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static Class loadClassInFolder(String pathName,String clazzName) {		 	
		    try {
		        // Convert File to a URL
		    	File file=new File(pathName);
		        URL url =file.toURL();
		        URL[] urls = new URL[]{url};
		    
		        // Create a new class loader with the directory
		        ClassLoader cl1=MetaDataUtil.class.getClassLoader();
		        ClassLoader cl = new URLClassLoader(urls,cl1);
		        
		        Class clazz = cl.loadClass(clazzName);
		        return clazz;
		    } catch (MalformedURLException e) {
		    	e.printStackTrace();
		    } catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		 return null;
	}

}
