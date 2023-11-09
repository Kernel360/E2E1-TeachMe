package kr.kernel.teachme.domain.member.controller;

import kr.kernel.teachme.common.exception.AlreadyRegisteredMemberException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SignUpExceptionHandler {

    @ExceptionHandler(AlreadyRegisteredMemberException.class)
    public String handleMemberNotFoundException(Exception e, Model model) {
        model.addAttribute("errorMessage", "이미 존재하는 아이디 입니다. 다시 시도해주세요.");
        return "/member/signup";
    }
}
