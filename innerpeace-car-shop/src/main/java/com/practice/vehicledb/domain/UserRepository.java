package com.practice.vehicledb.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(path = "users")
//위의 스프링 데이터 REST는 기본적으로 모든 공용 리포지터리에서 RESTful 웹서비스를 생성한다.
//아래와 같이 하면 리포지터리가 REST 리소스로 노출되지 않는다 
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
