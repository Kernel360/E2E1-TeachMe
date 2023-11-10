package kr.kernel.teachme.domain.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
}
