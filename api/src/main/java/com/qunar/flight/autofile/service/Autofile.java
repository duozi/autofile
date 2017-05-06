package com.qunar.flight.autofile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhouxi.zhou on 2016/5/5.
 */
public class Autofile {
    private final static Logger logger = LoggerFactory.getLogger(Autofile.class);


    public static void autofile(String interfaceName, String methodName, String type) {

        try {
            if (type.equalsIgnoreCase("json")) {
                GetJsonServiceImpl getJsonService = new GetJsonServiceImpl();
                getJsonService.getJson(interfaceName, methodName);
            } else if (type.equalsIgnoreCase("xml")) {
                GetXmlServiceImpl getXmlService = new GetXmlServiceImpl();
                getXmlService.getXml(interfaceName, methodName);

            }

        } catch (Exception e) {
            logger.error("接口名或方法名不存在");
            e.printStackTrace();
        }


    }



}
