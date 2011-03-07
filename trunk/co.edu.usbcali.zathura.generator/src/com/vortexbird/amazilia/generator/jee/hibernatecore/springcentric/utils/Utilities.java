package com.vortexbird.amazilia.generator.jee.hibernatecore.springcentric.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.Basic;
import javax.persistence.Column;

import co.edu.usbcali.lidis.zathura.generator.utilities.GeneratorUtil;
import co.edu.usbcali.lidis.zathura.metadata.model.Member;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaDataModel;
import co.edu.usbcali.lidis.zathura.metadata.model.SimpleMember;
/**
 * Zathura Generator
 * @author William Altuzarra Noriega (williamaltu@gmail.com)
 * @version 1.0
 */
public class Utilities {

	private static Utilities instance = null;

	private Utilities() {
	}

	public static Utilities getInstance() {
		if (instance == null) {
			instance = new Utilities();
		}

		return instance;
	}

	public Long length;
	public Long precision;
	public Long scale;
	public Boolean nullable;

	public String ifcondition = "if(";
	public String ifconditionClose = "){";
	public String throwExceptionNull = "throw new ZMessManager().new EmptyFieldException(";
	public String throwExceptionLength = "throw new ZMessManager().new NotValidFormatException(";
	public String throwExceptionClose = ");}";

	public List<String> dates;
	public List<String> datesJSP;
	public List<String> datesId;
	public List<String> datesIdJSP;
	public HashMap<String, Member> manyToOneTempHash;

	// getTypeAndvariableForManyToOneProperties
	public String[] getTypeAndvariableForManyToOneProperties(String strClass,
			List<MetaData> theMetaData) {
		String ret[] = new String[50];

		for (MetaData metaData : theMetaData) {
			if (metaData.getRealClassName().equalsIgnoreCase(strClass)) {

				manyToOneTempHash = metaData.getPrimaryKey()
						.getHashMapIdsProperties();

				if (!metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					Member member = metaData.getPrimaryKey();
					ret[0] = member.getRealClassName();
					ret[1] = member.getName() + "_"
							+ metaData.getRealClassName();
					// ret[2] = member.getGetNameOfPrimaryName();
					// ret[3] = member.getRealClassName();
				} else {
					int contTmp = 0;
					Field[] field = metaData.getComposeKey()
							.getDeclaredFields();
					for (int i = 0; i < field.length; i++) {

						Field field2 = field[i];

						String name = field2.getName();

						String realType = field2.getType().toString()
								.substring(
										(field2.getType().toString())
												.lastIndexOf(".") + 1,
										(field2.getType().toString()).length());

						ret[contTmp] = realType;
						contTmp++;
						ret[contTmp] = name + "_" + metaData.getRealClassName();
						contTmp++;
					}
				}
			}
		}

		boolean watch = false;

		for (int j = 0; j < ret.length; j++) {
			if (ret[j] != null) {
				if (!ret[j].equalsIgnoreCase(""))
					watch = true;
			}
		}

		if (watch) {
			return ret;
		} else {
			return null;
		}

	}

