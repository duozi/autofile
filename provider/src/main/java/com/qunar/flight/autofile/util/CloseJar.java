package com.qunar.flight.autofile.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

/**
 * Created by zhouxi.zhou on 2016/4/22.
 * 关闭动态加载的jar包
 */
public class CloseJar {
    private final static Logger logger = LoggerFactory.getLogger(CloseJar.class);
    public static void closeJar(URLClassLoader loader) throws Exception{
        Object ucpObj = null;
        Field ucpField = URLClassLoader.class.getDeclaredField("ucp");
        ucpField.setAccessible(true);
        ucpObj = ucpField.get(loader);
        URL[] list = loader.getURLs();
        for(int i=0;i<list.length;i++){
            // 获得ucp内部的jarLoader
            Method m = ucpObj.getClass().getDeclaredMethod("getLoader", int.class);
            m.setAccessible(true);
            Object jarLoader = m.invoke(ucpObj, i);
            String clsName = jarLoader.getClass().getName();
            if(clsName.indexOf("JarLoader")!=-1){
                m = jarLoader.getClass().getDeclaredMethod("ensureOpen");
                m.setAccessible(true);
                m.invoke(jarLoader);
                m = jarLoader.getClass().getDeclaredMethod("getJarFile");
                m.setAccessible(true);
                JarFile jf = (JarFile)m.invoke(jarLoader);
                // 释放jarLoader中的jar文件
                jf.close();
                System.out.println("release jar: "+jf.getName());
            }
        }
    }
}
