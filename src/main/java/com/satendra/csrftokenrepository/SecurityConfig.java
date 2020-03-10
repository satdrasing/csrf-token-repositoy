package com.satendra.csrftokenrepository;

import org.apache.tomcat.util.net.jsse.PEMFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("saten").password(passwordEncoder().encode("sa")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf()
                .csrfTokenRepository(new CookieCsrfTokenRepository()) // for cookie = csrf SPA for POST PUT DELETE  not GET
              /*  .and().rememberMe().alwaysRemember(true) // cookies for browser
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/ //backend stateless
                .and()
                .authorizeRequests().anyRequest().authenticated().and().httpBasic();

        //base class: CsrfTokenRepository
        //HttpSessionCsrfTokenRepository
        //CookieCsrfTokenRepository

        //if server is stateless, server will not sent session cookies but can sent remember me. Each request to server will be authenticated
        //with remember me. Not needed to login every time. client will manage session in this case.
        // In each request client can sent token for api call.(only remember me can be manage by the server)

        //if server is stateful session and remember me cookie injected to browser. The client session and remember me can
        // be handled by server.(in this case remember me and session manage by server)


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
