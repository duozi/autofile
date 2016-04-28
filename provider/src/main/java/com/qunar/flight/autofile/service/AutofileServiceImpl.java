package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.api.AutofileService;
import com.qunar.flight.autofile.pojo.AutofileRequest;
import com.qunar.flight.autofile.util.CloseJar;
import com.qunar.flight.autofile.util.DownloadJar;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLClassLoader;

/**
 * Created by zhouxi.zhou on 2016/4/27.
 */
@Service(value="autofileService")
public class AutofileServiceImpl implements AutofileService {
    @Resource
    GetJsonServiceImpl getJsonService;
    @Resource
    GetXmlServiceImpl getXmlService;

    private static final String XML = "xml";
    private static final String JSON = "json";
    private final static Logger logger = LoggerFactory.getLogger(AutofileServiceImpl.class);


    @Override
    public String autofile(AutofileRequest autofileRequest) {
        String resultString = StringUtils.EMPTY;
        String jarPath=StringUtils.EMPTY;
        try {
            jarPath=DownloadJar.downloadFromUrl(autofileRequest.getGroupId(),autofileRequest.getArtifactId(),autofileRequest.getVersion());
        } catch (Exception e) {
            logger.error("down load jar failed!");
            resultString= "下载jar文件异常，可能是jar文件属性不正确";

        }

        URLClassLoader loader=null;
        try {
            loader=DownloadJar.autoLoad(jarPath);
        } catch (Exception e) {
            logger.error("load jar failed!");
            resultString= "加载jar包异常，可能是接口名或方法名不正确";
        }
        if (JSON.equalsIgnoreCase(autofileRequest.getType())) {

            resultString= getJsonService.getJson(loader,autofileRequest.getInterfaceName(), autofileRequest.getMethodName());

        } else if (XML.equalsIgnoreCase(autofileRequest.getType())) {

            resultString=getXmlService.getXml(loader,autofileRequest.getInterfaceName(), autofileRequest.getMethodName());

        }

        try {
            CloseJar.closeJar(loader);
        } catch (Exception e) {
            logger.error("unload jar error ");
            e.printStackTrace();
        }
        return  resultString;
    }


}
