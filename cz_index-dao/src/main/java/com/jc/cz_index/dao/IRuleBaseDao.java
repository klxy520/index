package com.jc.cz_index.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.RuleBase;

/**
 * ruleBase Ibatis Dao 接口 Created by
 */
@Repository
public interface IRuleBaseDao extends IDataProvider<RuleBase, Long> {
    /**
     * 根据输入单位名称，取出对应所有单位规则名称
     * 
     * @param unitName
     * @return
     */
    public List<String> getRuleUnitNameList(Map<String, String> map);
}
