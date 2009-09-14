package co.edu.usbcali.lidis.zathura.generator.model;

public class GeneratorModel {
	
	private String name;
	private String guiName;
	private String description;
	private IZathuraGenerator zathuraGenerator;
	
	public String getGuiName() {
		return guiName;
	}
	public void setGuiName(String guiName) {
		this.guiName = guiName;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public IZathuraGenerator getZathuraGenerator() {
		return zathuraGenerator;
	}
	public void setZathuraGenerator(IZathuraGenerator zathuraGenerator) {
		this.zathuraGenerator = zathuraGenerator;
	}
	
	

}
