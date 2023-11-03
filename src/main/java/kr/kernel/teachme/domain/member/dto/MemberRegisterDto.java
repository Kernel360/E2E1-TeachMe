package kr.kernel.teachme.domain.member.dto;

import lombok.*;

/**
 * 유저 회원가입용 Dto
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberRegisterDto {

    private String username;
    private String password;
}
