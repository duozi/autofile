package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.api.GetXmlService;
import com.qunar.flight.autofile.commom.StringUtil;
import com.qunar.flight.autofile.commom.XmlReflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by zhouxi.zhou on 2016/3/12.
 */

@Service(value = "getXmlService")
public class GetXmlServiceImpl implements GetXmlService {
    private final static Logger logger = LoggerFactory.getLogger(GetXmlServiceImpl.class);
    public static StringBuffer result = new StringBuffer();
    public static StringBuffer before = new StringBuffer();
    public static int i = 1;



    public String getXml(String interfaceName, String methodName) {

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

                    try {
                        c = Class.forName(parname);
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
//        logger.info("{}", result);
        return result.toString();
    }

    public static void main(String[] args) {

        String s = " \"outboundFlights\": [\n" +
                "                        {\n" +
                "                            \"allowedPieces\": 0,\n" +
                "                            \"allowedWeight\": 0,\n" +
                "                            \"arr\": \"HKG\",\n" +
                "                            \"arrDate\": \"20160329\",\n" +
                "                            \"arrTime\": \"1335\",\n" +
                "                            \"arrTower\": \"\",\n" +
                "                            \"asr\": false,\n" +
                "                            \"cabin\": \"H\",\n" +
                "                            \"cabinStatuses\": [],\n" +
                "                            \"dpt\": \"XIY\",\n" +
                "                            \"dptDate\": \"20160329\",\n" +
                "                            \"dptTime\": \"1110\",\n" +
                "                            \"dptTower\": \"T3\",\n" +
                "                            \"etkt\": true,\n" +
                "                            \"flightNum\": \"MU223\",\n" +
                "                            \"flightTime\": \"145\",\n" +
                "                            \"meal\": \"L\",\n" +
                "                            \"planeStyle\": \"320\",\n" +
                "                            \"routNo\": null,\n" +
                "                            \"share\": false,\n" +
                "                            \"shareAirline\": null,\n" +
                "                            \"shareFlightNum\": null,\n" +
                "                            \"stopPoints\": [],\n" +
                "                            \"stops\": 0\n" +
                "                        }\n" +
                "                    ]\n";
//        Iterable<String> splitWhite = Splitter.on(",").trimResults().omitEmptyStrings().split(s);
//        for (String s2 : splitWhite) {
//            System.out.println(s2);
//        }
        System.out.println(s.replaceAll(" ","").length());
    }
}


