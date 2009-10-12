package co.edu.usbcali.lidis.zathura.generator.jeegwtcentric.utils;

import java.util.List;

import co.edu.usbcali.lidis.zathura.metadata.model.MetaData;

public interface IStringBuilderForId {

	public String finalParamForIdClassAsVariablesAsString(List<MetaData> list,
			MetaData metaData);

	public List<String> finalParamForIdClassAsVariables(List<MetaData> list,
			MetaData metaData);

	public List<String> finalParamForVariablesDataTablesForIdAsList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForIdVariablesAsList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForIdForViewVariablesInList(
			List<MetaData> list, MetaData metaData);

	public List<String> finalParamForIdForViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForIdForDtoForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForIdForDtoInViewForSetsVariablesInList(
			List<MetaData> theMetaData, MetaData metaData);

	public List<String> finalParamForIdClass(List<MetaData> list,
			MetaData metaData);

	public List<String> finalParamForIdClassAsVariables2(List<MetaData> list,
			MetaData metaData);

	public List<String> finalParamForIdForViewClass(List<MetaData> list,
			MetaData metaData);

	public String finalParamForId(List<MetaData> list, MetaData metaData);

	public String finalParamForIdVariables(List<MetaData> list,
			MetaData metaData);

	public void neededIds(List<MetaData> list);

}