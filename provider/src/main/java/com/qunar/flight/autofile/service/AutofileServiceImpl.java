package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.util.CloseJar;
import com.qunar.flight.autofile.util.OnloadJar;

import javax.annotation.Resource;
import java.net.URLClassLoader;
import java.util.logging.Logger;

/**
 * Created by zhouxi.zhou on 2016/4/27.
 */
@Service(value = "autofileService")
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
        String jarPath = StringUtils.EMPTY;
        try {
            jarPath = OnloadJar.downloadFromUrl(autofileRequest.getGroupId(), autofileRequest.getArtifactId(), autofileRequest.getVersion());
        } catch (Exception e) {
            logger.error("down load jar failed!");
            e.printStackTrace();
            resultString = "下载jar文件异常，可能是jar文件属性不正确";
            return resultString;

        }

        URLClassLoader loader = null;
        try {
            loader = OnloadJar.autoLoad(jarPath);
        } catch (Exception e) {

            logger.error("load jar failed!");
            e.printStackTrace();
            resultString = "加载jar包异常，可能是接口名或方法名不正确";
            return resultString;
        }

        try {
            if (JSON.equalsIgnoreCase(autofileRequest.getType())) {
                resultString = getJsonService.getJson(loader, autofileRequest.getInterfaceName(), autofileRequest.getMethodName());

            } else if (XML.equalsIgnoreCase(autofileRequest.getType())) {

                resultString = getXmlService.getXml(loader, autofileRequest.getInterfaceName(), autofileRequest.getMethodName());

            }


        } catch (Exception e) {
            logger.error("get param error");
            e.printStackTrace();
            resultString = "获得参数异常了，请联系zhouxi.zhou";
            return resultString;
        }


        try {
            CloseJar.closeJar(loader);
        } catch (Exception e) {
            logger.error("unload jar error ");
            e.printStackTrace();
        }
        return resultString;
    }


}
