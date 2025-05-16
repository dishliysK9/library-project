package com.dishoo.library_project.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // disbl csrf
        http.csrf().disable();

        //protect endpoints /api/<type>/secure
        http.authorizeRequests(config ->
                        config
                                .antMatchers("/api/books/secure/**")
                                .authenticated())
                .oauth2ResourceServer()
                .jwt();

        // cors filters
        http.cors();

        //add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        //force a non-empty response body for 401 to make response readable
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
}
