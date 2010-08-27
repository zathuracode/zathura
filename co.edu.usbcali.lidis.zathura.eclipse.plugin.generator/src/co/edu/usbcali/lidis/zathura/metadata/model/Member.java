package co.edu.usbcali.lidis.zathura.metadata.model;

import java.util.HashMap;

/**
 * 
 * @author diegomez
 * Basado en jpa2web
 *
 */
public class Member implements Comparable {
	
	private String name;
	private Class type;
	private int order=-1;
	private String showName;
	
	private Long precision;
	private Long scale;
	Boolean nullable;
	private Long length;
	
	private HashMap<String, Member> hashMapIdsProperties = new HashMap<String, Member>();
	/**
	 * 
	 */
	public Member() {

	}

	/**
	 * @param name
	 * @param type
	 * @param order
	 */
	
	public String getRealClassName(){
		String typeComplete = type.getName();
		String []tmp=(typeComplete.replace(".", "%")).split("%");
		String realName=tmp[tmp.length-1];
		return realName;
	}
	
	public String getRealClassVariableName(){
		String typeComplete = type.getName();
		String []tmp=(typeComplete.replace(".", "%")).split("%");
		String realName=tmp[tmp.length-1];
		return (realName.substring(0,1)).toLowerCase()+realName.substring(1,realName.length());
	}	
	
	public String getGetNameOfPrimaryName(){
		String build = name.substring(0,1).toUpperCase();
		String build2 = name.substring(1,name.length());
		return build+build2;
	}
	
	public Member(String name, String showName,Class type, int order) {
		super();
		this.name = name;
		this.type = type;
		this.order = order;
		this.showName=showName;
	}
	
	public boolean isPrimiaryKeyAComposeKey(){
		boolean ret = false;
		
		String firstLetters = type.getName();
		String []tmp=(firstLetters.replace(".", "%")).split("%");
		
		
		if(tmp!=null){
			if(tmp.length>1){
				//Is possible are java.lang.*, or java.util.* or java.math
				
				if( (tmp[0].equalsIgnoreCase("java") && tmp[1].equalsIgnoreCase("lang")) || 
						(tmp[0].equalsIgnoreCase("java") && tmp[1].equalsIgnoreCase("util")) ||
						(tmp[0].equalsIgnoreCase("java") && tmp[1].equalsIgnoreCase("math"))){
					
					ret = false;
				}else{
					ret = true;
				}
			}else{
				ret = false;
			}
		}
		
		return ret;
	}
	
	public boolean isSimpleMember(){
		boolean ret = false;
		
		try {
			if(this instanceof SimpleMember)
				ret = true;
			else
				ret = false;
		} catch (Exception e) {
			ret = false;
		}
		
		return ret;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Object o) {
		
		Member m =(Member)o;
		if ((order==-1)&&(m.getOrder()==-1)) {
			return getName().compareTo(m.getName()); 
		}
		else if (m.getOrder()==-1) {
			return -1;
		}
		else if (getOrder()==-1) {
			return 1;
		}
		else {
			return new Integer(order).compareTo(new Integer(m.getOrder()));
		}
			
	}
	
	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Long getPrecision() {
		return precision;
	}

	public void setPrecision(Long precision) {
		this.precision = precision;
	}

	public Long getScale() {
		return scale;
	}

	public void setScale(Long scale) {
		this.scale = scale;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public HashMap<String, Member> getHashMapIdsProperties() {
		return hashMapIdsProperties;
	}

	public void setHashMapIdsProperties(HashMap<String, Member> hashMapIdsProperties) {
		this.hashMapIdsProperties = hashMapIdsProperties;
	}

}
