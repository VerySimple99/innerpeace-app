package com.practice.vehicledb.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.vehicledb.domain.AccountCredentials;
import com.practice.vehicledb.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials){
		UsernamePasswordAuthenticationToken creds = 
				new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword());
		Authentication auth = authenticationManager.authenticate(creds);
		// 토큰 생성 
		String jwts = jwtService.getToken(auth.getName());	
		// 생성된 토큰으로 응답을 생성 
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer "+ jwts) // AUTHORIZATION 인증정보 , Bearer  bearer 는 JWT와 OAuth를 타나내는 인증 타입
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization") //ACCESS_CONTROL_EXPOSE_HEADERS 교차-출처 요청 (cross-origin request)에 대한 응답
				.build();
	}
}










