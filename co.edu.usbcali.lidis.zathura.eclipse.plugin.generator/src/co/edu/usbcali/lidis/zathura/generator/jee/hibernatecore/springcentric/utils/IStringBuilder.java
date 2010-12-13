package co.edu.usbcali.lidis.zathura.generator.jee.hibernatecore.springcentric.utils;

import java.util.List;

import co.edu.usbcali.lidis.zathura.metadata.model.Member;
import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;

public interface IStringBuilder {

	// finalParameter(List<MetaData> theMetaData, MetaData metaData) {
	public String finalParam(List<MetaData> theMetaData, MetaData metaData);

	public String finalParamVariables(List<MetaData> theMetaData,
			MetaData metaData);

	public String[] getTypeAndvariableForManyToOneProperties(String strClass,
			List<MetaData> theMetaData);

	public List<String> finalParamVariablesAsList(List<MetaData> theMetaData,
			MetaData metaData);

	public List<String> finalParamVariablesAsList2(List<MetaData> theMetaData,
			MetaData metaData);

	public List<String> finalParamVariablesDatesAsList2(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForVariablesDataTablesAsList(
			List<MetaData> theMetaData, MetaData metaData);

	public String finalParamForView(List<MetaData> theMetaData,
			MetaData metaData);

	public String finalParamForDtoUpdate(List<MetaData> theMetaData,
			MetaData metaData);

	public String finalParamForDtoUpdateOnlyVariables(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForViewVariablesInList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForDtoForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForDtoInViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> getVariableForManyToOneProperties(
			List<Member> manyToOne, List<MetaData> theMetaData);

	public List<String> getStringsForManyToOneProperties(
			List<Member> manyToOne, List<MetaData> theMetaData);

}