package com.jc.cz_index.core.web.task;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.impl.FrontEndMachineService;
import com.jc.cz_index.core.web.webservice.FemServiceClient;
import com.jc.cz_index.model.FrontEndMachine;

/**
 * @ClassName: AppTask
 * @Description:TODO(定时执行对象)
 * @author: ZYX
 * @date: 2017年12月28日 上午11:36:06
 * @Copyright: 2017 ... Inc. All rights reserved. Note: 将所有对象推送至前置机
 */
@Component
public class SyncTask {
    private static final Logger logger = Logger.getLogger(SyncTask.class);
    @Autowired
    private FemServiceClient    femServiceClient;



    /**
     * 
     * 描述：定时同步主索引数据： 每5分钟触发
     * 
     * @author yangyongchuan 2018年1月9日 上午9:44:10
     * @version 1.0
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void baseTask() {
        logger.info("-----------定时同步主索引数据开始了-----------");
        long nowTime = DateUtils.getTime();
        // 第一步：提取当前可用的前置机信息
        List<FrontEndMachine> ls = SpringConfigTool.getBean("frontEndMachineService", FrontEndMachineService.class).queryEffectiveList();
        // 第二步：执行同步方法
        for (FrontEndMachine frontEndMachine : ls) {
            String url = frontEndMachine.getFrontEndMachineaddress();
            // 如果地址可达
            if (WebUtils.urlIsReach(url)) {
                // 同步基本身份信息
                try {
                    femServiceClient.sendPersons(frontEndMachine);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // 同步联系人信息
                try {
                    femServiceClient.sendContact(frontEndMachine);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // 同步卡信息
                try {
                    femServiceClient.sendCard(frontEndMachine);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // 同步地址信息
                try {
                    femServiceClient.sendAddress(frontEndMachine);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // 同步证件信息
                try {
                    femServiceClient.sendCertificate(frontEndMachine);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // 同步联系方式信息
                try {
                    femServiceClient.sendWay(frontEndMachine);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("-----------定时同步主索引数据结束-----------总耗时：" + (DateUtils.getTime() - nowTime) + "毫秒");
    }
}
