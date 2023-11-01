package kr.kernel.teachme.member.controller;

import kr.kernel.teachme.member.dto.MemberRegisterDto;
import kr.kernel.teachme.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;

    @GetMapping
    public String login(){return "login";}

//    @PostMapping
//    public String login(
//            @ModelAttribute MemberRegisterDto memberRegisterDto
//    ){
//
//        return "home";
//    }
}
