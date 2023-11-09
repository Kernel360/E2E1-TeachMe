package kr.kernel.teachme.common.jwt;

import com.mysema.commons.lang.Pair;
import io.jsonwebtoken.*;
import kr.kernel.teachme.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JwtUtilsTest {

    private static final String USERNAME = "user";
    private static final String TOKEN = "token";
    private static final String REFRESH_TOKEN = "refreshToken";
    private Member member;
    private Pair<String, Key> keyPair;
    private Key key;

    @BeforeEach
    void setUp() {
        member = new Member(USERNAME, "password", "ROLE_USER", "User");
        key = mock(Key.class);
        keyPair = Pair.of("kid", key);
    }

    @DisplayName("getUsername 메서드가 토큰에서 사용자 이름을 제대로 추출하는지")
    @Test
    void testGetUsername() {
        try (MockedStatic<Jwts> jwtsMockedStatic = mockStatic(Jwts.class)) {
            JwtParserBuilder parserBuilder = mock(JwtParserBuilder.class);
            JwtParser jwtParser = mock(JwtParser.class);
            Jws<Claims> jwsClaims = mock(Jws.class);
            Claims claims = mock(Claims.class);

            when(claims.getSubject()).thenReturn(USERNAME);
            when(jwsClaims.getBody()).thenReturn(claims);
            when(jwtParser.parseClaimsJws(TOKEN)).thenReturn(jwsClaims);
            when(parserBuilder.build()).thenReturn(jwtParser);
            when(Jwts.parserBuilder()).thenReturn(parserBuilder);

            String username = JwtUtils.getUsername(TOKEN);
            assertEquals(USERNAME, username);
        }
    }
}
