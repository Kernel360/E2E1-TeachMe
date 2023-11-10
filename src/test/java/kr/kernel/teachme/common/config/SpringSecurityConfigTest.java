package kr.kernel.teachme.common.config;

import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SpringSecurityConfigTest {

    @InjectMocks
    private SpringSecurityConfig springSecurityConfig;

    @Mock
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @DisplayName("userDetailsService 메서드가 유효한 UserDetails를 반환하는지")
    @Test
    void testUserDetailService() {
        String username = "testUser";
        String password = "testPassword";
        String authority = "ROLE_USER";
        String name = "Test User";
        Member mockMember = new Member(username, password, authority, name);

        when(memberService.findByUsername(username)).thenReturn(mockMember);

        UserDetailsService userDetailsService = springSecurityConfig.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @DisplayName("userDetailService 메서드가 유효하지 않은 사용자 이름에 UsernameNotFoundException을 반환하는지")
    @Test
    void testUserDetailsServiceWithInvalidUsername() {
        String invalidUsername = "nonExistingUser";

        when(memberService.findByUsername(invalidUsername)).thenReturn(null);

        UserDetailsService userDetailsService = springSecurityConfig.userDetailsService();
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(invalidUsername));
    }
}
