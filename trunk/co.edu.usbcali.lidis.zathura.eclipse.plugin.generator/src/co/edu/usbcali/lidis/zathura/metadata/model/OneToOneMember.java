
package co.edu.usbcali.lidis.zathura.metadata.model;

/**
 * 
 * @author Diego Armando Gomez Mosquera
 *
 */
public class OneToOneMember extends Member {

	/**
	 * @param name
	 * @param type
	 * @param order
	 */
	public OneToOneMember(String name, String showName,Class type, int order) {
		super(name,showName, type, order);
	}

}