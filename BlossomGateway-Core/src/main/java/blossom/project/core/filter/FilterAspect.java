package blossom.project.core.filter;

import java.lang.annotation.*;

/**
 * @author: linqi
 * FilterAspect注解
 * 提供切面增强功能
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface FilterAspect {
    /**
     * 过滤器ID
     * @return
     */
    String id();

    /**
     * 过滤器名称
     * @return
     */
    String name() default "";

    /**
     * 排序
     * @return
     */
    int order() default 0;

}
