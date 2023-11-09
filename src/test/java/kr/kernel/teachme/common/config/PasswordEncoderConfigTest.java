package kr.kernel.teachme.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncoderConfigTest {

    private final PasswordEncoderConfig passwordEncoderConfig = new PasswordEncoderConfig();

    @Test
    void passwordEncoderBeanShouldBeCreated() {
        PasswordEncoder passwordEncoder = passwordEncoderConfig.passwordEncoder();
        assertThat(passwordEncoder).isNotNull();
        assertThat(passwordEncoder).isInstanceOf(PasswordEncoder.class);

        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
