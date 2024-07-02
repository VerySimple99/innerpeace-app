package com.practice.vehicledb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.practice.vehicledb.domain.Owner;
import com.practice.vehicledb.domain.OwnerRepository;

@DataJpaTest
public class OwnerRepositoryTest {
	@Autowired
	private OwnerRepository repository;
	
	@Test
	void saveOwner() {
		repository.save(new Owner("Lucy", "Smith"));
		assertThat(repository.findByFirstname("Lucy").isPresent()).isTrue();
	}
	
	
	@Test
	void deleteOwners() {
		repository.save(new Owner("Lisa", "Morrison"));
		repository.deleteAll();
		assertThat(repository.count()).isEqualTo(0);
	}
	
}
