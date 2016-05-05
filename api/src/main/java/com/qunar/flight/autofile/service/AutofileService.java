package com.qunar.flight.autofile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by zhouxi.zhou on 2016/5/5.
 */
public class AutofileService {
    private final static Logger logger = LoggerFactory.getLogger(AutofileService.class);
    @Resource
    GetJsonServiceImpl getJsonService;
    @Resource
    GetXmlServiceImpl getXmlService;
    public void autofile(String interfaceName, String methodName,String type) {
            try {
                if (type.equalsIgnoreCase("json")) {
                getJsonService.getJson(interfaceName, methodName);
                } else if (type.equalsIgnoreCase("xml")) {
                getXmlService.getXml( interfaceName, methodName);

                }

                } catch (Exception e) {

                e.printStackTrace();
            }


    }

}
