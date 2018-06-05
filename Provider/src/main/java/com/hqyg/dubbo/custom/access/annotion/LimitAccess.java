package com.hqyg.dubbo.custom.access.annotion;

import java.lang.annotation.*;

@Target(ElementType.METHOD) //字段注解
@Retention(RetentionPolicy.RUNTIME) //在运行期保留注解信息
@Documented     //在生成javac时显示该注解的信息
@Inherited      //标明MyAnnotation1注解可以被使用它的子类继承
public @interface LimitAccess {
    //默认方法并发访问次数，最多同时能有多少个线程能访问
    int limit() default 2;

}
