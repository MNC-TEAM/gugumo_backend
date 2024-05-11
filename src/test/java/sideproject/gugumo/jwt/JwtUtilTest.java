package sideproject.gugumo.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import sideproject.gugumo.domain.entity.MemberRole;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    Long expiredMs = 86400000L;

    @Test
    @DisplayName("jwt token 생성")
    public void createJwtToken() {
        //given
        String token = jwtUtil.createJwt("username", "ROLE_USER", expiredMs);

        //when
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //than
        Assertions.assertThat(username).isEqualTo("username");
        Assertions.assertThat(role).isEqualTo("ROLE_USER");

    }
}