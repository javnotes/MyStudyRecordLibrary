package annotationdemo;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * @description: 通过自定义方法参数注解来查看注解信息
 * 定义了两个注解@QueryParam和@DefaultValue，都用于修饰方法参数，方法hello使用了这两个注解
 * @author: luf
 * @create: 2021-12-07 20:39
 **/
public class MethodAnnotations {

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    static @interface QueryParam {
        String value();
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    static @interface DefaultValue {
        String value() default "";
    }

    public void hello(@QueryParam("action") String action, @QueryParam("sort") @DefaultValue("asc") String sort) {

    }

    // 实际所有的注解类型都是扩展的接口 Annotation。
    public static void main(String[] args) throws Exception {
        // 获取Class对象
        Class<?> cls = MethodAnnotations.class;
        // 返回本类/父类中指定名称和参数类型的public方法
        Method method = cls.getMethod("hello", new Class[]{String.class, String.class});

        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            System.out.println("annotations for paramter " + (i + 1));
            Annotation[] anntArr = annotations[i];
            for (Annotation annt : anntArr) {
                if (annt instanceof QueryParam) {
                    QueryParam queryParam = (QueryParam) annt;
                    // Annotation.annotationType() 返回真正的注解类型
                    System.out.println(queryParam.annotationType().getSimpleName() + ":" + queryParam.value());
                } else if (annt instanceof DefaultValue) {
                    DefaultValue defaultValue = (DefaultValue) annt;
                    System.out.println(defaultValue.annotationType().getSimpleName() + ":" + defaultValue.value());
                }
            }

        }


    }
}
