package com.dishoo.library_project.filter;

import com.dishoo.library_project.dao.UserRepository;
import com.dishoo.library_project.entity.User;
import com.dishoo.library_project.utils.JWTExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAutoRegistrationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    //guarantees user exist
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            String userEmail = JWTExtractor.payloadJWTExtractor(token, "\"sub\"");

            if (userEmail != null) {
                userRepository.findByEmail(userEmail).orElseGet(() -> {
                    try {
                        User newUser = new User();
                        newUser.setEmail(userEmail);
                        newUser.setName(userEmail.split("@")[0]);
                        newUser.setRole("USER");
                        return userRepository.save(newUser);
                    } catch (DataIntegrityViolationException e) {
                        // Another thread likely inserted the same user — ignore
                        return userRepository.findByEmail(userEmail).get();
                    }
                });
            }
        }

        filterChain.doFilter(request, response);
    }
}
