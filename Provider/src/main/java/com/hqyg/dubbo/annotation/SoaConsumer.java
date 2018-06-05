package com.hqyg.dubbo.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface SoaConsumer {
    String value() default "1.0.0";
    
    String group() default "";
}
