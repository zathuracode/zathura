package co.edu.usbcali.lidis.zathura.metadata.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.functors.IfClosure;


/**
 * 
 * @author Diego Armando Gomez Mosquera
 *
 */
public class MetaData {

	private String name;
	private Class<Object> mainClass;
	private Member primaryKey;

	private List<Member> properties;
	private Class composeKey;
	
	private String finamParam;
	private String finamParamVariables;
	private String finalParamForId;
	private String finalParamForIdVariables;
	
	public String getId() {
		return getMainClass().getName().replaceAll("\\.", "_");
	}
	public String getRealClassName(){
		String []tmp=(name.replace(".", "%")).split("%");
		String realName=tmp[tmp.length-1];
		return realName;
	}

	public String getRealClassNameAsVariable(){
		String []tmp=(name.replace(".", "%")).split("%");
		String realName=tmp[tmp.length-1];
		return (realName.substring(0, 1).toLowerCase())+realName.substring(1, realName.length());
	}	

	public List<Member> getProperties() {
		return properties;
	}
	public void setProperties(List<Member> properties) {
		this.properties = properties;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Class<Object> getMainClass() {
		return mainClass;
	}

	public void setMainClass(Class<Object> mainClass) {
		this.mainClass = mainClass;
	}

	public Member getPrimaryKey() {
		return primaryKey;
	}

	public Class getComposeKey() {
		return composeKey;
	}
	public void setComposeKey(Class composeKey) {
		this.composeKey = composeKey;
	}
	public void setPrimaryKey(Member primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getFinamParam() {
		return finamParam;
	}
	public void setFinamParam(String finamParam) {
		this.finamParam = finamParam;
	}
	public String getFinamParamVariables() {
		return finamParamVariables;
	}
	public void setFinamParamVariables(String finamParamVariables) {
		this.finamParamVariables = finamParamVariables;
	}
	
	public String getFinalParamForId() {
		return finalParamForId;
	}
	public void setFinalParamForId(String finalParamForId) {
		this.finalParamForId = finalParamForId;
	}
	public String getFinalParamForIdVariables() {
		return finalParamForIdVariables;
	}
	public void setFinalParamForIdVariables(String finalParamForIdVariables) {
		this.finalParamForIdVariables = finalParamForIdVariables;
	}
	public boolean hasComposeKey(){
		if(composeKey!=null)
			return true;
		else
			return false;
	}
	
	public String getRealClassNameForComposeKey(){
		String realClassName = new String();
		if(hasComposeKey()){
			realClassName = getRealClassName(composeKey.getName());
		}
		
		return realClassName;
	}
	
	public List<Member> getSimpleProperties() {
		List<Member> ret = new ArrayList<Member>();
		for(Member m:properties)
			if (m instanceof SimpleMember) ret.add(m);
		return ret;
	}
	
	public boolean isGetSimpleProperties(){
		if(getSimpleProperties()!=null){
			if(!getSimpleProperties().isEmpty() && getSimpleProperties().size()>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}	

	public List<Member> getManyToOneProperties() {
		List<Member> ret = new ArrayList<Member>();
		for(Member m:properties)
			if (m instanceof ManyToOneMember) ret.add(m);
		return ret;
	}

	public boolean isGetManyToOneProperties(){
		if(getManyToOneProperties()!=null){
			if(!getManyToOneProperties().isEmpty() && getManyToOneProperties().size()>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}	

	public List<Member> getOneToManyProperties() {
		List<Member> ret = new ArrayList<Member>();
		for(Member m:properties)
			if (m instanceof OneToManyMember) ret.add(m);
		return ret;
	}

	public boolean isGetOneToManyProperties(){
		if(getOneToManyProperties()!=null){
			if(!getOneToManyProperties().isEmpty() && getOneToManyProperties().size()>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public List<Member> getOneToOneProperties() {
		List<Member> ret = new ArrayList<Member>();
		for(Member m:properties)
			if (m instanceof OneToOneMember) ret.add(m);
		return ret;
	}

	public boolean isGetOneToOneProperties(){
		if(getOneToOneProperties()!=null){
			if(!getOneToOneProperties().isEmpty() && getOneToOneProperties().size()>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}	

	public List<Member> getManyToManyProperties() {
		List<Member> ret = new ArrayList<Member>();
		for(Member m:properties)
			if (m instanceof ManyToManyMember) ret.add(m);
		return ret;
	}

	public boolean isGetManyToManyProperties(){
		if(getOneToOneProperties()!=null){
			if(!getOneToOneProperties().isEmpty() && getOneToOneProperties().size()>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public String primaryKeyProperties(){
		
		String finalParam = new String();
		if(hasComposeKey()){
			String realClassName = getRealClassName(composeKey.getName());
			String variableName = primaryKey.getName();
			
			finalParam = realClassName+" "+variableName;
		}
		return finalParam;
	}
	
	public String getRealClassName(String name){
		String []tmp=(name.replace(".", "%")).split("%");
		String realName=tmp[tmp.length-1];
		return realName;
	}

}


