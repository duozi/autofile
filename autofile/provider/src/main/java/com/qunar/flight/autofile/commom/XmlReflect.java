package com.qunar.flight.autofile.commom;

import com.qunar.flight.autofile.service.GetXmlServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qunar.api.pojo.DateTime;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Pattern;

/**
 * Created by zhouxi.zhou on 2016/2/28.
 */

public class XmlReflect {
    /**
     * @desc 通过反射来动态调用get 和 set 方法
     * @date 2010-10-14
     * @Version 1.0
     */
    private static Logger logger = LoggerFactory.getLogger(XmlReflect.class);

    @Resource
    GetXmlServiceImpl getXmlService;
    private Class cls;


    /**
     * 存放get方法
     */
    public Hashtable<String, Method> getMethods = null;
    /**
     * 存放set方法
     */
    public Hashtable<String, Method> setMethods = null;

    /**
     * 定义构造方法 -- 一般来说是个pojo
     *
     * @param c 目标对象
     */
    public XmlReflect(Class c) {
        cls = c;
        initMethods();
    }

    /**
     * @desc 初始化
     */
    public void initMethods() {
        getMethods = new Hashtable<String, Method>();
        setMethods = new Hashtable<String, Method>();

        Method[] methods = cls.getMethods();
        // 定义正则表达式，从方法中过滤出getter / setter 函数.
        String gs = "get(\\w+)";
        Pattern getM = Pattern.compile(gs);
        String ss = "set(\\w+)";
        Pattern setM = Pattern.compile(ss);
        // 把方法中的"set" 或者 "get" 去掉
        String rapl = "$1";
        String param;
        Field[] fields = cls.getDeclaredFields();

        for (int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            String methodName = m.getName();
            if (Pattern.matches(ss, methodName)) {
                param = setM.matcher(methodName).replaceAll(rapl).toLowerCase();
                setMethods.put(param, m);
            } else {

                for (Field field : fields) {
                    String fieldName = field.getName().toLowerCase();
                    if (methodName.toLowerCase().endsWith(fieldName)) {
                        getMethods.put(fieldName, m);
                        break;
                    }
                }

            }
        }
    }

    /**
     * @desc 调用set方法
     */
    public boolean setMethodValue(String property, boolean value) {
        Method m = setMethods.get(property.toLowerCase());
        if (m != null) {
            try {
                // 调用目标类的setter函数
                m.invoke(cls.newInstance(), value);
                return true;
            } catch (Exception ex) {
                System.out.println("invoke setter on " + property + " error: "
                        + ex.toString());
                return false;
            }
        }
        return false;
    }

    /**
     * @desc 调用get方法
     */
    public boolean getMethodValue(StringBuffer result, String property) {
        Method m = getMethods.get(property.toLowerCase());
        if (m != null) {
            try {
                /**
                 * 调用obj类的getter函数
                 */

                logger.info("{} default value is {}", property, String.valueOf(m.invoke(cls.newInstance())));
                String defaultValue = String.valueOf(m.invoke(cls.newInstance()));
                if (!defaultValue.equals("null")) {
                    StringUtil.addMidString(result, defaultValue);
                }
                return true;
            } catch (Exception ex) {
                System.out.println("invoke getter on " + property + " error: "
                        + ex.toString());
                return false;
            }
        }
        return false;
    }

    public void getSuperClass(Class cls, StringBuffer result, StringBuffer before) {

        Class superClass = cls.getSuperclass();
        String superClassName = superClass.getName();
        logger.info("super class is {}", superClassName);
        if (!superClassName.contains("Object")) {
            getSuperClass(superClass, result, before);

        }
        getSonClass(cls, result, before);


    }

