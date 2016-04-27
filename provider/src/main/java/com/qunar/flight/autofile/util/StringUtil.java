package com.qunar.flight.autofile.util;

import com.google.common.base.Strings;

/**
 * Created by zhouxi.zhou on 2016/3/9.
 */
public class StringUtil {
    public static void addXMLStartString(StringBuffer result, String s, StringBuffer before) {
        result.append(before).append("<").append(s).append(">");
        addBefore(before);
    }

    public static void addMidString(StringBuffer result, String s) {
        result.append(s);
    }

    public static void addXMLEndString(StringBuffer result, String s, StringBuffer before, boolean falg) {
        delBefore(before);
        if (falg) {
            result.append(before);
        }
        result.append("</").append(s).append(">\n");


    }

    public static boolean isEmptyOrNull(String content) {
        return Strings.isNullOrEmpty(content) || Strings.isNullOrEmpty(content.trim());
    }

    public static void addBefore(StringBuffer before) {
        before.append("\t");

    }

    public static void delBefore(StringBuffer before) {
        before.deleteCharAt(before.length() - 1);

    }

    public static void addHead(StringBuffer result, StringBuffer before) {

        result.append(before).append("{\n");
        addBefore(before);
    }


    public static void addClassHead(StringBuffer result, String classsName, StringBuffer before) {
        result.append(before).append("\"@type\":\"" + classsName + "\",\n");

    }

    public static void addEnd(StringBuffer result, StringBuffer before) {
        delBefore(before);
        result.append(before).append("}\n");

    }


    public static void addJsonStartList(StringBuffer result, StringBuffer before) {

        result.append("\n").append(before).append("[").append("\n");
        addBefore(before);
    }

    public static void addJsonEndList(StringBuffer result, StringBuffer before) {
        delBefore(before);
        result.append(before).append("],").append("\n");

    }

    public static void addJsonStringDefaultValue(StringBuffer result, String value) {
        if (value == null) {
            result.append("\"").append("\",\n");
        } else {
            result.append("\"").append(value).append("\",\n");
        }
    }

    public static void addJsonOtherDefaultValue(StringBuffer result, String value) {
        if (value == null) {
            result.append(",\n");
        } else {
            result.append(value).append(",\n");
        }

    }

    public static void main(String[] args) {
//        String s=test();
//
//        System.out.println(s==null);
    }


    public static void addJsonStart(StringBuffer result, String fieldName, StringBuffer before) {
        result.append(before).append("\"").append(fieldName).append("\"").append(":");

    }
}
