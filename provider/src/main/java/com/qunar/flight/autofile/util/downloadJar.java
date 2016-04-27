package com.qunar.flight.autofile.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

/**
 * Created by zhouxi.zhou on 2016/4/26.
 */
public class downloadJar {
    public static final String DIR = "c:\\";
    public static final String SPLITE = "-";
    public static final String FILE_END = ".jar";
    public static final String URL_HEAD = "http://nexus.corp.qunar.com/nexus/service/local/artifact/maven/redirect?r=";
    public static final String SNAPSHOT = "-SNAPSHOT";
    private final static Logger logger = LoggerFactory.getLogger(downloadJar.class);


    public static String downloadFromUrl(String group,String artifact, String version) {
        String fileName = DIR+artifact + SPLITE + version + FILE_END;
        if (StringUtils.isEmpty(fileName)) {
            logger.error("get jar filrName failed");
        }

        logger.info(fileName);
        String url = getUrl(group, artifact, version);
        logger.info(url);
        try {
            URL httpurl = new URL(url);
            System.out.println(fileName);
            File f = new File(fileName);
            if (f.exists()) {
                logger.info("this jar download alreadly");
                return fileName;
            } else {
                FileUtils.copyURLToFile(httpurl, f);
                if (!f.exists()) {
                    logger.error("download jar failed!");
                    return null;
                } else {
                    return fileName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("download jar failed!");
            return null;
        }

    }

    public static String getUrl(String group, String artifact, String version) {
        String url = StringUtils.EMPTY;
        if (version.endsWith(SNAPSHOT)) {
            url = URL_HEAD + "snapshots" + "&g=" + group + "&a=" + artifact + "&v=" + version + "&e=jar";
        } else {
            url = URL_HEAD + "releases" + "&g=" + group + "&a=" + artifact + "&v=" + version + "&e=jar";
        }
        if (StringUtils.isEmpty(url)) {
            logger.error("get url failed");
        }
        return url;
    }

    public static void main(String[] args) {
//        downloadFromUrl("http://nexus.corp.qunar.com/nexus/service/local/artifact/maven/redirect?r=releases&g=com.qunar.flight.tts&a=officemanage.api&v=1.1.10&e=jar", "c:\\");

    }

//    public static void main(String[] args) {
//        try {
//            // 创建指向jar文件的URL
//            URL url = new URL("jar:http://nexus.corp.qunar.com/nexus/service/local/artifact/maven/redirect?r=releases&g=com.qunar.flight.tts&a=officemanage.api&v=1.1.9&e=jar!/");
//            JarURLConnection conn = (JarURLConnection) url.openConnection();
//            JarFile jarfile = conn.getJarFile();
//            System.out.println(jarfile.getJarEntry("/com/qunar/flight/tts/officemanageapi/api/IIBEWhiteListService.class"));
//            String entryName = conn.getEntryName();
//            System.out.println(entryName);
//            // 创建指向文件系统的URL
//
////            url = new URL("jar:file:/c:/officemanage.jar!/");
////            // 读取jar文件
////           conn = (JarURLConnection) url.openConnection();
////             jarfile = conn.getJarFile();
////            // 如果URL没有任何入口，则名字为null
////            entryName = conn.getEntryName(); // null
////            System.out.println(entryName);
////            // 创建一个指向jar文件里一个入口的URL
////            url = new URL("jar:file:/c:/officemanage.jar!/com/qunar/flight/tts/officemanageapi/api/IIBEWhiteListService.class");
////            // 读取jar文件
////            conn = (JarURLConnection) url.openConnection();
////            jarfile = conn.getJarFile();
////            // 此时的入口名字应该和指定的URL相同
////            entryName = conn.getEntryName();
////            // 得到jar文件的入口
////            JarEntry jarEntry = conn.getJarEntry();
//        } catch (MalformedURLException e) {
//            System.out.println("error2");
//        } catch (IOException e) {
//            System.out.println("error1");
//        }
//    }
}