    public void getSonClass(Class cls, StringBuffer result, StringBuffer before) {

        XmlReflect f = new XmlReflect(cls);
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Class partype = field.getType();
            logger.info("param type is {}", partype);


            String fieldName = field.getName();
            logger.info("param name is {}", fieldName);
            if (fieldName.equals("serialVersionUID")) {
                continue;
            }


            /**
             * 如果是自定义的类，获得他的属性
             */
            try {

                //if (!partype.getName().contains("java")) {

                if (partype.getName().contains("java.util.List")) {

                    Type genericFieldType = field.getGenericType();
                    Class listClass = getList(genericFieldType);
                    if (!isBaseDataType(listClass)) {
                        StringUtil.addXMLStartString(result, "list", before);
                        result.append("\n");
                        String[] className = listClass.getName().split("\\.");
                        int length = className.length;
                        String name = className[length - 1];
                        logger.info("list class is {}", className);
                        StringUtil.addXMLStartString(result, fieldName, before);
                        result.append("\n");
                        getSuperClass(listClass, result, before);
                        StringUtil.addXMLEndString(result, fieldName, before, true);
                        StringUtil.addXMLEndString(result, "list", before, true);
                    }else{
                        StringUtil.addXMLStartString(result, fieldName, before);
                        result.append("[");
                        if(listClass.getName().equals("java.lang.String")){
                            result.append("\"\"");
                        }
                        result.append("]");
                        StringUtil.addXMLEndString(result, fieldName, before, false);
                    }

                } else if (!isBaseDataType(partype)) {
                    //枚举类型
                    if (partype.isEnum()) {
                        StringUtil.addXMLStartString(result, fieldName, before);
                        StringBuffer enumString = new StringBuffer();
                        Field[] enumFields = partype.getDeclaredFields();
                        for (Field efield : enumFields) {

                            logger.info("param type is {}", efield.getName());
                            enumString.append(efield.getName()).append("|");
                        }
                        StringUtil.addMidString(result, String.valueOf(enumString).replace("|$VALUES|", ""));
                        StringUtil.addXMLEndString(result, fieldName, before, false);

                    } else {

                        //其他自定义类
                        StringUtil.addXMLStartString(result, fieldName, before);
                        result.append("\n");
                        getSonClass(partype, result, before);
                        StringUtil.addXMLEndString(result, fieldName, before, true);
                    }
                } else {
                    StringUtil.addXMLStartString(result, fieldName, before);
                    /**
                     * 获得默认值
                     */
                    f.getMethodValue(result, fieldName.toLowerCase());
                    StringUtil.addXMLEndString(result, fieldName, before, false);
                }


            } catch (Exception e) {
                logger.error("自定义类型判断错误");
            }
        }


    }

    /**
     * 判断一个类是否为基本数据类型。
     *
     * @param clazz 要判断的类。
     * @return true 表示为基本数据类型。
     */
    public static boolean isBaseDataType(Class clazz) throws Exception {
        return
                (
                        clazz.equals(String.class) ||
                                clazz.equals(Integer.class) ||
                                clazz.equals(Byte.class) ||
                                clazz.equals(Long.class) ||
                                clazz.equals(Double.class) ||
                                clazz.equals(Float.class) ||
                                clazz.equals(Character.class) ||
                                clazz.equals(Short.class) ||
                                clazz.equals(BigDecimal.class) ||
                                clazz.equals(BigInteger.class) ||
                                clazz.equals(Boolean.class) ||
                                clazz.equals(Date.class) ||
                                clazz.equals(DateTime.class) ||
                                clazz.isPrimitive() ||
                                clazz.getName().contains("java")
                );
    }

    /**
     * 获得list里面的类型
     *
     * @param type
     * @return
     */
    public static Class getList(Type type) {
        Class listClass = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) type;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                listClass = (Class) fieldArgType;
                logger.info("listClass = " + listClass);
            }
        }
        return listClass;
    }

}


//    // 一个model
//    static class Test {
//        boolean a = false;
//        boolean b = false;
//        boolean c = true;
//
//        public boolean isA() {
//            return a;
//        }
//
//        public void setA(boolean a) {
//            this.a = a;
//        }
//
//        public boolean isB() {
//            return b;
//        }
//
//        public void setB(boolean b) {
//            this.b = b;
//        }
//
//        public boolean isC() {
//            return c;
//        }
//
//        public void setC(boolean c) {
//            this.c = c;
//        }

// 测试方法
//    public static void main(String args[]) {
//        Class<?> c = null;
//        try {
//            c = Class.forName("com.qunar.flight.autofile.common.Reflect$Test");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Test ah = new Test();
//        System.out.println(ah.getClass().getName());
//        SetMethodReflect smr = new SetMethodReflect(ah);
//
//
//        smr.getMethodValue("a");
//        smr.getMethodValue("b");
//        smr.getMethodValue("c");
//    }

