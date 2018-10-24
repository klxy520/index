package com.jc.cz_index.common.tool;

import com.jc.cz_index.common.tool.scaffold.ScaffoldGen;

public class ScaffoldGenerator {

    public static void main(String[] args) throws Exception {
        // arg1 子系统名
        // arg2 业务对象名,即Model,双词以上要求单词首字大写
        // arg3 表名
    	
        // arg4 对应模块: core/fem/api
        ScaffoldGen generator = new ScaffoldGen("syncLog", "addressSyncLog", "address_sync_log", "core");
        generator.disableCheckRequiredCol();
        generator.execute(false);

    }

}
