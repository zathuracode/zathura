package co.edu.usbcali.lidis.zathura.eclipse.plugin.generator.utilities;

public class ConnectionModel {

	private String driverTemplate;
	private String name;
	private String url;
	private String user;
	private String password;
	private String driverClassName;
	private String jarPath;

	public ConnectionModel() {

	}

	public ConnectionModel(String driverTemplate, String name, String url, String user, String password, String driverClassName, String jarPath) {
		super();
		this.driverTemplate = driverTemplate;
		this.name = name;
		this.url = url;
		this.user = user;
		this.password = password;
		this.driverClassName = driverClassName;
		this.jarPath = jarPath;
	}

	public String getJarPath() {
		return jarPath;
	}

	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getDriverTemplate() {
		return driverTemplate;
	}

	public void setDriverTemplate(String driverTemplate) {
		this.driverTemplate = driverTemplate;
	}

}
