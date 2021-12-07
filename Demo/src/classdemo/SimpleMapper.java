package classdemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @description:
 * @author: luf
 * @create: 2021-12-07 19:16
 **/
public class SimpleMapper {

    // 将对象obj转换为字符串
    public static String toString(Object obj) {
        try {
            Class<?> cls = obj.getClass();
            StringBuilder sb = new StringBuilder();
            // 添加类名
            sb.append(cls.getName() + "\n");
            for (Field f : cls.getDeclaredFields()) {
                // 保证属性可访问
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                sb.append(f.getName() + "=" + f.get(obj).toString() + "\n");
            }
            return sb.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // 将字符串转换为对象
    public static Object fromString(String str) {
        try {
            // 根据构成时的分隔符号，进行分隔
            String[] lines = str.split("\n");
            // lines[0] 为类名
            if (lines.length < 1) {
                throw new IllegalArgumentException(str);
            }

            Class<?> cls = Class.forName(lines[0]);
            Object obj = cls.newInstance();
            // lines[1]...为类的属性
            if (lines.length > 1) {
                for (int i = 1; i < lines.length; i++) {
                    // 把key=value分开
                    String[] fv = lines[i].split("=");
                    if (fv.length != 2) {
                        throw new IllegalArgumentException(lines[i]);
                    }
                    // 返回本类中声明的指定的名称的字段
                    Field f = cls.getDeclaredField(fv[0]);
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    setFieldValue(f, obj, fv[1]);
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void setFieldValue(Field f, Object obj, String value) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        // 返回字段类型
        Class<?> type = f.getType();
        if (type == int.class) {
            f.setInt(obj, Integer.parseInt(value));
        } else if (type == byte.class) {
            f.setByte(obj, Byte.parseByte(value));
        } else if (type == short.class) {
            f.setShort(obj, Short.parseShort(value));
        } else if (type == long.class) {
            f.setLong(obj, Long.parseLong(value));
        } else if (type == float.class) {
            f.setFloat(obj, Float.parseFloat(value));
        } else if (type == double.class) {
            f.setDouble(obj, Double.parseDouble(value));
        } else if (type == char.class) {
            f.setChar(obj, value.charAt(0));
        } else if (type == boolean.class) {
            f.setBoolean(obj, Boolean.parseBoolean(value));
        } else if (type == String.class) {
            f.set(obj, value);
        } else {
            Constructor<?> constructor = type.getConstructor(new Class[]{String.class});
            f.set(obj, constructor.newInstance(value));
        }
    }
}
