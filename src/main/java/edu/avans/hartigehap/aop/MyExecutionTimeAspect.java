package edu.avans.hartigehap.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import edu.avans.hartigehap.service.impl.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class MyExecutionTimeAspect {

    // the pointcut expression
    @Pointcut("@annotation(edu.avans.hartigehap.aop.MyExecutionTime) && execution(* edu.avans.hartigehap..*(..))")

    // the pointcut signature
    public void myExecutionTimeAnnotation() {
    }

    // MyExecutionTime annotation
    @Around("myExecutionTimeAnnotation()")
    public Object myExecutionTimeAdvice(
            ProceedingJoinPoint joinPoint ) throws Throwable  {
        long startMillis = System.currentTimeMillis();
        log.debug("(AOP-myExecTime) Starting timing method " + joinPoint.getSignature());
        Object retVal = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startMillis;
        log.debug("(AOP-myExecTime) Call to " + joinPoint.getSignature() + " took " + duration + " ms");
        return retVal;

    }
}
