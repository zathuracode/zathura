package co.edu.usbcali.lidis.zathura.generator.exceptions;

/**
 * Zathura Generator
 * 
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 */
public class GeneratorNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8547472738527169792L;

	public GeneratorNotFoundException() {
		super("The generator name is not optional");
	}

	public GeneratorNotFoundException(String generatorName) {
		super("The generator " + generatorName + " no Found");
	}
}