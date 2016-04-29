package com.qunar.flight.autofile.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by zhouxi.zhou on 2016/4/26.
 */
public class OnloadJar {
    public static final String DIR = "/autofileAddJar/";
    public static final String SPLITE = "-";
    public static final String FILE_END = ".jar";
    public static final String URL_HEAD = "http://nexus.corp.qunar.com/nexus/service/local/artifact/maven/redirect?r=";
    public static final String SNAPSHOT = "-SNAPSHOT";
    private final static Logger logger = LoggerFactory.getLogger(OnloadJar.class);

    public static URLClassLoader  autoLoad(String jarPath)throws Exception{

        try {
            File jar = new File(jarPath);
            URL[] urls = new URL[]{jar.toURI().toURL()};
            URLClassLoader loader = new URLClassLoader(urls);
            return loader;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new Exception("load  jar error");
        }

    }



    public static String downloadFromUrl(String group,String artifact, String version) throws Exception {


        String fileName = DIR+artifact + SPLITE + version + FILE_END;
        if (StringUtils.isEmpty(fileName)) {
            logger.error("get jar fileName failed");
            throw new Exception("file name is empty");
        }

        logger.info("{} to download",fileName);

        try {

            File f = new File(fileName);
            if (f.exists()) {
                logger.info("this jar download alreadly");
                return fileName;
            } else {
                String url = getUrl(group, artifact, version);
                logger.info("down jar from {}",url);
                URL httpurl = new URL(url);
                FileUtils.copyURLToFile(httpurl, f);
                if (!f.exists()) {
                    logger.error("download jar failed!");
                    throw new Exception("download jar failed!");
                } else {
                    return fileName;
                }
            }
        } catch (Exception e) {

            logger.error("download jar failed!");
            throw new Exception("download jar failed!");
        }

    }

    public static String getUrl(String group, String artifact, String version) {
        String url = StringUtils.EMPTY;
        if (version.endsWith(SNAPSHOT)) {
            url = URL_HEAD + "snapshots" + "&g=" + group + "&a=" + artifact + "&v=" + version + "&e=jar";
        } else {
            url = URL_HEAD + "releases" + "&g=" + group + "&a=" + artifact + "&v=" + version + "&e=jar";
        }
        return url;
    }


}
