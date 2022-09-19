package org.simple.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 类加载工具
 */
public class ClassUtil {

    /**
     * 通过类名加载类
     * @param className 类名
     * @return
     */
    public static Class<?> loadClass(String className,boolean initialize){

        Class cls;
        try {
            ClassLoader classLoader = getClassLoader();
            System.out.println("className = " + className);
            cls = Class.forName(className,initialize,classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 通过包名加载包下所有类
     * @param packageName 包名
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage(String packageName){

        Set<Class<?>> classSet = new HashSet<Class<?>>();

        // 类加载器加载包目录下的所有文件
        ClassLoader classLoader = getClassLoader();
        try {
            Enumeration<URL> urlEnumeration = classLoader.getResources(packageName.replace(".", "/"));
            while (urlEnumeration.hasMoreElements()){
                URL url = urlEnumeration.nextElement();
                String protocol = url.getProtocol();

                // 协议是文件
                if ("file".equals(protocol)){
                    String packagePath = url.getPath();
                    addPathClasses(classSet,packageName,packagePath);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return classSet;
    }


    /**
     * 添加目录下所有Class
     * @param classSet
     * @param packageName 包名
     * @param packagePath 包路径
     */
    public static void addPathClasses(Set<Class<?>> classSet,String packageName, String packagePath){
        // 获取目录或者以.class结尾的文件
        File[] files = new File(packagePath).listFiles(file -> file.isDirectory() || (file.getName().endsWith(".class")));

        for (File file : files) {
            String fileName = file.getName();
            // 是文件则添加类
            if (file.isFile()){
                String className = packageName + "." + fileName.substring(0,fileName.indexOf("."));
                addClass(classSet,className);
            }
            // 是目录则
            else {
                String subPackageName = packageName + "." + fileName;
                String subPackagePath = packagePath + "/" + fileName;
                addPathClasses(classSet,subPackageName,subPackagePath);
            }
        }

    }

    public static void addClass(Set<Class<?>> classSet,String className){
        Class<?> clazz = loadClass(className,false);
        if (clazz != null){
            classSet.add(clazz);
        }
    }

    /**
     * 获取当前线程的类加载器
     * @return classLoader
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }


}
