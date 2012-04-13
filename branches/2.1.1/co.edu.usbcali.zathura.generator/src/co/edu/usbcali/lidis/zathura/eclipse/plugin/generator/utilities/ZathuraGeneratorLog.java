package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.ZathuraGeneratorActivator;

/**
 * 
 * @author Diego Armando Gomez Mosquera dgomez@vortexbird.com
 * 
 */

public class ZathuraGeneratorLog {

	/**
	 * 
	 * @param message
	 */
	public static void logInfo(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}

	/**
	 * 
	 * @param exception
	 */
	public static void logError(Throwable exception) {
		logError("Unexpected Exception", exception);
	}

	/**
	 * 
	 * @param message
	 * @param exception
	 */
	public static void logError(String message, Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}

	/**
	 * 
	 * @param severity
	 * @param code
	 * @param message
	 * @param exception
	 */
	public static void log(int severity, int code, String message, Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	/**
	 * 
	 * @param severity
	 * @param code
	 * @param message
	 * @param exception
	 * @return
	 */
	public static IStatus createStatus(int severity, int code, String message, Throwable exception) {
		IStatus status = new Status(severity, ZathuraGeneratorActivator.PLUGIN_ID, code, message, exception);
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public static void log(IStatus status) {
		ZathuraGeneratorActivator.getDefault().getLog().log(status);
	}

}
