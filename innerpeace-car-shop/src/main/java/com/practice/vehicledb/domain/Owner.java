package com.practice.vehicledb.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Owner {
	@Id
	@GeneratedValue
	private long ownerId;
	private String firstname,secondname;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<Car> cars;
	
	@Builder
	public Owner(String firstname, String secondname) {
		super();
		this.firstname = firstname;
		this.secondname = secondname;
	}	
}
