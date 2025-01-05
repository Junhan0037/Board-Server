package com.boardserver.aop;

import com.boardserver.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * AOP(Aspect-Oriented Programming)를 활용하여 로그인 상태를 확인하는 클래스
 * 로그인 여부와 사용자 타입(ADMIN, USER)을 확인한 후, 해당 메서드에 필요한 정보를 전달
 */
@Component
@Aspect
@Order // AOP 의 우선순위를 지정. 여러 Aspect 가 존재할 경우 처리 순서를 결정
@Log4j2
public class LoginCheckAspect {

    /**
     * @LoginCheck 어노테이션이 붙은 메서드에 대해 로그인 상태를 확인하는 기능을 제공
     * @param proceedingJoinPoint 현재 실행 중인 메서드에 대한 정보를 제공하는 객체
     * @param loginCheck @LoginCheck 어노테이션에서 제공되는 정보를 전달받음
     */
    @Around("@annotation(com.boardserver.aop.LoginCheck) && @annotation(loginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {
        // 현재 HTTP 요청의 세션 정보
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        
        String id = null; // 로그인된 사용자 ID를 저장할 변수
        int idIndex = 0;
        String userType = loginCheck.type().toString();

        // 사용자 타입에 따라 세션에서 적절한 로그인 ID를 추출
        switch (userType) {
            case "ADMIN":
                id = SessionUtil.getLoginAdminId(session);
                break;
            case "USER":
                id = SessionUtil.getLoginMemberId(session);
                break;
        }

        // 로그인 ID가 없는 경우, UNAUTHORIZED 상태를 반환하며 예외를 발생
        if (id == null) {
            log.info(proceedingJoinPoint.toString() + "accountName: " + id);
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 id값을 확인해주세요.") {};
        }

        // 메서드의 인자를 수정하기 위한 배열을 생성
        Object[] modifiedArgs = proceedingJoinPoint.getArgs();

        // 메서드에 전달된 인자가 존재하면, 지정된 위치에 사용자 ID를 추가
        if (proceedingJoinPoint.getArgs() != null) {
            modifiedArgs[idIndex] = id;
        }

        // 원래의 메서드를 수정된 인자 배열과 함께 실행
        return proceedingJoinPoint.proceed(modifiedArgs);
    }
    
}
