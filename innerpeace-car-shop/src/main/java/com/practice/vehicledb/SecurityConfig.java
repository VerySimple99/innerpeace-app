package com.practice.vehicledb;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.practice.vehicledb.web.AuthenticationFilter;

import lombok.RequiredArgsConstructor;

//spring boot 2 버전 대의 WebSecurityConfigurerApapter => deprecated 
//public class SecurityConfig extends WebSecurityConfigurerApapter {
//spring boot 3 버전 SecurityFilterChain 을 이용한 방식
//https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
//https://backendstory.com/spring-security-how-to-replace-websecurityconfigureradapter/

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	// @Autowired
	private final UserDetailsService userDetailsService;
	private final AuthenticationFilter authenticationFilter;
	private final AuthEntryPoint exceptionHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// deprecated 업데이트
	// https://backendstory.com/spring-security-how-to-replace-websecurityconfigureradapter/
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//http.csrf().disable().cors().and().authorizeRequests().anyRequest().permitAll();
		// deprecated 되어 아래와 같이 표현 했음 
//		http.csrf((csrfConfig) -> csrfConfig.disable())
//		.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
//				.authorizeHttpRequests((authorizeRequest) -> authorizeRequest.anyRequest().permitAll());
		
		http.csrf((csrfConfig) -> csrfConfig.disable())
				.sessionManagement(
						(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.requestMatchers("/login").permitAll()
						.anyRequest().authenticated())
				.exceptionHandling((exceptionHandling)->exceptionHandling.authenticationEntryPoint(exceptionHandler))
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
				
		return http.build();
	}

	/*
	 * CORS Filter 추가 : 교차 출처 리소스 공유 설정 , 다른 출처에서 요청을 보내는 프런트 엔드에 필요. CORS 필터는 요청을
	 * 가로채고 해당 요청이 교차 출처에서 확인되면 적절한 헤더를 요청에 추가한다. 전역 CORS 필터 추가
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		// 출처를 명시적으로 허용하려면 아래와 같이 명시하고 위의 filterChain 메서드에 cors() 를 추가하면 된다
		//config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(false);
		config.applyPermitDefaultValues();

		source.registerCorsConfiguration("/**", config);
		return source;
	}

}
