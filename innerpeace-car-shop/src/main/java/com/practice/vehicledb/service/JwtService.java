package com.practice.vehicledb.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtService {
	static final long EXPIRATIONTIME = 86400000;// 1일을 밀리초로 계산한 값
	static final String PREFIX = "Bearer ";//Bearer 뒤에 공란 한칸 둔다. 클라이언트에게 Bearer jwtkey로 응답되기 때문 

	// @Value("${carapp.app.secret}")
	// private String APP_SECRET;
	// 보안성을 위해서는 위와 같이 해야 하나 테스트 용도로 간단하게 한다
	static final String APP_SECRET = "TruthsAreVeryStraightForwardAndVerySimple";

	// 비밀 키 생성. 시연 용도
	// deprecated
	// static final Key key=Keys.secretKeyFor(SignatureAlgorithm.HS512);
	// 아래로 업데이트 ,, 아래 코드가 깔끔해서 이후 참고해 업데이트 해도 좋을 것 같은 느낌
	// https://stackoverflow.com/questions/78035951/what-should-i-put-instead-of-old-jwt-methods
	// spring boot 3점대 버전으로 현행화 잘되어 있는 자료 
	// https://sjh9708.tistory.com/170
	static final Key key = Keys.hmacShaKeyFor(APP_SECRET.getBytes());

	// 서명된 JWT 토큰 생성
	public String getToken(String username) {
		String token = Jwts.builder().subject(username)
				.expiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(key).compact();
		return token;

	}

	// 요청 권한 부여 헤더에서 토큰 가져와 확인하고 사용자 이름을 얻음
	public String getAuthUser(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token != null) {
			// 아래 deprecated 
			//  setSigningKey(key) 된 것 업데이트 -> verifyWith((SecretKey) key)
			//   getBody() -> getPayload()
			String user = Jwts.parser().verifyWith((SecretKey) key).build()
					.parseSignedClaims(token.replace(PREFIX, "")).getPayload().getSubject();
			if (user != null)
				return user;
		}

		return null;
	}
}
