package kr.kernel.teachme.domain.member.service;

import kr.kernel.teachme.domain.member.AlreadyRegisteredMemberException;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

// user, admin 등록, 이름으로 정보 조회
// 로그인 정보에 따라서
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 등록
     *
     * @param username username
     * @param password password
     * @param name name
     * @return 유저 권한을 가지고 있는 유저
     */
    public Member signup(
            String username,
            String password,
            String name
    ) {
        if (memberRepository.findByUsername(username) != null) {
            throw new AlreadyRegisteredMemberException();
        }
        return memberRepository.save(new Member(username, passwordEncoder.encode(password), "ROLE_USER", name));
    }

    /**
     * 관리자 등록
     *
     * @param username username
     * @param password password
     * @param name name
     * @return 관리자 권한을 가지고 있는 유저
     */
    public Member signupAdmin(
            String username,
            String password,
            String name
    ) {
        if (memberRepository.findByUsername(username) != null) {
            throw new AlreadyRegisteredMemberException();
        }
        return memberRepository.save(new Member(username, passwordEncoder.encode(password), "ROLE_ADMIN", name));
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    } //유저 정보 조회

   @Transactional(readOnly = true)
    public Map<String,String> validateHandling(Errors errors){
        Map<String,String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName,error.getDefaultMessage());
        }
        return validatorResult;
    }

}