	public List<String> addVariablesValuesToListDependingOnDataTypeForID(
			List<String> finalParam2, Field field, String variableName,
			Class clazz) {

		String realClassName = field.getType().getSimpleName();

		String variableNameFormethod = variableName.substring(0, 1)
				.toUpperCase()
				+ variableName.substring(1);

		buildStringToCheckLengths(field, clazz, variableName);

		if (realClassName.equalsIgnoreCase("String")) {
			if (!length.equals("0"))
				finalParam2.add(ifcondition + variableName + "!=null && "
						+ "Utilities.checkWordAndCheckWithlength("
						+ variableName + "," + length + ")==false"
						+ ifconditionClose + throwExceptionLength + "\""
						+ variableName + "\"" + throwExceptionClose);

		}

		if (realClassName.equalsIgnoreCase("Integer")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + 0
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Double")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + scale
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Long")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + 0
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Float")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + scale
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		return finalParam2;
	}

	public void buildStringToCheckLengths(Field field, Class clazz,
			String realClassName) {

		if (field.getAnnotations() != null && field.getAnnotations().length > 0) {
			if (field.getAnnotation(Column.class) != null) {
				Column column = field.getAnnotation(Column.class);
				length = new Long(column.length());
				precision = new Long(column.precision());
				scale = new Long(column.scale());
				nullable = new Boolean(column.nullable());

			} else {
				if (field.getAnnotation(Basic.class) != null) {
					nullable = new Boolean(field.getAnnotation(Basic.class)
							.optional());
				}
			}
		} else {
			for (Method method : clazz.getDeclaredMethods()) {

				if (method.getAnnotation(Column.class) != null) {
					String property = new String();

					if (method.getName().startsWith("get")) {
						property = method.getName().substring(3);

						if (realClassName.equalsIgnoreCase(property)) {
							Column column = method.getAnnotation(Column.class);
							length = new Long(column.length());
							precision = new Long(column.precision());
							scale = new Long(column.scale());
							nullable = new Boolean(column.nullable());

							break;
						}
					} else {
						if (method.getName().startsWith("is")) {
							property = method.getName().substring(3);

							if (realClassName.equalsIgnoreCase(property)) {
								Column column = method
										.getAnnotation(Column.class);
								length = new Long(column.length());
								precision = new Long(column.precision());
								scale = new Long(column.scale());
								nullable = new Boolean(column.nullable());

								break;
							}
						}
					}
				} else {
					if (method.getAnnotation(Basic.class) != null) {
						nullable = new Boolean(method
								.getAnnotation(Basic.class).optional());
					}
				}
			}
		}

	}

	public List<String> addVariablesValuesToListDependingOnDataType(
			List<String> finalParam2, String realClassName,
			String variableName, String precision, String scale, String length) {

		if (realClassName.equalsIgnoreCase("String")) {
			if (!length.equals("0"))
				finalParam2.add(ifcondition + variableName + "!=null && "
						+ "Utilities.checkWordAndCheckWithlength("
						+ variableName + "," + length + ")==false"
						+ ifconditionClose + throwExceptionLength + "\""
						+ variableName + "\"" + throwExceptionClose);

		}

		if (realClassName.equalsIgnoreCase("Integer")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + 0
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Double")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + (new Integer(precision)-new Integer(scale))+ "," + scale
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Long")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + precision + "," + 0
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		if (realClassName.equalsIgnoreCase("Float")) {
			if (!precision.equals("0"))
				finalParam2
						.add(ifcondition
								+ variableName
								+ "!=null && "
								+ "Utilities.checkNumberAndCheckWithPrecisionAndScale(\"\"+"
								+ variableName + "," + (new Integer(precision)-new Integer(scale)) + "," + scale
								+ ")==false" + ifconditionClose
								+ throwExceptionLength + "\"" + variableName
								+ "\"" + throwExceptionClose);
		}

		return finalParam2;
	}

	public List<String> getRelatedClasses(MetaData metaData,
			MetaDataModel dataModel) {
		List<String> imports = null;
		Member member = null;
		imports = new ArrayList<String>();
		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			imports.add(metaData.getPrimaryKey().getType().getName());
		}
		for (Object object : metaData.getProperties()) {

			if (!(object instanceof SimpleMember)) {

				member = (Member) object;
				imports.add(member.getType().getName());

				getRelatedClasses(dataModel, member, imports);
			} else {
				if (object instanceof SimpleMember) {
					if (((SimpleMember) object).getType().getName()
							.equalsIgnoreCase("java.util.Date")) {
						imports
								.add(((SimpleMember) object).getType()
										.getName());
					}
				}
			}
		}

		return imports;
	}

	public void getRelatedClasses(MetaDataModel dataModel, Member member,
			List<String> imports) {

		for (MetaData metaDataInList : dataModel.getTheMetaData()) {
			if (metaDataInList.getRealClassName().equalsIgnoreCase(
					member.getRealClassName())) {
				if (metaDataInList.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					imports.add(metaDataInList.getPrimaryKey().getType()
							.getName());
				}
			}
		}

	}

	public boolean isComposedKey(Class type) {
		boolean ret = false;

		String firstLetters = type.getName();
		String[] tmp = (firstLetters.replace(".", "%")).split("%");

		if (tmp != null) {
			if ((tmp[0].equalsIgnoreCase("java") && tmp[1]
					.equalsIgnoreCase("lang"))
					|| (tmp[0].equalsIgnoreCase("java") && tmp[1]
							.equalsIgnoreCase("util"))) {
				ret = false;
			} else {
				ret = true;
			}
		}

		return ret;
	}

	public boolean isFinalParamForViewDatesInList() {
		if (Utilities.getInstance().dates != null) {
			if (!Utilities.getInstance().dates.isEmpty()
					&& Utilities.getInstance().dates.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isFinalParamForIdForViewDatesInList() {
		if (Utilities.getInstance().datesId != null) {
			if (!Utilities.getInstance().datesId.isEmpty()
					&& Utilities.getInstance().datesId.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Object isFinalParamForIdClassAsVariablesForDates() {
		if (Utilities.getInstance().datesIdJSP != null) {
			if (!Utilities.getInstance().datesIdJSP.isEmpty()
					&& Utilities.getInstance().datesIdJSP.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Object isFinalParamDatesAsList() {
		if (Utilities.getInstance().datesJSP != null) {
			if (!Utilities.getInstance().datesJSP.isEmpty()
					&& Utilities.getInstance().datesJSP.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void biuldHashToGetIdValuesLengths(List<MetaData> list) {

		for (MetaData metaData : list) {

			HashMap<String, Member> hashMapIdsProperties = new HashMap<String, Member>();

			if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {

				Class clazz = metaData.getComposeKey();
				Field[] field = metaData.getComposeKey().getDeclaredFields();

				for (Field field2 : field) {
					String realClassName = field2.getName();

					// length;
					// precision;
					// scale;
					// nullable;

					Utilities.getInstance().buildStringToCheckLengths(field2,
							clazz, realClassName);

					Member member = new SimpleMember(field2.getName(), field2
							.getName(), field2.getType(), -1);
					member.setLength(Utilities.getInstance().length);
					member.setPrecision(Utilities.getInstance().precision);
					member.setScale(Utilities.getInstance().scale);
					member.setNullable(Utilities.getInstance().nullable);

					hashMapIdsProperties.put(field2.getName(), member);

					metaData.getPrimaryKey().setHashMapIdsProperties(
							hashMapIdsProperties);
				}
			} else {

				Member member = new SimpleMember(metaData.getPrimaryKey()
						.getName(), metaData.getPrimaryKey().getName(),
						metaData.getPrimaryKey().getType(), -1);

				member.setLength(metaData.getPrimaryKey().getLength());
				member.setPrecision(metaData.getPrimaryKey().getPrecision());
				member.setScale(metaData.getPrimaryKey().getScale());
				member.setNullable(metaData.getPrimaryKey().getNullable());

				hashMapIdsProperties.put(metaData.getPrimaryKey().getName(),
						member);

				metaData.getPrimaryKey().setHashMapIdsProperties(
						hashMapIdsProperties);

			}
		}
	}

	public void buildFolders(String packageName, String hardDiskLocation,
			Integer specificityLevel, String packageOriginal,
			Properties properties) {

		// / se construye paquete
		String pckge = packageName.replace('.', '_') + "_";
		String modelPckg = packageOriginal.replace('.', '_') + "_";

		String dataAcces = pckge + "dataaccess_";
		String model = modelPckg;
		String presentation = pckge + "presentation_";
		String dao = dataAcces + "dao_";

		List<String> folderBuilder = new ArrayList<String>();

		folderBuilder.add(pckge);

		folderBuilder.add(pckge + "exceptions");

		folderBuilder.add(pckge + "utilities");

		folderBuilder.add(dao);

//		folderBuilder.add(dataAcces + "entityManager");
		
		folderBuilder.add(model + "control");

		if (specificityLevel.intValue() == 2) {
			folderBuilder.add(model + "pojos");
		}

		folderBuilder.add(model + "dto");

		folderBuilder.add(presentation + "backEndBeans");

		folderBuilder.add(presentation + "businessDelegate");

		folderBuilder.add(properties.getProperty("webRootFolderPath"));

		for (String string : folderBuilder) {
			try {
				GeneratorUtil.validateDirectory(string, hardDiskLocation);
			} catch (IOException e) {
				// TODO Poner log4j por si lanza error
				e.printStackTrace();
			}
		}

		try {
			GeneratorUtil.validateDirectory("JSPX", properties
					.getProperty("webRootFolderPath"));
			GeneratorUtil.validateDirectory("WEB-INF", properties
					.getProperty("webRootFolderPath"));
			GeneratorUtil.validateDirectory("facelets", properties
					.getProperty("webRootFolderPath")
					+ GeneratorUtil.slash + "WEB-INF");
			// WEB-INF
			GeneratorUtil.validateDirectory("META-INF", hardDiskLocation);
		} catch (IOException e) {
			// TODO Poner log4j por si lanza error
			e.printStackTrace();
		}

	}

	public String getGetNameOfPrimaryName(String name) {
		String build = name.substring(0, 1).toUpperCase();
		String build2 = name.substring(1);
		return build + build2;
	}

	public String getRealClassName(String type) {
		String typeComplete = type;
		String[] tmp = (typeComplete.replace(".", "%")).split("%");
		String realName = tmp[tmp.length - 1];
		return realName;
	}

}
