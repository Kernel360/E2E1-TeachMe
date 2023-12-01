package kr.kernel.teachme.domain.member.service;

import kr.kernel.teachme.common.exception.AlreadyRegisteredMemberException;
import kr.kernel.teachme.common.exception.MemberNotFoundException;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    public MemberService memberService;

    @Autowired
    public MemberRepository memberRepository;

    @Test
    @DisplayName("입력받은 username,password,name으로 signup을 수행한 후, 필드 값들이 잘 입력되었는지 확인한다.")
    void signupTest(){
        //given
        String username = "user1";
        String password = "user12345678*";
        String name = "홍길동1";
        //then
        Member member = memberService.signup(username,password,name);
        //when
        assertThat(member.getUsername()).isEqualTo("user1");
        assertThat(member.getPassword()).startsWith("{bcrypt}");
        assertThat(member.getName()).isEqualTo("홍길동1");
        assertThat(member.getAuthorities()).hasSize(1);
        assertThat(member.getAuthorities().stream().findFirst().get().getAuthority()).isEqualTo("ROLE_USER");
        assertThat(member.isAdmin()).isFalse();
    }

    @Test
    @DisplayName("이미 가입된 username으로 회원가입을 시도할 경우 AlreadyRegisteredMemberException을 throw 한다.")
    void signupAlreadyResisteredExceptionTest() {
        //given
        String username = "user1";
        String password = "user12345678*";
        String name = "홍길동1";
        memberService.signup(username,password,name); //한 번 가입

        //when
        assertThrows(AlreadyRegisteredMemberException.class, ()-> memberService.signup(username,password,name));
    }

    @Test
    @DisplayName("username으로 repository에 있는 username을 조회했을 때 username에 맞는 Member객체를 반환한다.")
    void findByUsernameTest() {
        //given
        memberRepository.save(new Member("user123", "password", "ROLE_USER","홍길동"));
        //when
        Member member = memberService.findByUsername("user123");
        //then
        then(member.getId()).isNotNull();
    }

    @Test
    @DisplayName("username으로 repository에 있는 username을 조회했을 때 해당하는 username이 없으면 MemberNotFoundException을 throw 한다.")
    void findByUsername_isNotExist(){
        //given
        String username = "abcd123";

        //then
        assertThrows(MemberNotFoundException.class, () -> memberService.findByUsername(username));
    }
}
