package kr.kernel.teachme.common.jwt;

import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.service.MemberService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class JwtAuthorizationFilterTest {

    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Mock
    private MemberService memberService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    private static MockedStatic<JwtUtils> jwtUtils;

    @BeforeAll
    public static void beforeAll() {
        jwtUtils = mockStatic(JwtUtils.class);
    }

    @BeforeEach
    void setUp() {
        openMocks(this);
        jwtAuthorizationFilter = new JwtAuthorizationFilter(memberService);
    }

    @DisplayName("doFilterInternel 메서드가 쿠기가 없을 때 잘 동작하는지")
    @Test
    void testDoFilterInternelWithNoCookies() throws ServletException, IOException {
        when(request.getCookies()).thenReturn(null);
        jwtAuthorizationFilter.doFilterInternal(request, response, chain);
        verify(chain, times(1)).doFilter(request, response);
    }

    @DisplayName("doFilterInternel 메서드가 액세스 토큰이 있을 때 잘 동작하는지")
    @Test
    void testDofilterInternelWithValidAccessToken() throws ServletException, IOException {
        Cookie[] cookies = {new Cookie(JwtProperties.COOKIE_NAME, "validAccessToken")};
        when(request.getCookies()).thenReturn(cookies);
        when(JwtUtils.validateToken("validAccessToken")).thenReturn(true);
        when(JwtUtils.getUsername("validAccessToken")).thenReturn("user");

        Member mockMember = new Member("user", "password", "ROLE_USER", "User");
        when(memberService.findByUsername("user")).thenReturn(mockMember);

        jwtAuthorizationFilter.doFilterInternal(request, response, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(auth).isNotNull();
        assertThat(auth.getPrincipal()).isEqualTo(mockMember);
        verify(chain, times(1)).doFilter(request, response);
    }

    @DisplayName("doFilterInternel 메서드가 리프레시 토큰이 있을 때 새 액세스 토큰을 잘 발급하는지")
    @Test
    void testDoFilterInternelWithValidRefreshToken() throws ServletException, IOException {
        String validRefreshToken = "validRefreshToken";
        Cookie[] cookies = {new Cookie(JwtProperties.REFRESH_COOKIE_NAME, validRefreshToken)};
        when(request.getCookies()).thenReturn(cookies);
        when(JwtUtils.validateRefreshToken(validRefreshToken)).thenReturn(true);
        when(JwtUtils.getUsername(validRefreshToken)).thenReturn("user");

        Member mockMember = new Member("user", "password", "ROLE_USER", "User");
        when(memberService.findByUsername("user")).thenReturn(mockMember);

        String newAccessToken = "newAccessToken";
        when(JwtUtils.createToken(mockMember)).thenReturn(newAccessToken);

        jwtAuthorizationFilter.doFilterInternal(request, response, chain);

        verify(response).addCookie(argThat(cookie ->
                cookie.getName().equals(JwtProperties.COOKIE_NAME) && cookie.getValue().equals(newAccessToken)
        ));

        verify(chain).doFilter(request, response);
    }

    @AfterEach
    public void afterEach() {
        SecurityContextHolder.clearContext();
    }

    @AfterAll
    public static void afterAll() {
        jwtUtils.close();
    }
}
