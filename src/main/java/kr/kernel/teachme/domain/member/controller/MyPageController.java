package kr.kernel.teachme.domain.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MyPageController {

    @ApiOperation(value="마이페이지", notes="개인정보를 위한 페이지 출력")
    @GetMapping("/me")
    public String showMyPage(){
        return "member/mypage";
    }
}
