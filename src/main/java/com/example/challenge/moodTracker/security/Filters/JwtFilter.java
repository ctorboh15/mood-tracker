package com.example.challenge.moodTracker.security.Filters;

import com.example.challenge.moodTracker.Constants;
import com.example.challenge.moodTracker.exceptions.ApiError;
import com.example.challenge.moodTracker.exceptions.custom.UnAuthorizedException;
import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.security.JwtTokenUtil;
import com.example.challenge.moodTracker.services.interfaces.AppUserService;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.challenge.moodTracker.Constants.TOKEN_PREFIX;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private Gson gson = new Gson();

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AppUserService appUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(Constants.HEADER_STRING);
        String username;
        String authToken;
        String errorMessage = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
                Optional<AppUser> savedAppUser = Optional.ofNullable(appUserService.findByUsername(username));

                if(savedAppUser.isPresent()){
                    httpServletRequest.setAttribute(Constants.JWT_USER_HEADER, savedAppUser.get());
                }
                else {
                    errorMessage = "No user found from JWT";
                    logger.warn(errorMessage);
                }

            } catch (IllegalArgumentException e) {
                errorMessage = "an error occurred during getting username from token";
                logger.error(errorMessage, e);
            } catch (ExpiredJwtException e) {
                errorMessage = "the token is expired and not valid anymore";
                logger.warn(errorMessage, e);
            } catch(SignatureException e){
                errorMessage = "Authentication Failed. Username or Password not valid.";
                logger.error(errorMessage);
            }
            catch (Exception e) {
                errorMessage = e.getLocalizedMessage();
                logger.error(errorMessage);
            }
        }

        if(errorMessage != null || header == null){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Authentication Failed");
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> excludeUrlPatterns = new ArrayList<>();
        excludeUrlPatterns.add("/account/login");
        excludeUrlPatterns.add("/account/signup");
        AntPathMatcher pathMatcher = new AntPathMatcher();

        return excludeUrlPatterns.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }
}
