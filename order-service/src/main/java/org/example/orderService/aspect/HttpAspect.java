package org.example.orderService.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.orderService.http.HttpResult;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class HttpAspect {

    @Pointcut("execution(public * com.example.orderService.controller.*.*(..))")//切面所切的位置
    public void log(){
    }

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Before("log()")//请求之前
    public void before(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("http req：, url={{}}, method={{}}, class_method={{}}, args={{}}", request.getRequestURL(), request.getMethod(),
                 joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(pointcut = "log()", returning = "result")
    public HttpResult doAfterReturning(HttpResult res) {
        log.info("http res: {{}} in {{}} ms", res.getMsg(),(System.currentTimeMillis() - startTime.get()));
        return res;
    }
}