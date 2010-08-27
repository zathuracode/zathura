package co.edu.usbcali.lidis.zathura.generator.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;

import co.edu.usbcali.lidis.zathura.generator.exceptions.GeneratorNotFoundException;
import co.edu.usbcali.lidis.zathura.generator.model.GeneratorModel;
import co.edu.usbcali.lidis.zathura.generator.model.IZathuraGenerator;
import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;

/**
 * 
 * @author diegomez
 * 
 */
public class ZathuraGeneratorFactory {

	/**
	 * Log4j
	 */
	private static Logger log = Logger.getLogger(ZathuraGeneratorFactory.class);
	
	/**
	 * xml file path 
	 */
	private static String xmlConfigFactoryPath = GeneratorUtil.getXmlConfigFactoryPath();
	
	/**
	 * Generator Model
	 */
	private static HashMap<String, GeneratorModel> theZathuraGenerators = null;

	/**
	 * The names of generators
	 */
	private static java.util.List<String> generatorNames=new ArrayList<String>();
	
	static {
		try {
			loadZathuraGenerators();
		} catch (FileNotFoundException e) {
			log.fatal(e.getMessage());
			e.printStackTrace();
		} catch (XMLStreamException e) {
			log.fatal(e.getMessage());
			e.printStackTrace();
		} catch (InstantiationException e) {
			log.fatal(e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			log.fatal(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log.fatal(e.getMessage());
			e.printStackTrace();
		}
	}

	private ZathuraGeneratorFactory() {

	}

	/**
	 * 
	 * @param generatorName
	 * @return 
	 * @throws GeneratorNotFoundException
	 */
	public static IZathuraGenerator createZathuraGenerator(String generatorName)throws GeneratorNotFoundException {
		IZathuraGenerator zathuraGenerator;
		if (generatorName == null || generatorName.equals("") == true) {
			throw new GeneratorNotFoundException();
		}
		GeneratorModel generatorModel = theZathuraGenerators.get(generatorName);
		if (generatorModel == null) {
			throw new GeneratorNotFoundException(generatorName);
		}
		zathuraGenerator = generatorModel.getZathuraGenerator();
		return zathuraGenerator;
	}

	/**
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 */
	private static void loadZathuraGenerators() throws FileNotFoundException,XMLStreamException, InstantiationException, IllegalAccessException,ClassNotFoundException {
		
		log.info("Reading:"+GeneratorUtil.getXmlConfigFactoryPath());
		
		GeneratorModel generatorModel = null;
		boolean boolName = false;
		boolean descriptionName = false;
		boolean className = false;
		boolean guiName=false;

		theZathuraGenerators = new HashMap<String, GeneratorModel>();
		
		// Get the factory instace first.		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		 factory.setProperty(
			        XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
			        Boolean.TRUE);
			    factory.setProperty(
			        XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
			        Boolean.FALSE);
			    factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE,
			        Boolean.TRUE);
			    factory.setProperty(XMLInputFactory.IS_COALESCING,
			        Boolean.TRUE);

		
		log.debug("FACTORY: " + factory);

		// create the XMLEventReader, pass the filename for any relative
		//XMLEventReader r = factory.createXMLEventReader(new FileInputStream(xmlConfigFactoryPath));

		
		//xmlConfigFactoryPath="config/zathura-generator-factory-config.xml";
		XMLEventReader r = factory.createXMLEventReader(new FileInputStream(xmlConfigFactoryPath));
		
		// iterate as long as there are more events on the input stream
		while (r.hasNext()) {
			XMLEvent e = r.nextEvent();
			if (e.isStartElement()) {
				StartElement startElement = (StartElement) e;
				QName qname = startElement.getName();
				String localName = qname.getLocalPart();
				if (localName.equals("generator") == true) {
					generatorModel = new GeneratorModel();
				} else if (localName.equals("name") == true) {
					boolName = true;
					log.info(localName);
				} else if (localName.equals("description") == true) {
					descriptionName = true;
					log.info(localName);
				} else if (localName.equals("gui-name") == true) {
					guiName = true;
					log.info(localName);
				} else if (localName.equals("class") == true) {
					className = true;
					log.info(localName);
				}
			} else if (e.isCharacters()) {
				Characters characters = (Characters) e;
				String cadena = characters.getData().toString().trim();
				if(boolName == true) {
					generatorModel.setName(cadena);
					generatorNames.add(cadena);
					boolName = false;
					log.info(cadena);
				} else if (descriptionName == true) {
					generatorModel.setDescription(cadena);
					descriptionName = false;
					log.info(cadena);
				} else if(guiName==true){
					generatorModel.setGuiName(cadena);
					guiName=false;
					log.info(cadena);
				} else if (className == true) {
					generatorModel.setZathuraGenerator((IZathuraGenerator) Class.forName(cadena).newInstance());
					className = false;
					log.info(cadena);
				}
			} else if (e.isEndElement() == true) {
				EndElement endElement = (EndElement) e;
				QName qname = endElement.getName();
				String localName = qname.getLocalPart();
				if (localName.equals("generator") == true) {
					theZathuraGenerators.put(generatorModel.getName(),generatorModel);
				}
			}

		}
		log.debug("Generator length:"+theZathuraGenerators.size());
	}

	/**
	 * 
	 * @return
	 */
	public static HashMap<String, GeneratorModel> getTheZathuraGenerators() {
		return theZathuraGenerators;
	}
	/**
	 * 
	 * @return
	 */
	public static java.util.List<String> getGeneratorNames() {
		return generatorNames;
	}
	public static String getGeneratorNameForGuiName(String guiName){
		for(GeneratorModel generatorModel:theZathuraGenerators.values()){
			if(generatorModel.getGuiName().equals(guiName)==true){
				return generatorModel.getName();
			}
		}
		return "";
	}

}
