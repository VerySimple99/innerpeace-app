package com.practice.vehicledb.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
 *  Long : pk id 필드 형식이 Long 
 */
@RepositoryRestResource(path = "cars")
public interface CarRepository extends JpaRepository<Car, Long> {
	List<Car> findByBrand(@Param("brand") String brand);
	List<Car> findByColor(@Param("color") String color);		
}
