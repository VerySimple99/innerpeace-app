package com.practice.vehicledb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.practice.vehicledb.web.CarController;

@SpringBootTest
class VehicledbApplicationTests {
	@Autowired
	private CarController controller;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
