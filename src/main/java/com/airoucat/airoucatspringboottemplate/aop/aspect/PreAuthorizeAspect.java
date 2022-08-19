package com.airoucat.airoucatspringboottemplate.aop.aspect;

import com.qjyy.swjzgxweb.aop.annotation.PreAuthorize;
import com.qjyy.swjzgxweb.enums.StatusCodeEnum;
import com.qjyy.swjzgxweb.utils.HttpResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class PreAuthorizeAspect {
    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;

    @Pointcut("@annotation(com.qjyy.swjzgxweb.aop.annotation.PreAuthorize)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取注解参数值
        PreAuthorize preAuthorize = methodSignature.getMethod().getAnnotation(PreAuthorize.class);
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        Expression expression = spelExpressionParser.parseExpression(preAuthorize.value());
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(defaultListableBeanFactory));
        Boolean result = expression.getValue(context,Boolean.class);
        if (result==null||!result){
            return HttpResult.error(StatusCodeEnum.SC_UNAUTHORIZED);
        }
        else return joinPoint.proceed();
    }
}
