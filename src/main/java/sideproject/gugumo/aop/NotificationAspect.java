package sideproject.gugumo.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import sideproject.gugumo.service.NotificationService;

@Aspect
@Component
@EnableAsync
@RequiredArgsConstructor
public class NotificationAspect {

    private final NotificationService notificationService;

    @Pointcut("@annotation(sideproject.gugumo.aop.annotation.NeedNotify)")
    public void annotationPointcut() {
    }

    //TODO: 미완성
    //리턴 값을 적절히 가공하여 알림을 보내는 메서드
    @Async
    @AfterReturning(pointcut = "annotationPointcut()", returning = "result")
    public void checkValue(JoinPoint joinPoint, Object result) throws Throwable {

    }
}
