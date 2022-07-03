package com.nhnacademy.myhome.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Basic AuthenticationManager and UserDetailService Create
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(                              // authentication(인증처리)
                        "select user_name, password, enabled " +
                        "from user " +
                        "where user_name = ?")
                .authoritiesByUsernameQuery(                        // authority(권한처리)
                        "select u.user_name, r.role_name " +
                        "from user_role ur inner join user u on ur.user_id = u.user_id " +
                        "inner join role r on ur.role_id = r.role_id " +
                        "where u.user_name = ?");

        AuthenticationManager authenticationManager
                = authenticationManagerBuilder.build();

        http
                .authorizeRequests()
                .antMatchers("/", "/account/register" ,"/css/**").permitAll() // 매쳐스 = url 정의 -> 요런 url 은 로그인 없이도 누구나 접근할 수 있다.
                .anyRequest().authenticated()                       // 그밖에(anyRequest) 모든 요청은 반드시 로그인을해야만(authenticated) 접근할 수 있다.

                .and()
                .formLogin()                                        // 위 authorizeRequest 에서 정의한 주소와 다른 곳을 방문하게 되면
                .loginPage("/account/login")                        // loginPage 로 자동으로 redirect 가 일어나게 됩니다. 그 후, 로그인을 하게되면 다시 원래페이지로 이동합니다.(한마디로, 로그인을 어디에서 할거냐 이말임.)
                .permitAll()                                        // 로그인 페이지는 모든 권한이 주어져야함.
                .and()

                .logout()                                           // 로그아웃 또한 모든 권한이 주어진다.
                .permitAll()
                .and()

                .authenticationManager(authenticationManager);

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // password Encoding 방식
    }
}