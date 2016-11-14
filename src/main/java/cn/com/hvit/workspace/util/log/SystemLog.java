package cn.com.hvit.workspace.util.log;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2016/11/14.
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String module() default "";
    String methods() default "";
}
