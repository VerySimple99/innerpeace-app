package com.practice.vehicledb.web;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.practice.vehicledb.service.JwtService;

import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 토큰을 가져옴 
		String jws = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(jws!=null) {
			// 토큰을 확인하고 사용자를 얻음 
			String user = jwtService.getAuthUser(request);
			// 인증
			Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
}







