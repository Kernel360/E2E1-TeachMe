package kr.kernel.teachme.common.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {

    static final Logger log = LoggerFactory.getLogger(LogExecutionTime.class);

    String value() default "";

    @Component
    @org.aspectj.lang.annotation.Aspect
    class Aspect {
        @Around("@annotation(logExecutionTime)")
        public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            String methodName = joinPoint.getSignature().toShortString();
            String description = logExecutionTime.value();
            if (description.isEmpty()) {
                log.info("{} 실행 시간: {}ms", methodName, executionTime);
            } else {
                log.info("{} - {} 실행 시간: {}ms", description, methodName, executionTime);
            }

            return result;
        }
    }
}
