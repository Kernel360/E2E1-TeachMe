package kr.kernel.teachme.common.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyRegisteredMemberException.class)
    public String handleAlreadyRegisteredMemberException(Exception e, Model model) {
        model.addAttribute("errorMessage", "이미 존재하는 아이디 입니다. 다시 시도해주세요.");
        return "member/signup";
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public String handleInternalAuthenticationServiceException(Exception e, Model model) {
        model.addAttribute("errorMessage", "로그인 정보를 찾을 수 없습니다. 다시 시도해주세요.");
        return "member/login";
    }
}
