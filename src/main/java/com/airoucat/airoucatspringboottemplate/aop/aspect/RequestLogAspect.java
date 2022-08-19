package com.airoucat.airoucatspringboottemplate.aop.aspect;


import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
@Aspect
public class RequestLogAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    @Pointcut("@annotation(com.qjyy.swjzgxweb.aop.annotation.RequestLog)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //打印请求的内容
        long startTime = System.currentTimeMillis();
//        logger.info("RequestUrl: {}({}) IP: {}" , request.getRequestURL().toString(),request.getMethod(),request.getRemoteAddr());
        logger.info("RequestAPI: {}({}) IP: {}" , request.getRequestURI(),request.getMethod(),request.getRemoteAddr());
        logger.info("Method: {}" , joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("Params: {}" , Arrays.toString(joinPoint.getArgs()));
        // 系统信息
        logger.info("System: {},Browser: {},Version: {}", userAgent.getOperatingSystem().toString(),userAgent.getBrowser().toString(),userAgent.getBrowserVersion());
        // joinPoint.proceed()：当我们执行完切面代码之后，还有继续处理业务相关的代码。proceed()方法会继续执行业务代码，并且其返回值，就是业务处理完成之后的返回值。
        Object ret = joinPoint.proceed();
        logger.info("Request end at: {}  time: {} ms" , LocalDateTime.now().format(RequestLogAspect.formatter),(System.currentTimeMillis() - startTime));
        // 处理完请求，返回内容
        logger.info("Request return: {}\n" , ret);
        return ret;
    }
}

