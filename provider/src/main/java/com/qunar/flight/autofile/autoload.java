package com.qunar.flight.autofile;


import com.qunar.flight.autofile.util.CloseJar;
import com.qunar.flight.autofile.util.downloadJar;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by zhouxi.zhou on 2016/4/22.
 */
public class AutoLoad {
    public static void  autoLoad(String interfaceName , String jarPath)throws Exception{
        try {
            File jar = new File(jarPath);
            URL[] urls = new URL[]{jar.toURI().toURL()};
            URLClassLoader loader = new URLClassLoader(urls);

            Class<?> c = loader.loadClass(interfaceName);
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(method.getName());
            }
            CloseJar.closeJar(loader);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            String jar_path= downloadJar.downloadFromUrl("com.qunar.flight.tts", "officemanage.api", "1.1.11");
            autoLoad("com.qunar.flight.tts.officemanageapi.api.IIBEWhiteListService",jar_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
