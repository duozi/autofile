package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.commom.XmlReflect;
import com.qunar.flight.autofile.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLClassLoader;

/**
 * Created by zhouxi.zhou on 2016/3/12.
 */

@Service(value = "getXmlService")
public class GetXmlServiceImpl {
    private final static Logger logger = LoggerFactory.getLogger(GetXmlServiceImpl.class);
    public static StringBuffer result = new StringBuffer();
    public static StringBuffer before = new StringBuffer();
    public static int i = 1;


    public String getXml(URLClassLoader loader, String interfaceName, String methodName) {

        result.setLength(0);
        before.setLength(0);
        i=1;
        Class<?> c = null;
        try {
            c = loader.loadClass(interfaceName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            String name = method.getName();
            //获得调用的接口的方法
            if (name.endsWith(methodName)) {
                Type[] types = method.getGenericParameterTypes();

                for (Type type : types) {
                    String parname = type.toString().substring(6);

                    StringUtil.addXMLStartString(result, "var" + i, before);
                    result.append("\n");

                    try {
                        c = loader.loadClass(parname);
                        if (!XmlReflect.isBaseDataType(c)) {
                            XmlReflect f = new XmlReflect(c);
                            f.getSuperClass(c, result, before);
                        }


                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    StringUtil.addXMLEndString(result, "var" + i, before, false);
                    i++;
                }
            }
        }
        logger.info("{}", result);
        return result.toString();
    }


}


