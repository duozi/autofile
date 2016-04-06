package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.api.GetJsonService;
import com.qunar.flight.autofile.commom.JsonReflect;
import com.qunar.flight.autofile.commom.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by zhouxi.zhou on 2016/3/12.
 */
@Component
public class GetJsonServiceImpl implements GetJsonService {
    private final static Logger logger = LoggerFactory.getLogger(GetJsonServiceImpl.class);
    public static StringBuffer result = new StringBuffer();
    public static StringBuffer before = new StringBuffer();
    public static int i = 1;


    public String getJson(String interfaceName, String methodName) {
        result.setLength(0);
        before.setLength(0);
        Class<?> c = null;
        try {
            c = Class.forName(interfaceName);
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
                    StringUtil.addHead(result, before);
                    try {
                        c = Class.forName(parname);
                        if (!JsonReflect.isBaseDataType(c)) {
                            JsonReflect f = new JsonReflect(c);
                            StringUtil.addClassHead(result,c.getName(),before);
                            f.getSuperClass(c, result, before);
                            StringUtil.addEnd(result,before);
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
//        logger.info("{}", result);
        return result.toString();
    }
}

