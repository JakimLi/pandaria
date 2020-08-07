package com.github.jakimli.pandaria.domain.wait;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Scope("cucumber-glue")
public class Until {

    private static final Logger LOGGER = LoggerFactory.getLogger(Until.class);

    @Autowired
    Wait wait;

    @Around("@annotation(cucumber.api.java.en.Then)")
    public Object until(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!wait.on()) {
            return verify(joinPoint);
        }
        return waiting(joinPoint);
    }

    private Object waiting(ProceedingJoinPoint joinPoint) throws Throwable {
        int count = 0;
        while (count < wait.maxRetry()) {
            try {
                return verify(joinPoint);
            } catch (Throwable throwable) {
                LOGGER.info(throwable.getMessage());
                count++;
                sleep();
                retry(count);
            }
        }
        return verify(joinPoint);
    }

    private Object verify(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        wait.off();
        return proceed;
    }

    private void retry(int count) {
        LOGGER.info("retrying: {}/{}", count, wait.maxRetry());
        wait.retry();
    }

    private void sleep() throws InterruptedException {
        LOGGER.info("sleep: {}ms", wait.millis());
        wait.sleep();
    }
}
