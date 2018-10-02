package com.github.jakimli.pandaria.domain.wait;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.fail;

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
            return joinPoint.proceed();
        }

        int count = 0;
        while (count <= wait.maxRetry()) {
            try {
                Object proceed = joinPoint.proceed();
                wait.off();
                return proceed;
            } catch (Throwable throwable) {
                LOGGER.info(throwable.getMessage());

                if (exceeds(count)) {
                    wait.off();
                    LOGGER.info("max retry times exceeded: {}", wait.maxRetry());
                    throw throwable;
                }

                count++;

                LOGGER.info("sleep: {}ms", wait.millis());
                wait.sleep();

                LOGGER.info("retrying: {}/{}", count, wait.maxRetry());
                wait.retry();
            }
        }
        wait.off();

        fail("max retry times exceeded: " + wait.maxRetry());
        return null;
    }

    private boolean exceeds(int count) {
        return count >= wait.maxRetry();
    }
}
