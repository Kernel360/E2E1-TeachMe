package kr.kernel.teachme.jwt;

import kr.kernel.teachme.member.entity.Member;
import kr.kernel.teachme.member.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * JWT를 이용한 인증
 * -> jwtAuthenticationFilter에서 만든 쿠키 속 토큰을 가져와서 파싱하여 유저 구하고, 유저의 authentication을 만들어서 SecurityContext에 넣는다.
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private MemberRepository memberRepository;

    public JwtAuthorizationFilter(
            MemberRepository memberRepository
    ) {
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String token = null;
        try {
            // cookie 에서 JWT token을 가져옵니다. (쿠키의 이름이 JwtProperties에 있는 쿠키 이름과 같은 것을 가져옴)
            // 쿠키의 값(토큰)을 가져오고 없으면 null 반환함.
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        } catch (Exception ignored) {
            // 아무 것도 하지 않음
        }
        if (token != null) {
            try {
                // authentication을 만들어서 SecurityContext에 넣는다.
                Authentication authentication = getUsernamePasswordAuthenticationToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 실패하는 경우에는 쿠키를 초기화합니다.
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * JWT 토큰으로 User를 찾아서 UsernamePasswordAuthenticationToken를 만들어서 반환한다.
     * User가 없다면 null
     */
    private Authentication getUsernamePasswordAuthenticationToken(String token) {
        String userName = JwtUtils.getUsername(token);
        if (userName != null) {
            Member member = memberRepository.findByUsername(userName); // 유저를 유저명으로 찾습니다.
            return new UsernamePasswordAuthenticationToken(
                    member, // principal
                    null,
                    member.getAuthorities()
            );
        }
        return null; // 유저가 없으면 NULL
    }
}