package cn.itweknow.sbaop.aop;

import cn.itweknow.sbaop.annotation.AnnotationResolver;
import cn.itweknow.sbaop.annotation.WebLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebLogAspect {

    @Autowired
    private AnnotationResolver annotationResolver;

    @Pointcut("execution(* cn.itweknow.sbaop.controller..*.*(..))")
    public void webLog() {}

    @Before("@annotation(webLog)")
    public Object around(JoinPoint joinPoint, WebLog webLog) {
        System.out.printf(webLog.operation());
        try {
            System.out.printf(annotationResolver.resolver(joinPoint, webLog.request()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
