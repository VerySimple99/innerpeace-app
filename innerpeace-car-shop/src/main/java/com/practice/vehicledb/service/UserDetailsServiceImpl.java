package com.practice.vehicledb.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.vehicledb.domain.User;
import com.practice.vehicledb.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user=repository.findByUsername(username);
		UserBuilder builder=null;
		if(user.isPresent()) {
			User currentUser=user.get();
			builder=org.springframework.security.core.userdetails.User.withUsername(username)
					.password(currentUser.getPassword()).roles(currentUser.getRole());			
		}else {
			throw new UsernameNotFoundException("User not found. innerpeace");		
		}
		return builder.build();
	}
}











