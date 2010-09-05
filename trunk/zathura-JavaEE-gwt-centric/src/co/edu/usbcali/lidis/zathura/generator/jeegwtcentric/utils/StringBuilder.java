package co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.ListUtils;

import co.edu.usbcali.lidis.zathura.metadata.model.Member;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;
/**
 * 
 * @author William Altuzarra Noriega
 * 
 */
public class StringBuilder implements IStringBuilder {

	StringBuilderForId stringBuilderForId;

	public StringBuilder(List<MetaData> list,
			StringBuilderForId stringBuilderForId) {
		this.stringBuilderForId = stringBuilderForId;
	}

	// finalParameter(List<MetaData> theMetaData, MetaData metaData) {
	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParam(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public String finalParam(List<MetaData> theMetaData, MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				finalParam = finalParam + realType + " " + name + ", ";
			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {
					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName() + ", ";
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				// String params[] = getTypeAndvariableForManyToOneProperties(
				// member.getName(), theMetaData);
				String params[] = Utilities.getInstance()
						.getTypeAndvariableForManyToOneProperties(
								member.getRealClassName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							tmpFinalParam = tmpFinalParam + " " + params[cont];

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam + ", ";
							} else {
								finalParam = finalParam + tmpFinalParam + i
										+ ", ";
							}
						}

					}
				}

			}
		}

		String finalCharacter = new String();

		if (finalParam.length() > 2) {
			finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

			finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
					finalParam.length() - 2) : finalParam;
		}

		return finalParam;
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamVariables(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public String finalParamVariables(List<MetaData> theMetaData,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				finalParam = finalParam + name + ", ";
			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {
					finalParam = finalParam + member.getName() + ", ";
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = Utilities.getInstance()
						.getTypeAndvariableForManyToOneProperties(
								member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam + ", ";
							}
						}

					}
				}

			}
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#getTypeAndvariableForManyToOneProperties(java.lang.String, java.util.List)
	 */
	public String[] getTypeAndvariableForManyToOneProperties(String strClass,
			List<MetaData> theMetaData) {
		String ret[] = new String[50];

		for (MetaData metaData : theMetaData) {
			if (metaData.getRealClassName().equalsIgnoreCase(strClass)) {

				Utilities.getInstance().manyToOneTempHash = metaData
						.getPrimaryKey().getHashMapIdsProperties();

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

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamVariablesAsList(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamVariablesAsList(List<MetaData> theMetaData,
			MetaData metaData) {
		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		Utilities.getInstance().datesJSP = new ArrayList<String>();

		// if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
		// Field[] field = metaData.getComposeKey().getDeclaredFields();
		// for (Field field2 : field) {
		//				
		// String name = field2.getName().substring(0, 1).toUpperCase()
		// + field2.getName().substring(1);
		//				
		// finalParam = finalParam + name + ", ";
		// finalParam2.add(name);
		// }
		// }

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
//				if (!metaData.getPrimaryKey().getName()
//						.equals(member.getName())) {

					String name = member.getName().substring(0, 1)
							.toUpperCase()
							+ member.getName().substring(1);

					finalParam = finalParam + name + ", ";

					if (member.getRealClassName().equalsIgnoreCase("date")) {
						Utilities.getInstance().datesJSP.add(name);
					} else {
						finalParam2.add(name);
					}
//				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String forstCont = params[cont];

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {

								String name = tmpFinalParam.substring(0, 1)
										.toUpperCase()
										+ tmpFinalParam.substring(1);

								finalParam = finalParam + tmpFinalParam + ", ";

								if (forstCont.equalsIgnoreCase("date")) {
									Utilities.getInstance().datesJSP.add(name);
								} else {
									finalParam2.add(name);
								}

							}
						}

					}
				}

				// String tmpFinalParam = params[1];
				// if (!finalParam.contains(tmpFinalParam)) {
				//
				// String name = params[1].substring(0, 1).toUpperCase()
				// + params[1].substring(1);
				//
				// finalParam = finalParam + params[1] + ", ";
				// finalParam2.add(name);
				// }
			}
		}

		List<String> primaryKey = stringBuilderForId
				.finalParamForIdClassAsVariables(theMetaData, metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamVariablesAsList2(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamVariablesAsList2(List<MetaData> theMetaData,
			MetaData metaData) {
		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!metaData.getPrimaryKey().getName()
						.equals(member.getName())) {

					String name = member.getName().substring(0, 1)
							.toUpperCase()
							+ member.getName().substring(1);

					finalParam = finalParam + name + ", ";

					finalParam2.add(name);
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String forstCont = params[cont];

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {

								String name = tmpFinalParam.substring(0, 1)
										.toUpperCase()
										+ tmpFinalParam.substring(1);

								finalParam = finalParam + tmpFinalParam + ", ";

								finalParam2.add(name);
							}
						}

					}
				}
			}
		}

		List<String> primaryKey = stringBuilderForId
				.finalParamForIdClassAsVariables(theMetaData, metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamVariablesDatesAsList2(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamVariablesDatesAsList2(
			List<MetaData> theMetaData, MetaData metaData) {
		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!metaData.getPrimaryKey().getName()
						.equals(member.getName())) {
					String type = member.getRealClassName();
					if (type.equalsIgnoreCase("date")) {
						String name = member.getName().substring(0, 1)
								.toLowerCase()
								+ member.getName().substring(1);

						finalParam = finalParam + name + ", ";

						finalParam2.add(name);
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String forstCont = params[cont];

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];
							String tmpFinalType = "";
							try {
								tmpFinalType = params[cont - 1];
							} catch (Exception e) {
								tmpFinalType = "";
							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								if (tmpFinalType.equalsIgnoreCase("date")) {
									String name = tmpFinalParam.substring(0, 1)
											.toLowerCase()
											+ tmpFinalParam.substring(1);

									finalParam = finalParam + tmpFinalParam
											+ ", ";

									finalParam2.add(name);
								}
							}
						}

					}
				}
			}
		}

		List<String> primaryKey = stringBuilderForId
				.finalParamForIdClassAsVariables(theMetaData, metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamForVariablesDataTablesAsList(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForVariablesDataTablesAsList(
			List<MetaData> theMetaData, MetaData metaData) {

		// if (metaData.getRealClassName().equalsIgnoreCase("SampleInReb")) {
		// String tmp = null;
		// System.out.println(tmp);
		// }

		List<String> finalParam2 = new ArrayList<String>();
		//
		// if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
		// Field[] field = metaData.getComposeKey().getDeclaredFields();
		// for (Field field2 : field) {
		//
		// String name = field2.getName();
		//
		// finalParam2
		// .add(metaData.getPrimaryKey().getName() + "." + name);
		// }
		// } else {
		// finalParam2.add(metaData.getPrimaryKey().getName());
		// }

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!metaData.getPrimaryKey().getName()
						.equals(member.getName())) {

					String name = member.getName();

					finalParam2.add(name);
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						if (params[cont] != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmpFinalParam = params[cont];

							HashMap map = stringBuilderForId.hashMapIds;

							String name = null;
							String idName = null;
							try {
								idName = stringBuilderForId.hashMapIds
										.get(tmpFinalParam);

							} catch (Exception e) {
								name = member.getName() + "." + tmpFinalParam;
							}

							if (idName != null)
								name = member.getName() + "." + idName + "."
										+ tmpFinalParam.split("_")[0];
							else
								name = member.getName() + "."
										+ tmpFinalParam.split("_")[0];

							finalParam2.add(name);

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

						}
					}
				}

				// String tmpFinalParam = params[1];
				// if (!finalParam.contains(tmpFinalParam)) {
				//
				// String name = params[1].substring(0, 1).toUpperCase()
				// + params[1].substring(1);
				//
				// finalParam = finalParam + params[1] + ", ";
				// finalParam2.add(name);
				// }
			}
		}

		// List<String> primaryKey =
		// finalParamForIdClassAsVariables(theMetaData,
		// metaData);

		// return ListUtils.subtract(finalParam2, primaryKey);
		return finalParam2;
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamForView(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public String finalParamForView(List<MetaData> theMetaData,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String nameWithCapitalOnFirst = Utilities.getInstance()
						.getGetNameOfPrimaryName(name);
				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				if (realType.equalsIgnoreCase("date")) {
					finalParam = finalParam + "(txt" + nameWithCapitalOnFirst
							+ ".getValue())==null||(txt"
							+ nameWithCapitalOnFirst
							+ ".getValue()).equals(\"\")?null:" + "("
							+ realType + ")txt" + nameWithCapitalOnFirst
							+ ".getValue(), ";
				} else {
					finalParam = finalParam + "(txt" + nameWithCapitalOnFirst
							+ ".getValue())==null||(txt"
							+ nameWithCapitalOnFirst
							+ ".getValue()).equals(\"\")?null:new " + realType
							+ "(txt" + nameWithCapitalOnFirst
							+ ".getValue().toString()), ";
				}

			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {

					// finalParam = finalParam + "new "
					// + member.getRealClassName() + "((String)txt"
					// + member.getGetNameOfPrimaryName()
					// + ".getValue()), ";

					if (member.getRealClassName().equalsIgnoreCase("date")) {
						finalParam = finalParam + "(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue())==null||(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue()).equals(\"\")?null:" + "("
								+ member.getRealClassName() + ")txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue(), ";
					} else {
						finalParam = finalParam + "(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue())==null||(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue()).equals(\"\")?null:new "
								+ member.getRealClassName() + "(txt"
								+ member.getGetNameOfPrimaryName()
								+ ".getValue().toString()), ";
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmp = Utilities.getInstance()
									.getGetNameOfPrimaryName(params[cont]);

							// tmpFinalParam = "new " + tmpFinalParam
							// + "((String)txt" + tmp;

							if (tmpFinalParam.equalsIgnoreCase("date")) {
								tmpFinalParam = "(txt" + tmp
										+ ".getValue())==null||(txt" + tmp
										+ ".getValue()).equals(\"\")?null:"
										+ "(" + tmpFinalParam + ")txt" + tmp
										+ ".getValue(), ";
							} else {
								tmpFinalParam = "(txt" + tmp
										+ ".getValue())==null||(txt" + tmp
										+ ".getValue()).equals(\"\")?null:new "
										+ tmpFinalParam + "(txt" + tmp
										+ ".getValue().toString()), ";
							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam;
							}
						}

					}
				}

				// String tmpFinalParam = "(" + params[0] + ") txt" + params[2];
				// if (!finalParam.contains(tmpFinalParam)) {
				// finalParam = finalParam + "(" + params[0] + ") txt"
				// + params[2] + ".getValue(), ";
				// }
			}
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamForDtoUpdate(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public String finalParamForDtoUpdate(List<MetaData> theMetaData,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				if (realType.equalsIgnoreCase("date")) {
					finalParam = finalParam + name + "==null || " + name
							+ ".equals(\"\")?null:" + name + ", ";
				} else {
					finalParam = finalParam + name + "==null||" + name
							+ ".equals(\"\")?null:new " + realType + "(" + name
							+ "), ";
				}

			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {

					// finalParam = finalParam + "new "
					// + member.getRealClassName() + "((String)txt"
					// + member.getGetNameOfPrimaryName()
					// + ".getValue()), ";

					if (member.getRealClassName().equalsIgnoreCase("date")) {
						finalParam = finalParam + member.getName()
								+ "==null|| " + member.getName()
								+ ".equals(\"\")?null:" + member.getName()
								+ ", ";
					} else {
						finalParam = finalParam + member.getName() + "==null||"
								+ member.getName() + ".equals(\"\")?null:new "
								+ member.getRealClassName() + "("
								+ member.getName() + "), ";
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmp = params[cont];

							// tmpFinalParam = "new " + tmpFinalParam
							// + "((String)txt" + tmp;

							if (tmpFinalParam.equalsIgnoreCase("date")) {
								tmpFinalParam = tmp + "==null||" + tmp
										+ ".equals(\"\")?null:" + tmp + ", ";
							} else {
								tmpFinalParam = tmp + "==null|| " + tmp
										+ ".equals(\"\")?null:new "
										+ tmpFinalParam + "(" + tmp + "), ";
							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam;
							}
						}

					}
				}

				// String tmpFinalParam = "(" + params[0] + ") txt" + params[2];
				// if (!finalParam.contains(tmpFinalParam)) {
				// finalParam = finalParam + "(" + params[0] + ") txt"
				// + params[2] + ".getValue(), ";
				// }
			}
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamForDtoUpdateOnlyVariables(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public String finalParamForDtoUpdateOnlyVariables(
			List<MetaData> theMetaData, MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				finalParam = finalParam + name + ", ";

			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {

					finalParam = finalParam + member.getName() + ", ";
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {
				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							String tmp = params[cont];

							tmpFinalParam = tmp + ", ";

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							if (!finalParam.contains(tmpFinalParam)) {
								finalParam = finalParam + tmpFinalParam;
							}
						}
					}
				}
			}
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamForViewVariablesInList(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForViewVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		Utilities.getInstance().dates = new ArrayList<String>();

		if (metaData.getRealClassName().equalsIgnoreCase("DiaNoLaboral")) {
			String tmp = "";
			System.out.println(tmp);
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {
					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName();
					String tmp2 = (member.getName().substring(0, 1))
							.toUpperCase()
							+ member.getName().substring(1);
					if (member.getRealClassName().equalsIgnoreCase("date")) {
						Utilities.getInstance().dates.add(tmp2);
					} else {

						finalParam2.add(tmp2);
					}
				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				if (params != null) {
					int cont = 0;
					for (int i = 0; i < params.length; i++) {

						String tmpFinalParam = params[cont];
						if (tmpFinalParam != null) {
							if (cont > params.length)
								cont = params.length;
							else
								cont++;

							tmpFinalParam = tmpFinalParam + " " + params[cont];

							if (!finalParam.contains(tmpFinalParam)) {

								finalParam = finalParam + tmpFinalParam;
								;

								String tmp3 = (params[cont].substring(0, 1))
										.toUpperCase()
										+ params[cont].substring(1);

								if (tmpFinalParam.equalsIgnoreCase("date")) {
									Utilities.getInstance().dates.add(tmp3);
								} else {
									finalParam2.add(tmp3);
								}

							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;

						}

					}
				}

				// String tmpFinalParam = params[0] + " " + params[1];
				//				
				// if (!finalParam.contains(tmpFinalParam)) {
				//					
				// finalParam = finalParam + params[0] + " " + params[1];
				//					
				// String tmp3 = (params[1].substring(0, 1)).toUpperCase()
				// + params[1].substring(1);
				//					
				// finalParam2.add(tmp3);
				// }
			}
		}

		List primaryKey = stringBuilderForId
				.finalParamForIdForViewVariablesInList(theMetaData, metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamForViewForSetsVariablesInList(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!member.getName().equalsIgnoreCase(
						metaData.getPrimaryKey().getName())) {

					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName();
					String tmp2 = "txt"
							+ (member.getName().substring(0, 1)).toUpperCase()
							+ member.getName().substring(1) + ".setValue("
							+ "entity.get"
							+ (member.getName().substring(0, 1)).toUpperCase()
							+ member.getName().substring(1) + "()" + ");"
							+ "txt"
							+ (member.getName().substring(0, 1)).toUpperCase()
							+ (member.getName().substring(1))
							+ ".setDisabled(false);";
					finalParam2.add(tmp2);

				}
			}
		}

		// txtCliCedula.setValue(entity.getClientes().getCliCedula());
		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				String tmpFinalParam = "";
				String tmpFinalParam1 = "";
				String tmpFinalParam2 = "";
				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							tmpFinalParam1 = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam2 = params[cont];

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam
											+ ", ";

									HashMap<String, String> map = stringBuilderForId.hashMapIds;

									String hashMapProve = "";
									String hashMapProve1 = "";
									try {

										hashMapProve = (stringBuilderForId.hashMapIds
												.get(tmpFinalParam2));

									} catch (Exception e) {
										// TODO: handle exception
									}
									String tmp3 = "";

									if (hashMapProve == null) {
										tmp3 = "txt"
												+ (tmpFinalParam2.substring(0,
														1)).toUpperCase()
												+ tmpFinalParam2.substring(1)
												+ ".setValue("
												+ "entity.get"
												+ member.getRealClassName()
												+ "().get"
												+ (tmpFinalParam2.substring(0,
														1)).toUpperCase()
												+ tmpFinalParam2
														.substring(
																1,
																tmpFinalParam2
																		.lastIndexOf("_"))
												+ "()"
												+ ");"
												+ "txt"
												+ (tmpFinalParam2.substring(0,
														1)).toUpperCase()
												+ tmpFinalParam2.substring(1)
												+ ".setDisabled(false);";
									} else {
										if (hashMapProve.equals("")) {

											tmp3 = "txt"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ ".setValue("
													+ "entity.get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "()"
													+ ");"
													+ "txt"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ ".setDisabled(false);";

										} else {

											tmp3 = "txt"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ ".setValue("
													+ "entity.get"
													+ member.getRealClassName()
													+ "().get"
													+ hashMapProve.substring(0,
															1).toUpperCase()
													+ hashMapProve.substring(1)
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "()"
													+ ");"
													+ "txt"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ ".setDisabled(false);";

										}
									}

									finalParam2.add(tmp3);
								}
							}

						}
					}

				} else {

					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								tmpFinalParam1 = params[cont];

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam;

									String tmp3 = "txt"
											+ (tmpFinalParam1.substring(0, 1))
													.toUpperCase()
											+ tmpFinalParam1.substring(1)
											+ ".setValue("
											+ "entity.get"
											+ member.getRealClassName()
											+ "()"
											+ ".get"
											+ tmpFinalParam1.substring(0, 1)
													.toUpperCase()
											+ tmpFinalParam1.substring(1,
													tmpFinalParam1
															.lastIndexOf("_"))
											+ "()"
											+ ");"
											+ "txt"
											+ (tmpFinalParam1.substring(0, 1))
													.toUpperCase()
											+ tmpFinalParam1.substring(1)
											+ ".setDisabled(false);";

									finalParam2.add(tmp3);
								}
							}
						}
					}
				}
			}
		}

		List primaryKey = stringBuilderForId
				.finalParamForIdForViewForSetsVariablesInList(theMetaData,
						metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamForDtoForSetsVariablesInList(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForDtoForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!member.getName().equalsIgnoreCase(
						metaData.getPrimaryKey().getName())) {

					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName();
					if (!member.getRealClassName().equalsIgnoreCase("date")) {
						String tmp2 = member.getName()
								+ "="
								+ metaData.getRealClassNameAsVariable()
								+ ".get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "()!=null ? "
								+ metaData.getRealClassNameAsVariable()
								+ ".get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "().toString() : null;";
						finalParam2.add(tmp2);
					} else {
						String tmp2 = member.getName()
								+ "="
								+ metaData.getRealClassNameAsVariable()
								+ ".get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1) + "();";
						finalParam2.add(tmp2);
					}

				}
			}
		}

		// txtCliCedula.setValue(entity.getClientes().getCliCedula());
		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				String tmpFinalParam = "";
				String tmpFinalParam1 = "";
				String tmpFinalParam2 = "";
				String tmpFinalParam3 = "";
				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							tmpFinalParam1 = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam2 = params[cont];
								try {
									tmpFinalParam3 = params[cont - 1];
								} catch (Exception e) {
									tmpFinalParam3 = "";
								}

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam
											+ ", ";

									HashMap<String, String> map = stringBuilderForId.hashMapIds;

									String hashMapProve = "";
									String hashMapProve1 = "";
									try {

										hashMapProve = (stringBuilderForId.hashMapIds
												.get(tmpFinalParam2));

									} catch (Exception e) {
										// TODO: handle exception
									}
									String tmp3 = "";

									if (hashMapProve == null) {
										if (!tmpFinalParam3
												.equalsIgnoreCase("date")) {
											tmp3 = tmpFinalParam2
													+ "="
													+ metaData
															.getRealClassNameAsVariable()
													+ ".get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "()!=null ? "
													+ metaData
															.getRealClassNameAsVariable()
													+ ".get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "().toString() : null;";
										} else {
											tmp3 = tmpFinalParam2
													+ "="
													+ metaData
															.getRealClassNameAsVariable()
													+ ".get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "();";
										}
									} else {
										if (hashMapProve.equals("")) {
											if (!tmpFinalParam3
													.equalsIgnoreCase("date")) {
												tmp3 = tmpFinalParam2
														+ "="
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "()!=null ? "
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "().toString() : null;";
											} else {
												tmp3 = tmpFinalParam2
														+ "="
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "();";
											}
										} else {
											if (!tmpFinalParam3
													.equalsIgnoreCase("date")) {
												tmp3 = tmpFinalParam2
														+ "="
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "()!=null ? "
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "().toString() : null;";
											} else {
												tmp3 = tmpFinalParam2
														+ "="
														+ metaData
																.getRealClassNameAsVariable()
														+ ".get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "();";
											}
										}
									}

									finalParam2.add(tmp3);
								}
							}

						}
					}

				} else {

					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								tmpFinalParam1 = params[cont];
								try {
									tmpFinalParam3 = params[cont - 1];
								} catch (Exception e) {
									tmpFinalParam3 = "";
								}

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam;
									if (!tmpFinalParam3
											.equalsIgnoreCase("date")) {
										String tmp3 = tmpFinalParam1
												+ "="
												+ metaData
														.getRealClassNameAsVariable()
												+ ".get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "()!=null ?"
												+ metaData
														.getRealClassNameAsVariable()
												+ ".get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "().toString() : null;";

										finalParam2.add(tmp3);
									} else {
										String tmp3 = tmpFinalParam1
												+ "="
												+ metaData
														.getRealClassNameAsVariable()
												+ ".get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "();";

										finalParam2.add(tmp3);
									}
								}
							}
						}
					}
				}
			}
		}

		List primaryKey = stringBuilderForId
				.finalParamForIdForDtoForSetsVariablesInList(theMetaData,
						metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#finalParamForDtoInViewForSetsVariablesInList(java.util.List, co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForDtoInViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (!member.getName().equalsIgnoreCase(
						metaData.getPrimaryKey().getName())) {

					finalParam = finalParam + member.getRealClassName() + " "
							+ member.getName();
					if (!member.getRealClassName().equalsIgnoreCase("date")) {
						String tmp2 = metaData.getRealClassNameAsVariable()
								+ "DTO2.set"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "("
								+ metaData.getRealClassNameAsVariable()
								+ "Tmp.get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "()!=null ?"
								+ metaData.getRealClassNameAsVariable()
								+ "Tmp.get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "().toString() : null);";
						finalParam2.add(tmp2);
					} else {
						String tmp2 = metaData.getRealClassNameAsVariable()
								+ "DTO2.set"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1)
								+ "("
								+ metaData.getRealClassNameAsVariable()
								+ "Tmp.get"
								+ (member.getName().substring(0, 1))
										.toUpperCase()
								+ member.getName().substring(1) + "());";
						finalParam2.add(tmp2);
					}

				}
			}
		}

		// txtCliCedula.setValue(entity.getClientes().getCliCedula());
		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				String params[] = getTypeAndvariableForManyToOneProperties(
						member.getType().getSimpleName(), theMetaData);

				String tmpFinalParam = "";
				String tmpFinalParam1 = "";
				String tmpFinalParam2 = "";
				String tmpFinalParam3 = "";
				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							tmpFinalParam1 = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam2 = params[cont];
								try {
									tmpFinalParam3 = params[cont - 1];
								} catch (Exception e) {
									tmpFinalParam3 = "";
								}

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam
											+ ", ";

									HashMap<String, String> map = stringBuilderForId.hashMapIds;

									String hashMapProve = "";
									String hashMapProve1 = "";
									try {

										hashMapProve = (stringBuilderForId.hashMapIds
												.get(tmpFinalParam2));

									} catch (Exception e) {
										// TODO: handle exception
									}
									String tmp3 = "";

									if (hashMapProve == null) {
										if (!tmpFinalParam3
												.equalsIgnoreCase("date")) {
											tmp3 = metaData
													.getRealClassNameAsVariable()
													+ "DTO2.set"
													+ tmpFinalParam2.substring(
															0, 1).toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ "("
													+ metaData
															.getRealClassNameAsVariable()
													+ "Tmp.get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "()!=null ? "
													+ metaData
															.getRealClassNameAsVariable()
													+ "Tmp.get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "().toString() : null);";
										} else {
											tmp3 = metaData
													.getRealClassNameAsVariable()
													+ "DTO2.set"
													+ tmpFinalParam2.substring(
															0, 1).toUpperCase()
													+ tmpFinalParam2
															.substring(1)
													+ "("
													+ metaData
															.getRealClassNameAsVariable()
													+ "Tmp.get"
													+ member.getRealClassName()
													+ "().get"
													+ (tmpFinalParam2
															.substring(0, 1))
															.toUpperCase()
													+ tmpFinalParam2
															.substring(
																	1,
																	tmpFinalParam2
																			.lastIndexOf("_"))
													+ "());";
										}
									} else {
										if (hashMapProve.equals("")) {
											if (!tmpFinalParam3
													.equalsIgnoreCase("date")) {
												tmp3 = metaData
														.getRealClassNameAsVariable()
														+ "DTO2.set"
														+ tmpFinalParam2
																.substring(0, 1)
																.toUpperCase()
														+ tmpFinalParam2
																.substring(1)
														+ "("
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "()!=null ? "
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "().toString() : null);";
											} else {
												tmp3 = metaData
														.getRealClassNameAsVariable()
														+ "DTO2.set"
														+ tmpFinalParam2
																.substring(0, 1)
																.toUpperCase()
														+ tmpFinalParam2
																.substring(1)
														+ "("
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "());";
											}
										} else {
											if (!tmpFinalParam3
													.equalsIgnoreCase("date")) {
												tmp3 = metaData
														.getRealClassNameAsVariable()
														+ "DTO2.set"
														+ tmpFinalParam2
																.substring(0, 1)
																.toUpperCase()
														+ tmpFinalParam2
																.substring(1)
														+ "("
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "()!=null ?"
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "().toString() : null);";
											} else {
												tmp3 = metaData
														.getRealClassNameAsVariable()
														+ "DTO2.set"
														+ tmpFinalParam2
																.substring(0, 1)
																.toUpperCase()
														+ tmpFinalParam2
																.substring(1)
														+ "("
														+ metaData
																.getRealClassNameAsVariable()
														+ "Tmp.get"
														+ member
																.getRealClassName()
														+ "().get"
														+ hashMapProve
																.substring(0, 1)
																.toUpperCase()
														+ hashMapProve
																.substring(1)
														+ "().get"
														+ (tmpFinalParam2
																.substring(0, 1))
																.toUpperCase()
														+ tmpFinalParam2
																.substring(
																		1,
																		tmpFinalParam2
																				.lastIndexOf("_"))
														+ "());";
											}
										}
									}

									finalParam2.add(tmp3);
								}
							}

						}
					}

				} else {

					if (params != null) {
						int cont = 0;
						for (int i = 0; i < params.length; i++) {

							tmpFinalParam = params[cont];

							if (tmpFinalParam != null) {
								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								tmpFinalParam = tmpFinalParam + " "
										+ params[cont];

								tmpFinalParam1 = params[cont];
								try {
									tmpFinalParam3 = params[cont - 1];
								} catch (Exception e) {
									tmpFinalParam3 = "";
								}

								if (cont > params.length)
									cont = params.length;
								else
									cont++;

								if (!finalParam.contains(tmpFinalParam)) {
									finalParam = finalParam + tmpFinalParam;
									if (!tmpFinalParam3
											.equalsIgnoreCase("date")) {
										String tmp3 = metaData
												.getRealClassNameAsVariable()
												+ "DTO2.set"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1.substring(1)
												+ "("
												+ metaData
														.getRealClassNameAsVariable()
												+ "Tmp.get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "()!=null ? "
												+ metaData
														.getRealClassNameAsVariable()
												+ "Tmp.get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "().toString() : null);";

										finalParam2.add(tmp3);
									} else {
										String tmp3 = metaData
												.getRealClassNameAsVariable()
												+ "DTO2.set"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1.substring(1)
												+ "("
												+ metaData
														.getRealClassNameAsVariable()
												+ "Tmp.get"
												+ member.getRealClassName()
												+ "()"
												+ ".get"
												+ tmpFinalParam1
														.substring(0, 1)
														.toUpperCase()
												+ tmpFinalParam1
														.substring(
																1,
																tmpFinalParam1
																		.lastIndexOf("_"))
												+ "());";

										finalParam2.add(tmp3);
									}
								}
							}
						}
					}
				}
			}
		}

		List primaryKey = stringBuilderForId
				.finalParamForIdForDtoInViewForSetsVariablesInList(theMetaData,
						metaData);

		return ListUtils.subtract(finalParam2, primaryKey);
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#getVariableForManyToOneProperties(java.util.List, java.util.List)
	 */
	public List<String> getVariableForManyToOneProperties(
			List<Member> manyToOne, List<MetaData> theMetaData) {
		List<String> finalParam = new ArrayList<String>();

		for (MetaData metaData1 : theMetaData) {
			for (Member member : manyToOne) {
				if (metaData1.getRealClassName().equalsIgnoreCase(
						member.getRealClassName())) {

					if (metaData1.getPrimaryKey().isPrimiaryKeyAComposeKey()) {

						Field[] field = metaData1.getComposeKey()
								.getDeclaredFields();

						for (int i = 0; i < field.length; i++) {

							Field field2 = field[i];

							String name = field2.getName() + "_"
									+ metaData1.getRealClassName();

							finalParam.add(name);

						}

					} else {
						finalParam.add(metaData1.getPrimaryKey().getName()
								+ "_" + metaData1.getRealClassName());
					}
				}
			}
		}

		return finalParam;
	}

	/* (non-Javadoc)
	 * @see co.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.IStringBuilder#getStringsForManyToOneProperties(java.util.List, java.util.List)
	 */
	public List<String> getStringsForManyToOneProperties(
			List<Member> manyToOne, List<MetaData> theMetaData) {
		List<String> finalParam = new ArrayList<String>();

		int cont = 1;
		for (MetaData metaData1 : theMetaData) {
			for (Member member : manyToOne) {
				if (metaData1.getRealClassName().equalsIgnoreCase(
						member.getRealClassName())) {

					if (metaData1.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
						Field[] field = metaData1.getComposeKey()
								.getDeclaredFields();

						finalParam.add(metaData1.getPrimaryKey()
								.getRealClassName()
								+ " "
								+ metaData1.getPrimaryKey()
										.getRealClassVariableName()
								+ "Class = new "
								+ metaData1.getPrimaryKey().getRealClassName()
								+ "();");

						for (int i = 0; i < field.length; i++) {

							Field field2 = field[i];
							String name = field2.getName();

							// sitesId.setConame(coname);

							String build = name.substring(0, 1).toUpperCase();
							String build2 = name.substring(1, name.length());
							String build3 = build + build2;

							finalParam.add(metaData1.getPrimaryKey()
									.getRealClassVariableName()
									+ "Class.set"
									+ build3
									+ "("
									+ name
									+ "_"
									+ metaData1.getRealClassName() + ");");

						}

						finalParam
								.add(member.getRealClassName()
										+ " "
										+ member.getName()
										+ "Class = logic"
										+ member.getRealClassName()
										+ cont
										+ ".get"
										+ member.getRealClassName()
										+ "("
										+ metaData1.getPrimaryKey()
												.getRealClassVariableName()
										+ "Class);");
						cont++;

					} else {
						finalParam.add(member.getRealClassName() + " "
								+ member.getName() + "Class = logic"
								+ member.getRealClassName() + cont + ".get"
								+ member.getRealClassName() + "("
								+ metaData1.getPrimaryKey().getName() + "_"
								+ metaData1.getRealClassName() + ");");
						cont++;

					}

				}

			}
		}

		return finalParam;
	}

	// if (metaData.getRealClassName().equalsIgnoreCase("TipoAlarma")) {
	// String tmp = "";
	// System.out.println(tmp);
	// }
	// if (metaData.getRealClassName().equalsIgnoreCase("comuna")) {
	// String tmp = "";
	// System.out.println(tmp);
	// }
	// if (metaData.getRealClassName().equalsIgnoreCase("DetalleCantiMuestra"))
	// {
	// String tmp = "";
	// System.out.println(tmp);
	// }

}
