package com.github.jakimli.pandaria.domain.wait;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.fail;

@Component
@Aspect
@Scope("cucumber-glue")
public class Until {

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
                if (exceeds(count)) {
                    wait.off();
                    throw throwable;
                }

                wait.sleep();
                wait.retry();
                count++;
            }
        }
        wait.off();
        fail("max retry times exceeded");
        return null;
    }

    private boolean exceeds(int count) {
        return count >= wait.maxRetry();
    }
}
