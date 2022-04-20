package com.yrent.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author dengbp
 * @ClassName reflectUtil
 * @Description TODO
 * @date 12/15/21 2:50 PM
 */
@Slf4j
public class ReflectUtil {


    public static Field[] getAllField(Class classz) throws ClassNotFoundException {
        Class  columnClass = Class.forName(classz.getName());
        return columnClass.getDeclaredFields();
    }

    public static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void invokeSetMethod(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);
        try {
            method.invoke(o, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isTypeOf(Class clazz,String filterMethod,Object target){
        Method method;
        Method[] methods = clazz.getDeclaredMethods();
        try {

            for (int i = 0; i < methods.length; i++) {

                method = methods[i];
                if (method.getName().indexOf(filterMethod)> -1){
                    Type[] types = method.getGenericParameterTypes();
                    for (Type type : types) {
                        Class<?> c = Class.forName(type.getTypeName());
                        //通过桥接方法过滤带范型参数的父类
                        if (c.isInstance(target)  && !method.isBridge()) {
                            log.info("class type check is true...");
                            log.info("class:" + clazz.getName() + ",method:" + method.getName()+",type:"+type+",target type:{}",target.getClass().getName());
                            return true;
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
