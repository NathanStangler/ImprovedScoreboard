package net.improved.improvedscoreboard.common;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionHandler {
    private static final Unsafe UNSAFE;
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setField(Object object, String name, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T createInstance(Class<?> clazz) {
        try {
            return (T) UNSAFE.allocateInstance(clazz);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getClazz(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T invoke(Class<?> clazz, String name, Object... args) {
        try {
            Method method = clazz.getDeclaredMethod(name, Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new));
            method.setAccessible(true);
            T out = (T) method.invoke(null, args);
            method.setAccessible(false);
            return out;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
