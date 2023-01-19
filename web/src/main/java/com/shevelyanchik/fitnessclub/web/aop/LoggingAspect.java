package com.shevelyanchik.fitnessclub.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        long executionTime = System.currentTimeMillis() - start;
        log.debug(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return joinPoint.proceed();
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repositoryPointcut() {
    }

    @Around("repositoryPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Enter: {}.{}() with argument[s] = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

    @Around("@annotation(CustomCacheable)")
    public Object aroundCachedMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Execution of Cacheable method caught");

        StringBuilder keyBuff = new StringBuilder();
        keyBuff.append(joinPoint.getSignature().getDeclaringTypeName());
        keyBuff.append(".").append(joinPoint.getSignature().getName());
        keyBuff.append("(");
        Arrays.stream(joinPoint.getArgs()).forEach(arg -> keyBuff.append(arg.getClass().getSimpleName()).append("=").append(arg).append(";"));
        keyBuff.append(")");
        String key = keyBuff.toString();

        log.debug("Key = " + key);
        Object result = cache.get(key);
        if (Objects.isNull(result)) {
            log.debug("Result not yet cached. Must be calculated...");
            result = joinPoint.proceed();
            log.info("Storing calculated value '" + result + "' to cache");
            cache.put(key, result);
        } else {
            log.debug("Result '" + result + "' was found in cache");
        }
        return result;
    }
}
