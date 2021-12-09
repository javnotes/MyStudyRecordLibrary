package annotationdemo.myannotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 定义日期类型的输出格式
 *
 * @author luf
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Format {
    String pattern() default "yyyy-MM-dd HH:mm:ss";

    String timezone() default "GMT+8";
}
