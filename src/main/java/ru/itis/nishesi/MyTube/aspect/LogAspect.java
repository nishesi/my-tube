package ru.itis.nishesi.MyTube.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.itis.nishesi.MyTube.exceptions.ContentNotFoundException;
import ru.itis.nishesi.MyTube.exceptions.ExistsException;

@Aspect
@Log4j2
@Component
public class LogAspect {

    @Pointcut("execution(public * ru.itis.nishesi.MyTube.services.implementations.*.*(..))")
    public void anyServiceMethod() {
    }

    @AfterThrowing(pointcut = "anyServiceMethod()", throwing = "ex")
    public void logException(RuntimeException ex) throws RuntimeException {
        if (ContentNotFoundException.class.isAssignableFrom(ex.getClass())) {
            if (log.isDebugEnabled())
                log.debug("Content not found in: " + ex.getStackTrace()[0].toString());

        } else if (ExistsException.class.isAssignableFrom(ex.getClass())) {
            if (log.isDebugEnabled())
                log.debug("Expected validation exception: " + ex + " from: " + ex.getStackTrace()[0].toString());

        } else {
            if (log.isErrorEnabled())
                log.error("Unexpected exception: " + ex + " from: " + ex.getStackTrace()[0].toString());
        }
    }

    @Before("anyServiceMethod()")
    public void before(JoinPoint joinPoint) {
        if (log.isDebugEnabled())
            log.debug("Service method invoked: " + joinPoint.toShortString());
    }
}
