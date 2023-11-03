package kr.kernel.teachme.member.controller;

import kr.kernel.teachme.member.dto.MemberRegisterDto;
import kr.kernel.teachme.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원가입 Controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/member/signup")
public class SignUpController {

    private final MemberService memberService;

    /**
     * @return 회원가입 페이지 리소스
     */
    @GetMapping
    public String signup() {
        return "/member/signup";
    }

    @PostMapping
    public String signup(
            @ModelAttribute MemberRegisterDto userDto
    ) {
        memberService.signup(userDto.getUsername(), userDto.getPassword(), userDto.getName());
        return "redirect:../login";
    }
}
