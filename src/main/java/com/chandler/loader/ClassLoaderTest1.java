package com.chandler.loader;

import java.lang.reflect.Method;

public class ClassLoaderTest1 {

    public static void main(String[] args) {
        String classDataRootPath = "C:\\Users\\86188\\Documents\\study\\spring-study\\src\\main\\java";
        FileSystemClassLoader fscl1 = new FileSystemClassLoader(classDataRootPath);
        FileSystemClassLoader fscl2 = new FileSystemClassLoader(classDataRootPath);
        String className = "com.chandler.loader.Sample";
        try {
            Class<?> class1 = fscl1.findClass(className);
            Object obj1 = class1.newInstance();
            Class<?> class2 = fscl2.findClass(className);
            Object obj2 = class2.newInstance();
            Method setSampleMethod = class1.getMethod("setSample", java.lang.Object.class);
            setSampleMethod.invoke(obj1, obj2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
