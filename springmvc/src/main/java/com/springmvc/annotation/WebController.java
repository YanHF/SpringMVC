package com.springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @author YanHF 声明Controller
 *
 */
@Target({ java.lang.annotation.ElementType.TYPE })//可以注解类和接口
@Retention(RetentionPolicy.RUNTIME)//运行时可以拿到这个注解
@Documented
public @interface WebController {

}
