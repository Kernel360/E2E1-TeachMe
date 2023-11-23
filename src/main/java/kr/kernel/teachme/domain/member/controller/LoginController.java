package kr.kernel.teachme.domain.member.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.common.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    @ApiOperation(value="로그인 페이지", notes="로그인을 위한 페이지 이동")
    @GetMapping
    public String login(){
        return "member/login";
    }

    @ExceptionHandler({MemberNotFoundException.class})
    public String loginWithError(Model model, Exception e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "member/login";
    }
}
