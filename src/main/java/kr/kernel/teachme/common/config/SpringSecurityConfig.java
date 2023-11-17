package kr.kernel.teachme.common.config;

import kr.kernel.teachme.common.exception.CustomAuthenticationFailureHandler;
import kr.kernel.teachme.common.jwt.JwtAuthenticationFilter;
import kr.kernel.teachme.common.jwt.JwtAuthorizationFilter;
import kr.kernel.teachme.common.jwt.JwtProperties;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.repository.MemberRepository;
import kr.kernel.teachme.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();
        http.rememberMe().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class
        ).addFilterBefore(
                new JwtAuthorizationFilter(memberService),
                BasicAuthenticationFilter.class
        );
        http.authorizeRequests()
                .antMatchers("/", "/home", "/member/**","/img/**","/style/**","/js/**","/lecture/**","/fragments/**", "/api/**").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger-ui/**").permitAll()
                .antMatchers("/crawler/**", "/report/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .failureHandler(authenticationFailureHandler())
                .defaultSuccessUrl("/")
                .permitAll();
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies(JwtProperties.COOKIE_NAME)
                .deleteCookies(JwtProperties.REFRESH_COOKIE_NAME);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            Member member = memberService.findByUsername(username);
            if (member == null) {
                throw new UsernameNotFoundException(username);
            }
            return member;
        };
    }
}
