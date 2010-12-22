package co.edu.usbcali.lidis.zathura.generator.jee.jpa.webcentric.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.edu.usbcali.lidis.zathura.metadata.model.ManyToOneMember;
import co.edu.usbcali.lidis.zathura.metadata.model.Member;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;

/**
 * Zathura Generator
 * 
 * @author William Altuzarra (williamaltu@gmail.com)
 * @version 1.0
 */
public class StringBuilderForId implements IStringBuilderForId {

	public HashMap<String, String> hashMapIds;

	public StringBuilderForId(List<MetaData> list) {
		hashMapIds = new HashMap<String, String>();
		neededIds(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId
	 * #finalParamForIdClassAsVariablesAsString(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public String finalParamForIdClassAsVariablesAsString(List<MetaData> list,
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

				finalParam = finalParam + "FacesUtils.check" + realType
						+ "(txt" + nameWithCapitalOnFirst + "), ";

			}
		} else {
			finalParam = "FacesUtils.check"
					+ metaData.getPrimaryKey().getRealClassName() + "(txt"
					+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
					+ "), ";
		}

		String finalCharacter = "" + finalParam.charAt(finalParam.length() - 2);

		finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
				finalParam.length() - 2) : finalParam;

		return finalParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#finalParamForIdClassAsVariables(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdClassAsVariables(List<MetaData> list,
			MetaData metaData) {
		List<String> finalParam = new ArrayList<String>();

		Utilities.getInstance().datesIdJSP = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String realType = Utilities.getInstance().getRealClassName(
						field2.getType().getName());

				if (realType.equalsIgnoreCase("date")) {
					Utilities.getInstance().datesIdJSP.add(name);
				} else {
					finalParam.add(name);
				}
			}
		} else {
			finalParam.add(metaData.getPrimaryKey().getName());
		}

		return finalParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId
	 * #finalParamForVariablesDataTablesForIdAsList(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForVariablesDataTablesForIdAsList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {

				String name = field2.getName();

				finalParam2
						.add(metaData.getPrimaryKey().getName() + "." + name);
			}
		} else {
			finalParam2.add(metaData.getPrimaryKey().getName());
		}

		return finalParam2;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#finalParamForIdVariablesAsList(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdVariablesAsList(
			List<MetaData> theMetaData, MetaData metaData) {
		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {

				String name = field2.getName();

				finalParam = finalParam + name + ", ";
				finalParam2.add(Utilities.getInstance().ifcondition + name
						+ "==null" + Utilities.getInstance().ifconditionClose
						+ Utilities.getInstance().throwExceptionNull + "\""
						+ name + "\""
						+ Utilities.getInstance().throwExceptionClose);

				finalParam2 = Utilities.getInstance()
						.addVariablesValuesToListDependingOnDataTypeForID(
								finalParam2, field2, name,
								metaData.getComposeKey());

			}
		}

		if (metaData.isGetSimpleProperties()) {
			for (Member member : metaData.getSimpleProperties()) {
				if (member.isPrimiaryKeyAComposeKey() == false) {

					try {
						if (member.getNullable() == false) {
							String name = member.getName();

							finalParam = finalParam + name + ", ";
							finalParam2
									.add(Utilities.getInstance().ifcondition
											+ name
											+ "==null"
											+ Utilities.getInstance().ifconditionClose
											+ Utilities.getInstance().throwExceptionNull
											+ "\""
											+ name
											+ "\""
											+ Utilities.getInstance().throwExceptionClose);

						}

						finalParam2 = Utilities.getInstance()
								.addVariablesValuesToListDependingOnDataType(
										finalParam2, member.getRealClassName(),
										member.getName(),
										member.getPrecision().toString(),
										member.getScale().toString(),
										member.getLength().toString());
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}
		}

		if (metaData.isGetManyToOneProperties()) {
			for (Member member : metaData.getManyToOneProperties()) {

				Utilities.getInstance().manyToOneTempHash = new HashMap<String, Member>();

				String params[] = Utilities.getInstance()
						.getTypeAndvariableForManyToOneProperties(
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

							tmpFinalParam = finalParam + tmp + ", ";

							if (!finalParam.contains(tmpFinalParam)) {

								ManyToOneMember manyToOneMember = (ManyToOneMember) member;

								String variableNames = (tmp.split("_"))[0];
								String className = (tmp.split("_"))[1];

								Boolean nullable = null;

								try {
									nullable = manyToOneMember
											.getHashMapNullableColumn()
											.get(variableNames.toUpperCase());
								} catch (Exception e) {
									// // TODO: handle exception
								}

								if (nullable == null) {
									try {
										nullable = manyToOneMember
												.getHashMapNullableColumn()
												.get(className.toUpperCase());
									} catch (Exception e) {
										// // TODO: handle exception
									}
								}

								Member primarySimple = Utilities.getInstance().manyToOneTempHash
										.get(variableNames);

								try {
									if (nullable == false) {
										finalParam2
												.add(Utilities.getInstance().ifcondition
														+ tmp
														+ "==null"
														+ Utilities
																.getInstance().ifconditionClose
														+ Utilities
																.getInstance().throwExceptionNull
														+ "\""
														+ tmp
														+ "\""
														+ Utilities
																.getInstance().throwExceptionClose);
									}
								} catch (Exception e) {
//									 System.out.println(e.getMessage());
								}

								try {

									finalParam2 = Utilities
											.getInstance()
											.addVariablesValuesToListDependingOnDataType(
													finalParam2,
													primarySimple
															.getRealClassName(),
													tmp,
													primarySimple
															.getPrecision()
															.toString(),
													primarySimple.getScale()
															.toString(),
													primarySimple.getLength()
															.toString());
								} catch (Exception e) {
									// TODO: handle exception
								}

							} else {
								ManyToOneMember manyToOneMember = (ManyToOneMember) member;

								String variableNames = (tmp.split("_"))[0] + i;
								String variableNames2 = (tmp.split("_"))[0];
								String className = (tmp.split("_"))[1] + i;

								Boolean nullable = null;

								try {
									nullable = manyToOneMember
											.getHashMapNullableColumn()
											.get(variableNames.toUpperCase());
								} catch (Exception e) {
									// TODO: handle exception
								}

								if (nullable == null) {
									try {
										nullable = manyToOneMember
												.getHashMapNullableColumn()
												.get(className.toUpperCase());
									} catch (Exception e) {
										// TODO: handle exception
									}
								}

								Member primarySimple = Utilities.getInstance().manyToOneTempHash
										.get(variableNames2);

								try {
									if (nullable == false) {
										finalParam2
												.add(Utilities.getInstance().ifcondition
														+ tmp
														+ "==null"
														+ Utilities
																.getInstance().ifconditionClose
														+ Utilities
																.getInstance().throwExceptionNull
														+ "\""
														+ tmp
														+ "\""
														+ Utilities
																.getInstance().throwExceptionClose);
									}
								} catch (Exception e) {
									// System.out.println(e.getMessage());
								}

								try {

									finalParam2 = Utilities
											.getInstance()
											.addVariablesValuesToListDependingOnDataType(
													finalParam2,
													primarySimple
															.getRealClassName(),
													tmp,
													primarySimple
															.getPrecision()
															.toString(),
													primarySimple.getScale()
															.toString(),
													primarySimple.getLength()
															.toString());
								} catch (Exception e) {
									// TODO: handle exception
								}
							}

							if (cont > params.length)
								cont = params.length;
							else
								cont++;
						}
					}
				}
			}
		}

		return finalParam2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#finalParamForIdForViewVariablesInList(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdForViewVariablesInList(
			List<MetaData> list, MetaData metaData) {

		// if (metaData.getRealClassName().equalsIgnoreCase("DiaNoLaboral")) {
		// String tmp = "";
		// System.out.println(tmp);
		// }

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();
		Utilities.getInstance().datesId = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				String type = Utilities.getInstance().getRealClassName(
						field2.getType().getName());

				String tmp1 = (name.substring(0, 1)).toUpperCase()
						+ name.substring(1);

				if (type.equalsIgnoreCase("date")) {
					if (!Utilities.getInstance().dates.contains(tmp1))
						Utilities.getInstance().datesId.add(tmp1);
				} else {
					finalParam2.add(tmp1);
				}

			}
		} else {
			finalParam = metaData.getPrimaryKey().getName();
			String type = metaData.getPrimaryKey().getRealClassName();
			String tmp1 = (finalParam.substring(0, 1)).toUpperCase()
					+ finalParam.substring(1);
			if (type.equalsIgnoreCase("date")) {
				if (!Utilities.getInstance().dates.contains(tmp1))
					Utilities.getInstance().datesId.add(tmp1);
			} else {
				finalParam2.add(tmp1);
			}

		}

		return finalParam2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId
	 * #finalParamForIdForViewForSetsVariablesInList(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdForViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				finalParam = finalParam + name;
				// String idName = metaData.getPrimaryKey().getName();
				// txtMasCodigo.setValue(entity.getId().getMasCodigo());
				String tmp1 = "txt" + (name.substring(0, 1)).toUpperCase()
						+ name.substring(1) + ".setValue(" + "entity.get"
						+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
						+ "()" + ".get" + (name.substring(0, 1)).toUpperCase()
						+ name.substring(1) + "()" + ");" + "txt"
						+ (name.substring(0, 1)).toUpperCase()
						+ name.substring(1) + ".setDisabled(true);";

				finalParam2.add(tmp1);
			}

		} else {
			// txtCliCedula.setValue(entity.getCliCedula());
			finalParam = metaData.getPrimaryKey().getName();
			String tmp1 = "txt" + (finalParam.substring(0, 1)).toUpperCase()
					+ finalParam.substring(1) + ".setValue(" + "entity.get"
					+ (finalParam.substring(0, 1)).toUpperCase()
					+ finalParam.substring(1) + "()" + ");" + "txt"
					+ (finalParam.substring(0, 1)).toUpperCase()
					+ finalParam.substring(1) + ".setDisabled(true);";
			finalParam2.add(tmp1);
		}

		return finalParam2;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId
	 * #finalParamForIdForDtoForSetsVariablesInList(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdForDtoForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		// if(metaData.getRealClassName().equalsIgnoreCase("alarma")){
		// String tmp = "";
		// System.out.println(tmp);
		// }

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				finalParam = finalParam + name;
				String type = field2.getType().getSimpleName();

				// String idName = metaData.getPrimaryKey().getName();
				// txtMasCodigo.setValue(entity.getId().getMasCodigo());
				if (!type.equalsIgnoreCase("date")) {
					String tmp1 = name
							+ "="
							+ metaData.getRealClassNameAsVariable()
							+ ".get"
							+ metaData.getPrimaryKey()
									.getGetNameOfPrimaryName() + "()" + ".get"
							+ (name.substring(0, 1)).toUpperCase()
							+ name.substring(1) + "().toString()" + ";";

					finalParam2.add(tmp1);
				} else {
					String tmp1 = name
							+ "="
							+ metaData.getRealClassNameAsVariable()
							+ ".get"
							+ metaData.getPrimaryKey()
									.getGetNameOfPrimaryName() + "()" + ".get"
							+ (name.substring(0, 1)).toUpperCase()
							+ name.substring(1) + "()" + ";";

					finalParam2.add(tmp1);
				}

			}

		} else {
			// txtCliCedula.setValue(entity.getCliCedula());
			finalParam = metaData.getPrimaryKey().getName();
			String type = metaData.getPrimaryKey().getRealClassName();
			if (!type.equalsIgnoreCase("date")) {
				String tmp1 = finalParam + "="
						+ metaData.getRealClassNameAsVariable() + ".get"
						+ (finalParam.substring(0, 1)).toUpperCase()
						+ finalParam.substring(1) + "().toString()" + ";";
				finalParam2.add(tmp1);
			} else {
				String tmp1 = finalParam + "="
						+ metaData.getRealClassNameAsVariable() + ".get"
						+ (finalParam.substring(0, 1)).toUpperCase()
						+ finalParam.substring(1) + "()" + ";";
				finalParam2.add(tmp1);
			}

		}

		return finalParam2;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId
	 * #finalParamForIdForDtoInViewForSetsVariablesInList(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdForDtoInViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData) {

		List<String> finalParam2 = new ArrayList<String>();
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();
				finalParam = finalParam + name;
				String type = field2.getType().getSimpleName();
				// String idName = metaData.getPrimaryKey().getName();
				// txtMasCodigo.setValue(entity.getId().getMasCodigo());
				if (!type.equalsIgnoreCase("date")) {
					String tmp1 = metaData.getRealClassNameAsVariable()
							+ "DTO2.set"
							+ name.substring(0, 1).toUpperCase()
							+ name.substring(1)
							+ "("
							+ metaData.getRealClassNameAsVariable()
							+ "Tmp.get"
							+ metaData.getPrimaryKey()
									.getGetNameOfPrimaryName() + "()" + ".get"
							+ (name.substring(0, 1)).toUpperCase()
							+ name.substring(1) + "().toString()" + ");";

					finalParam2.add(tmp1);
				} else {
					String tmp1 = metaData.getRealClassNameAsVariable()
							+ "DTO2.set"
							+ name.substring(0, 1).toUpperCase()
							+ name.substring(1)
							+ "("
							+ metaData.getRealClassNameAsVariable()
							+ "Tmp.get"
							+ metaData.getPrimaryKey()
									.getGetNameOfPrimaryName() + "()" + ".get"
							+ (name.substring(0, 1)).toUpperCase()
							+ name.substring(1) + "()" + ");";

					finalParam2.add(tmp1);
				}
			}

		} else {
			// txtCliCedula.setValue(entity.getCliCedula());
			String type = metaData.getPrimaryKey().getRealClassName();
			finalParam = metaData.getPrimaryKey().getName();
			if (!type.equalsIgnoreCase("date")) {
				String tmp1 = metaData.getRealClassNameAsVariable()
						+ "DTO2.set" + finalParam.substring(0, 1).toUpperCase()
						+ finalParam.substring(1) + "("
						+ metaData.getRealClassNameAsVariable() + "Tmp.get"
						+ (finalParam.substring(0, 1)).toUpperCase()
						+ finalParam.substring(1) + "().toString())" + ";";
				finalParam2.add(tmp1);
			} else {
				String tmp1 = metaData.getRealClassNameAsVariable()
						+ "DTO2.set" + finalParam.substring(0, 1).toUpperCase()
						+ finalParam.substring(1) + "("
						+ metaData.getRealClassNameAsVariable() + "Tmp.get"
						+ (finalParam.substring(0, 1)).toUpperCase()
						+ finalParam.substring(1) + "())" + ";";
				finalParam2.add(tmp1);
			}
		}

		return finalParam2;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#finalParamForIdClass(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdClass(List<MetaData> list,
			MetaData metaData) {
		List<String> finalParam = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			String id = metaData.getPrimaryKey().getRealClassName() + " "
					+ metaData.getPrimaryKey().getName() + "Class = " + "new "
					+ metaData.getPrimaryKey().getRealClassName() + "();";
			finalParam.add(id);
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				String setToId = metaData.getPrimaryKey().getName()
						+ "Class.set" + name.substring(0, 1).toUpperCase()
						+ name.substring(1, name.length()) + "(" + name + ");";
				finalParam.add(setToId);
			}
		}

		return finalParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#finalParamForIdClassAsVariables2(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdClassAsVariables2(List<MetaData> list,
			MetaData metaData) {
		List<String> finalParam = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				finalParam.add(name);
			}
		} else {
			finalParam.add(metaData.getPrimaryKey().getName());
		}

		return finalParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#finalParamForIdForViewClass(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public List<String> finalParamForIdForViewClass(List<MetaData> list,
			MetaData metaData) {
		List<String> finalParam = new ArrayList<String>();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			String id = metaData.getPrimaryKey().getRealClassName() + " "
					+ metaData.getPrimaryKey().getName() + " = " + "new "
					+ metaData.getPrimaryKey().getRealClassName() + "();";
			finalParam.add(id);
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				String nameFragment = name.substring(0, 1).toUpperCase()
						+ name.substring(1);

				String realType = field2.getType().toString().substring(
						(field2.getType().toString()).lastIndexOf(".") + 1,
						(field2.getType().toString()).length());

				String setToId = new String();

				// if (realType.equalsIgnoreCase("date")) {
				// setToId = metaData.getPrimaryKey().getName() + ".set"
				// + nameFragment + "((txt" + nameFragment
				// + ".getValue())==null||(txt" + nameFragment
				// + ".getValue()).equals(\"\")?null:" + "("
				// + realType + ")txt" + nameFragment
				// + ".getValue());";
				// } else {
				// setToId = metaData.getPrimaryKey().getName() + ".set"
				// + nameFragment + "((txt" + nameFragment
				// + ".getValue())==null||(txt" + nameFragment
				// + ".getValue()).equals(\"\")?null:new " + realType
				// + "(txt" + nameFragment
				// + ".getValue().toString()));";
				// }

				setToId = metaData.getPrimaryKey().getName() + ".set"
						+ nameFragment + "(FacesUtils.check" + realType
						+ "(txt" + nameFragment + "));";

				finalParam.add(setToId);
			}
		} else {
			String setToId = new String();

			// setToId = metaData.getPrimaryKey().getRealClassName() + " "
			// + metaData.getPrimaryKey().getName() + " = new "
			// + metaData.getPrimaryKey().getRealClassName() + "(" + "txt"
			// + metaData.getPrimaryKey().getGetNameOfPrimaryName()
			// + ".getValue().toString());";

			setToId = metaData.getPrimaryKey().getRealClassName() + " "
					+ metaData.getPrimaryKey().getName() + " = new "
					+ metaData.getPrimaryKey().getRealClassName() + "("
					+ "FacesUtils.check"
					+ metaData.getPrimaryKey().getType().getSimpleName()
					+ "(txt"
					+ metaData.getPrimaryKey().getGetNameOfPrimaryName()
					+ "));";

			finalParam.add(setToId);
		}

		return finalParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#finalParamForId(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public String finalParamForId(List<MetaData> list, MetaData metaData) {
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

			String finalCharacter = ""
					+ finalParam.charAt(finalParam.length() - 2);

			finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
					finalParam.length() - 2) : finalParam;

			return finalParam;
		} else {
			finalParam = metaData.getPrimaryKey().getRealClassName() + " "
					+ metaData.getPrimaryKey().getName();
		}

		return finalParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#finalParamForIdVariables(java.util.List,
	 * co.edu.usbcali.lidis.zathura.metadata.model.MetaData)
	 */
	public String finalParamForIdVariables(List<MetaData> list,
			MetaData metaData) {
		String finalParam = new String();

		if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
			Field[] field = metaData.getComposeKey().getDeclaredFields();
			for (Field field2 : field) {
				String name = field2.getName();

				finalParam = finalParam + name + ", ";
			}

			String finalCharacter = ""
					+ finalParam.charAt(finalParam.length() - 2);

			finalParam = finalCharacter.equals(",") ? finalParam.substring(0,
					finalParam.length() - 2) : finalParam;

			return finalParam;
		} else {
			finalParam = metaData.getPrimaryKey().getName();
		}

		return finalParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.edu.usbcali.lidis.zathura.generator.jeewebcentric.utils.
	 * IStringBuilderForId#neededIds(java.util.List)
	 */
	public void neededIds(List<MetaData> list) {
		for (MetaData metaData : list) {
			if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {

				Field[] field = metaData.getComposeKey().getDeclaredFields();

				for (int i = 0; i < field.length; i++) {
					Field field2 = field[i];
					String name = field2.getName();

					hashMapIds.put(name + "_" + metaData.getRealClassName(),
							metaData.getPrimaryKey().getName());
				}
			}
		}
		HashMap<String, String> map = hashMapIds;
	}

}
