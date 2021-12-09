package annotationdemo.myannotation;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @description: 注解的应用：定制序列化，对输出格式进行定制化
 * 《Java 编程的逻辑》
 * @author: luf
 * @create: 2021-12-09 19:28
 **/

public class SimpleFormatter {
    private static Object formatDate(Field f, Object value) {
        // 如果存在此类注释，则返回此元素对指定类型的注释，否则返回 null。
        Format format = f.getAnnotation(Format.class);
        if (format != null) {
            // 构造指定格式的日期
            SimpleDateFormat sdf = new SimpleDateFormat(format.pattern());
            sdf.setTimeZone(TimeZone.getTimeZone(format.timezone()));
            return sdf.format(value);
        }
        // 没有得到注解，就原样返回
        return value;
    }

    public static String format(Object obj) {
        try {
            // 获取其Class 对象
            Class<?> cls = obj.getClass();
            StringBuilder sb = new StringBuilder();
            for (Field f : cls.getDeclaredFields()) {
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                // 尝试获取修饰字段的注解中的value
                Label label = f.getAnnotation(Label.class);
                String name = label != null ? label.value() : f.getName();

                // 获取实例中字段的值value
                Object value = f.get(obj);
                if (value != null && f.getType() == Date.class) {
                    value = formatDate(f, value);
                }
                sb.append(name + "：" + value + "\n");
            }
            return sb.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

