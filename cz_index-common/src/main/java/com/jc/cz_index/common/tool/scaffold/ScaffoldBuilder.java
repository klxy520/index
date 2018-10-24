package com.jc.cz_index.common.tool.scaffold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScaffoldBuilder
{
	protected final Log					logger				= LogFactory.getLog(getClass());
	
	protected final static String		PKG_XML			= "main.resources.config.mybatis";
	
	protected final static String		PKG_PREFIX_PRE			= "main.java.";
	protected final static String		PKG_PREFIX				= "com.jc.cz_index.";
	protected final static String		PKG_SUFFIX_MODEL		= ".model.";
	protected final static String		PKG_SUFFIX_DAO			= ".dao.";
	
	protected final static String		PKG_SUFFIX_CORE			= ".core.service."; // core
	protected final static String       PKG_SUFFIX_FEM   		= ".fem.service."; //  fem
	protected final static String       PKG_SUFFIX_API  	    = ".api.service."; //  api
	protected final static String		PKG_IMPL				= "impl";
	
	protected String					pkgName;
	protected String					clzName;
	protected String                    clientOrManager; //// fsl: 用以区分是生成"客户端"还是"管理端" 
	protected TableInfo					tableInfo;
	private final Map<String, String>	mapping;
	
	
	public ScaffoldBuilder(String pkgName, String clzName, String clientOrManager, TableInfo tableInfo)
	{
//		this.pkgName = PKG_PREFIX_PRE + PKG_PREFIX + pkgName;
		this.pkgName = PKG_PREFIX_PRE + PKG_PREFIX.substring(0, PKG_PREFIX.length() - 1);
		this.clzName = clzName;
		this.clientOrManager = clientOrManager;  // fsl: 客户端还是管理端
		this.tableInfo = tableInfo;
		
		mapping = new HashMap<String, String>();
		
		mapping.put("pkgName", pkgName);
		mapping.put("clzName", clzName);
		mapping.put("clientOrManager", clientOrManager);  // fsl: 客户端还是管理端
		mapping.put("clzNameLC", StringUtils.uncapitalize(getModelPath()));
		mapping.put("tblName", tableInfo.getName());
		mapping.put("modelPath", getModelPath());
		mapping.put("modelQueryPath", getModelQueryPath());
		
		mapping.put("iDataProvider", PKG_PREFIX+"dao.IDataProvider");
		mapping.put("daoPath", getDaoPath());
		mapping.put("daoImplPath", getDaoImplPath());
		mapping.put("managerPath", getManagerPath());
		mapping.put("managerImplPath", getManagerImplPath());
		// for Model java
		mapping.put("fieldsDeclareInfo", tableInfo.getFieldsDeclareInfo());
		// for model sqlmapping
		mapping.put("resultMap", tableInfo.getResultMap());
		mapping.put("baseCol", tableInfo.getBaseCol());
		mapping.put("whereSql", tableInfo.getWhereSql());
		mapping.put("orderSql", tableInfo.getOrderSql());
		mapping.put("updateSetSql", tableInfo.getUpdateSetSql());
		mapping.put("insertStatement", tableInfo.getInsertStatement());
		mapping.put("updateStatement", tableInfo.getUpdateStatement());
		mapping.put("queryParams", PKG_PREFIX + "dao.QueryParams");
		mapping.put("primaryKey", tableInfo.getPrimaryKey());
		// 批量插入字段 insertListFields
        mapping.put("insertListFields", tableInfo.getInsertListFields());
        // 批量插入字段values insertListFieldsValues
        mapping.put("insertListFieldsValues", tableInfo.getInsertListFieldsValues());
	}
	

	public String getModelPath()
	{
		return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_MODEL + clzName;
	}
	
	public String getModelQueryPath(){
		return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_MODEL + clzName+"Query";
	}

	public String getSqlMapFile()
	{
		return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_MODEL + clzName + ".xml";
	}
	

	public String getDaoPath()
	{
		return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_DAO + "I" + clzName + "Dao";
	}
	

	public String getDaoImplPath()
	{
		return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_DAO + "I" + clzName + "Dao";
	}
	

	public String getManagerPath()
	{   // fsl
	    if("core".equals(this.clientOrManager)){ //core
	        return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_CORE + "I" + clzName + "Service";
	    }else if("fem".equals(this.clientOrManager)){ //fem
	        return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_FEM + "I" + clzName + "Service";
	    }else { //api
	    	return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_API + "I" + clzName + "Service";
	    }
		
	}
	

	public String getManagerImplPath()
	{   // fsl
		if("core".equals(this.clientOrManager)){ // core
	        return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_CORE + PKG_IMPL + StringUtil.DOT + clzName + "Service"
            + StringUtils.capitalize(PKG_IMPL);
		}else if("fem".equals(this.clientOrManager)){ // fem
	        return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_FEM + PKG_IMPL + StringUtil.DOT + clzName + "Service"
                    + StringUtils.capitalize(PKG_IMPL);
	    }else { //api
	    	return pkgName.replace(PKG_PREFIX_PRE, "") + PKG_SUFFIX_API + PKG_IMPL + StringUtil.DOT + clzName + "Service"
            + StringUtils.capitalize(PKG_IMPL);
	    }
		
	}
	

	public List<FileGenerator> buildGenerators()
	{
		List<FileGenerator> list = new ArrayList<FileGenerator>();
		// model
		list.add(new FileGenerator(pkgName + ".model", clzName, "Model.txt", mapping));
		list.add(new FileGenerator(PKG_XML, "mybatis-" + clzName, "SqlMap.txt", mapping, "xml"));
		
		// fsl
		// dao
		list.add(new FileGenerator(pkgName + ".dao", "I" + clzName + "Dao", "DAO.txt", mapping));
		
		// service
        list.add(new FileGenerator(pkgName + "."+clientOrManager+".service", "I" + clzName + "Service", "Service.txt", mapping));
        list.add(new FileGenerator(pkgName + "."+clientOrManager+".service.impl", clzName + "Service", "ServiceImpl.txt", mapping));
        
        // controller
        list.add(new FileGenerator(pkgName + "."+clientOrManager+".web.controller." + this.mapping.get("pkgName"), clzName + "Controller", "Controller.txt", mapping));
		
		// fsl
		return list;
	}
	
}
