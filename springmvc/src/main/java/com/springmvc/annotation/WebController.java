package com.springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @author YanHF ����Controller
 *
 */
@Target({ java.lang.annotation.ElementType.TYPE })//����ע����ͽӿ�
@Retention(RetentionPolicy.RUNTIME)//����ʱ�����õ����ע��
@Documented
public @interface WebController {

}
