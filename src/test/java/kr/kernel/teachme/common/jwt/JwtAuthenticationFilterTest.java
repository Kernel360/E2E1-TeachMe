package kr.kernel.teachme.common.jwt;

import kr.kernel.teachme.domain.member.entity.Member;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private Authentication authResult;

    private static MockedStatic<JwtUtils> jwtUtils;

    @BeforeAll
    public static void beforeAll() {
        jwtUtils = mockStatic(JwtUtils.class);
    }

    @BeforeEach
    public void setUp() {
        openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
    }


    @DisplayName("attemptAuthentication 메서드가 유효한 인증일 시도하는지")
    @Test
    void testAttemptAuthentication() {
        when(request.getParameter("username")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("password");

        when(authenticationManager.authenticate(any())).thenReturn(authResult);

        Authentication authentication = jwtAuthenticationFilter.attemptAuthentication(request, response);

        verify(authenticationManager).authenticate(any());
        assertNotNull(authentication);
    }

    @DisplayName("successfulAuthentication 메서드가 성공적으로 토큰을 쿠키에 넣고 리다이렉트 하는지")
    @Test
    void testSuccessfulAuthentication() throws IOException {
        Member mockMember = new Member("testUser", "testPassword", "ROLE_USER", "Test User");

        when(authResult.getPrincipal()).thenReturn(mockMember);

        when(JwtUtils.createToken(mockMember)).thenReturn("mockToken");
        when(JwtUtils.createRefreshToken(mockMember)).thenReturn("mockRefreshToken");

        doNothing().when(response).addCookie(any());
        doNothing().when(response).sendRedirect(anyString());

        jwtAuthenticationFilter.successfulAuthentication(request, response, chain, authResult);

        verify(response, atLeastOnce()).addCookie(any());
        verify(response).sendRedirect("/");
    }

    @DisplayName("unsuccessfulAuthentication 메서드가 인증에 실패했을 때 적절한 리다이렉트를 실행하는지")
    @Test
    void testUnsuccessfulAuthentication() throws IOException {
        doNothing().when(response).sendRedirect("/login");

        jwtAuthenticationFilter.unsuccessfulAuthentication(request, response, mock(AuthenticationException.class));

        verify(response).sendRedirect("/login");
    }

    @AfterAll
    public static void afterAll() {
        jwtUtils.close();
    }
}
