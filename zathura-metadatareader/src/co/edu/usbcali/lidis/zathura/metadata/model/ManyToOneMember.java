
package co.edu.usbcali.lidis.zathura.metadata.model;

import java.util.HashMap;

/**
 * 
 * @author diegomez
 *
 */
public class ManyToOneMember extends Member {
	
	private HashMap<String, Boolean> hashMapNullableColumn;
	
	public ManyToOneMember(String name,String showName, Class type,int order) {
		super(name, showName,type,order);
		
	}

	public HashMap<String, Boolean> getHashMapNullableColumn() {
		return hashMapNullableColumn;
	}

	public void setHashMapNullableColumn(
			HashMap<String, Boolean> hashMapNullableColumn) {
		this.hashMapNullableColumn = hashMapNullableColumn;
	}
	
	

}
