package kr.kernel.teachme.common.jwt;

import io.jsonwebtoken.*;
import kr.kernel.teachme.domain.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtUtils {
    public static String getUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKeyResolver(SigningKeyResolver.instance)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.debug("jwt 토큰 만료");
            return null;
        } catch (Exception e) {
            log.error("jwt 토큰 처리중 Error 발생!: " + e.getMessage());
            return null;
        }
    }

    public static String createToken(Member member) { //유저 가져와서 유저에 맞는 토큰을 생성해줌.
        Claims claims = Jwts.claims().setSubject(member.getUsername()); // subject
        Date now = new Date(); // 현재 시간
        Pair<String, Key> key = JwtKey.getRandomKey();
        // JWT Token 생성
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보 -> 지금 발행되니까 now
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME)) // 토큰 만료 시간 설정 -> 코드 상에서는 10분
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid
                .signWith(key.getSecond()) // signature
                .compact(); //compact 통해 토큰 생성
    }

    public static boolean validateToken(String token) {
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

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String createRefreshToken(Member member) {
        Date now = new Date();
        Pair<String, Key> key = JwtKey.getRandomKey();

        long refreshTokenExpirationTime = 604800000;

        return Jwts.builder()
                .setSubject(member.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpirationTime))
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst())
                .signWith(key.getSecond(), SignatureAlgorithm.HS512)
                .compact();

    }

    public static boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKeyResolver(SigningKeyResolver.instance)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
