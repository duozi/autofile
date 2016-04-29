package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.commom.XmlReflect;
import com.qunar.flight.autofile.util.StringUtil;
import org.apache.commons.lang.StringUtils;
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


    public String getXml(URLClassLoader loader, String interfaceName, String methodName) throws Exception {

        result.setLength(0);
        before.setLength(0);
        i = 1;
        Class<?> c = null;
        try {
            c = loader.loadClass(interfaceName);

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

                            c = loader.loadClass(parname);
                            if (!XmlReflect.isBaseDataType(c)) {
                                StringUtil.addXMLStartString(result, "list", before);
                                result.append("\n");
                                XmlReflect f = new XmlReflect(c);
                                f.getSuperClass(c, result, before);
                                StringUtil.addXMLEndString(result, "list", before, true);
                            } else {
                                result.append("[");
                                if (parname.equals("java.lang.String")) {
                                    result.append("\"\"");
                                }
                                result.append("]");
                            }

                        } else {
                            parname = type.toString().substring(6);

                            c = loader.loadClass(parname);
                            if (!XmlReflect.isBaseDataType(c)) {
                                XmlReflect f = new XmlReflect(c);
                                f.getSuperClass(c, result, before);
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


