package annotationdemo.myannotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 定制输出字段的名称
 *
 * @author luf
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Label {
    String value() default "";
}
