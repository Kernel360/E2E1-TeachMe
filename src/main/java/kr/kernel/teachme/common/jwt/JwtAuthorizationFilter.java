package kr.kernel.teachme.common.jwt;

import io.jsonwebtoken.*;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.repository.MemberRepository;
import kr.kernel.teachme.domain.member.service.MemberService;
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
import java.security.Key;
import java.util.function.Function;

/**
 * JWT를 이용한 인증
 * -> jwtAuthenticationFilter에서 만든 쿠키 속 토큰을 가져와서 파싱하여 유저 구하고, 유저의 authentication을 만들어서 SecurityContext에 넣는다.
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private MemberRepository memberRepository;
    private MemberService memberService;

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
        String accessToken = null;
        String refreshToken = null;

        try {
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for(Cookie cookie : cookies) {
                    if(cookie.getName().equals(JwtProperties.COOKIE_NAME)) {
                        accessToken = cookie.getValue();
                    } else if (cookie.getName().equals(JwtProperties.REFRESH_COOKIE_NAME)) {
                        refreshToken = cookie.getValue();
                    }
                }
            }
//            token = Arrays.stream(request.getCookies())
//                    .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
//                    .map(Cookie::getValue)
//                    .orElse(null);
        } catch (Exception ignored) {
            // 아무 것도 하지 않음
        }

        try {
            if (accessToken != null && JwtUtils.validateToken(accessToken)) {
                Authentication authentication = getUsernamePasswordAuthenticationToken(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            if (refreshToken != null && JwtUtils.validateRefreshToken(refreshToken)) {
                String userName = getUsernameFromRefreshToken(refreshToken);
                Member member = memberService.findByUsername(userName);
                String newAccessToken = JwtUtils.createToken(member);

                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        member,
                        null,
                        member.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                Cookie newAccessTokenCookie = new Cookie(JwtProperties.COOKIE_NAME, newAccessToken);
                newAccessTokenCookie.setPath("/");
                newAccessTokenCookie.setMaxAge(JwtProperties.EXPIRATION_TIME);
                response.addCookie(newAccessTokenCookie);
            } else {
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                return;
            }
        }

//        if (token != null) {
//            try {
//                // authentication을 만들어서 SecurityContext에 넣는다.
//                Authentication authentication = getUsernamePasswordAuthenticationToken(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (Exception e) {
//                // 실패하는 경우에는 쿠키를 초기화합니다.
//                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            }
//        }
        chain.doFilter(request, response);
    }

    /**
     * JWT 토큰으로 User를 찾아서 UsernamePasswordAuthenticationToken를 만들어서 반환한다.
     * User가 없다면 null
     */
    private Authentication getUsernamePasswordAuthenticationToken(String token) {
        String userName = JwtUtils.getUsername(token);
        if (userName != null) {
            Member member = memberService.findByUsername(userName); // 유저를 유저명으로 찾습니다.
            return new UsernamePasswordAuthenticationToken(
                    member, // principal
                    null,
                    member.getAuthorities()
            );
        }
        return null; // 유저가 없으면 NULL
    }

    private String getUsernameFromRefreshToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKeyResolver(new SigningKeyResolverAdapter() {
                        @Override
                        public Key resolveSigningKey(JwsHeader header, Claims claims) {
                            String kid = header.getKeyId();
                            return JwtKey.getKey(kid);
                        }
                    })
                    .build()
                    .parseClaimsJws(token);

            return claimsResolver.apply(claims.getBody());
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}