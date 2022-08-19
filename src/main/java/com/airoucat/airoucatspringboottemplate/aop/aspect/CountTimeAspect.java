package com.airoucat.airoucatspringboottemplate.aop.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CountTimeAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Pointcut("@annotation(com.qjyy.swjzgxweb.aop.annotation.CountTime)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object countTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        Signature sig = joinPoint.getSignature();
        MethodSignature ms = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        ms = (MethodSignature) sig;
//        Object target = joinPoint.getTarget();
//        Method currentMethod = target.getClass().getMethod(ms.getName(), ms.getParameterTypes());
//        logger.info("method"+currentMethod.getName()+"spend time: "+(endTime-startTime)+"ms");
        logger.info("method ["+ms.getDeclaringTypeName()+"."+ms.getName()+"] spend time: "+(endTime-startTime)+"ms");
        return obj;
    }
}
