package kr.kernel.teachme.domain.member.controller;

import kr.kernel.teachme.domain.member.dto.MemberRegisterDto;
import kr.kernel.teachme.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

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
        return "member/signup";
    }

    @PostMapping
    public String signup(
            @Valid MemberRegisterDto memberDto, Errors errors, Model model
    ) {
        if (errors.hasErrors()) {
            /* 회원가입 실패시 입력 데이터 값을 유지 */
            model.addAttribute("MemberDto", memberDto);
            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "member/signup";
        }
        memberService.signup(memberDto.getUsername(), memberDto.getPassword(), memberDto.getName());
        return "redirect:../login";
    }
}
