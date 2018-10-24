package com.jc.cz_index.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringConfigTool implements ApplicationContextAware {

    private static ApplicationContext ac               = null;
    private static SpringConfigTool   springConfigTool = null;



    public synchronized static SpringConfigTool init() {
        if (springConfigTool == null) {
            springConfigTool = new SpringConfigTool();
        }
        return springConfigTool;
    }



    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }



    public synchronized static Object getBean(String beanName) {
        if (null == ac) {
            return null;
        }
        return ac.getBean(beanName);
    }



    public static <T> T getBean(String name, Class<T> requiredType) {
        if (null == ac) {
            return null;
        }
        return ac.getBean(name, requiredType);
    }
}
