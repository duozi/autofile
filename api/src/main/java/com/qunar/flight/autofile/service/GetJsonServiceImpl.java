package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.commom.JsonReflect;
import com.qunar.flight.autofile.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by zhouxi.zhou on 2016/3/12.
 */
@Service(value = "getJsonService")
public class GetJsonServiceImpl {
    private final static Logger logger = LoggerFactory.getLogger(GetJsonServiceImpl.class);
    public static StringBuffer result = new StringBuffer();
    public static StringBuffer before = new StringBuffer();
    public static int i = 1;


    public String getJson(String interfaceName, String methodName) throws Exception {
        result.setLength(0);
        before.setLength(0);
        i = 1;
        Class<?> c = null;
        try {
            c = Class.forName(interfaceName);

            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                String name = method.getName();
                //获得调用的接口的方法
                if (name.endsWith(methodName)) {
                    Type[] types = method.getGenericParameterTypes();

                    for (Type type : types) {
                        StringUtil.addXMLStartString(result, "var" + i, before);
                        result.append("\n");
                        String parname = StringUtils.EMPTY;
                        if (type.toString().startsWith("java.util.List")) {
                            parname = type.toString().substring(15, type.toString().length() - 1);
                            c = Class.forName(parname);
                            if (!JsonReflect.isBaseDataType(c)) {
                                StringUtil.addJsonStartList(result, before);
                                StringUtil.addHead(result, before);
                                JsonReflect f = new JsonReflect(c);
                                f.getSuperClass(c, result, before);
                                StringUtil.addEnd(result, before);
                                StringUtil.addJsonEndList(result, before);
                            } else if (parname.equals("java.lang.String")) {
                                result.append("[\"\"],\n");
                            } else {
                                result.append("[],\n");
                            }

                        } else {
                            parname = type.toString().substring(6);


                            c = Class.forName(parname);
                            if (!JsonReflect.isBaseDataType(c)) {
                                StringUtil.addHead(result, before);
                                JsonReflect f = new JsonReflect(c);
                                f.getSuperClass(c, result, before);
                                StringUtil.addEnd(result, before);
                            }
                        }


                        StringUtil.addXMLEndString(result, "var" + i, before, false);
                        i++;
                    }
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("interface or class is not exit");
        }
        logger.info("{}", result);
        return result.toString();
    }
}

