package kr.kernel.teachme.common.jwt;

import io.jsonwebtoken.*;
import kr.kernel.teachme.domain.member.entity.Member;
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

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private MemberService memberService;

    public JwtAuthorizationFilter(MemberService memberService) {
        this.memberService = memberService;
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
        } catch (Exception ignored) {
        }

        boolean validAccessToken = false;
        if(accessToken != null) {
            try {
                validAccessToken = JwtUtils.validateToken(accessToken);
                if(validAccessToken) {
                    Authentication authentication = getUsernamePasswordAuthenticationToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ignored) {
            }
        }

        if(!validAccessToken && refreshToken != null && JwtUtils.validateRefreshToken(refreshToken)) {
            String userName = getUsernameFromRefreshToken(refreshToken);
            try {
                Member member = memberService.findByUsername(userName);
                String newAccessToken = JwtUtils.createToken(member);
                Authentication newAuth = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                Cookie newAccessTokenCookie = new Cookie(JwtProperties.COOKIE_NAME, newAccessToken);
                newAccessTokenCookie.setPath("/");
                newAccessTokenCookie.setMaxAge(JwtProperties.EXPIRATION_TIME);
                response.addCookie(newAccessTokenCookie);
            } catch(Exception ignored) {
            }
        } else if (!validAccessToken) {
            Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
      chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthenticationToken(String token) {
        String userName = JwtUtils.getUsername(token);
        if (userName != null) {
            Member member = memberService.findByUsername(userName);
            return new UsernamePasswordAuthenticationToken(
                    member,
                    null,
                    member.getAuthorities()
            );
        }
        return null;
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