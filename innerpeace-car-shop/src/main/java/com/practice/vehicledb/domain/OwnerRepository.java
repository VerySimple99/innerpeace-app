package com.practice.vehicledb.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(path = "owners")
public interface OwnerRepository extends JpaRepository<Owner, Long> {
	public Optional<Owner> findByFirstname(String firstname);
}
