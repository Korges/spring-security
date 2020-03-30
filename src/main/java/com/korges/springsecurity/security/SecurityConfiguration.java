package com.korges.springsecurity.security;

import com.korges.springsecurity.jwt.JwtConfig;
import com.korges.springsecurity.jwt.JwtTokenVerifier;
import com.korges.springsecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.korges.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.korges.springsecurity.security.UserPermission.USER_READ;
import static com.korges.springsecurity.security.UserPermission.USER_WRITE;
import static com.korges.springsecurity.security.UserRole.ADMIN;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final JwtConfig jwtConfig;

    public SecurityConfiguration(UserService userService, JwtConfig jwtConfig) {
        this.userService = userService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //If you’ve enabled Spring Security in your Spring Boot application, you will not be able to access the
        //H2 database console. With its default settings under Spring Boot, Spring Security will block access to
        //H2 database console.
        //
        //To enable access to the H2 database console under Spring Security you need to change three things:
        //
        // * Allow all access to the url path /h2/**.
        // * Disable CRSF (Cross-Site Request Forgery). By default, Spring Security will protect against CRSF attacks.
        // * Since the H2 database console runs inside a frame, you need to enable this in in Spring Security.
        http
                .csrf()
                    .ignoringAntMatchers("/h2/**")
                .and()
                .authorizeRequests()
                    .antMatchers("/h2/**").permitAll()
                .and()
                .headers()
                    .frameOptions().sameOrigin();
        //End of H2 console configuration

        http
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringAntMatchers("/", "/login")
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api/users/**").hasAuthority(USER_READ.name())
                    .antMatchers(HttpMethod.POST, "/api/users/**").hasAuthority(USER_WRITE.name())
                    .antMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority(USER_WRITE.name())
                    .antMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority(USER_WRITE.name())
                    .antMatchers("/api/admin/**").hasRole(ADMIN.name())
                    .anyRequest()
                    .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
