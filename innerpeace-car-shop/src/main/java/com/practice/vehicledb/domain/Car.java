package com.practice.vehicledb.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
//@Builder// constructor level 로 변경해본다 , 필수와 선택 필드를 구분해서 할당해보기 위해 
/*
 * @NoArgsConstructor(access = AccessLevel.PROTECTED) 실무에서 권장함 
 * Entity의 **Proxy 조회** 때문이다. 정확히는 엔티티의 연관 관계에서 
 * 지연 로딩의 경우에는 실제 엔티티가 아닌 프록시 객체를 통해서 조회를 한다. 
 * 프록시 객체를 사용하기 위해서 JPA 구현체는, 실제 엔티티의 기본 생성자를 통해 프록시 객체를 생성하는데, 이 때 접근 권한이
 * private이면 프록시 객체를 생성할 수 없는 것이다.
 * private 은 다른 클래스에서는 접근 불가, 하지만 protected는 상속시 다른 패키지에서 허용됨
 * 프록시는 상속을 통해서 만들어짐 
 * 프록시를 사용하는 이유는 성능 향상을 위한 지연 로딩에서 사용되기 때문 , 지연 로딩은 직접 
 * https://yeon-kr.tistory.com/190
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String brand, model, color, registerNumber;
	// springboot 2.7 이상 year 가 예약어가 되어 `year` 로 처리, 그대로 사용하면 에러
	/*
	 * @Column(name="`YEAR`") private int year;
	 */
	private int registerYear;
	private int price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private Owner owner;

	/*
	 * @Builder public Car(String brand, String model, String color, String
	 * registerNumber, int registerYear, int price) { super(); this.brand = brand;
	 * this.model = model; this.color = color; this.registerNumber = registerNumber;
	 * this.registerYear = registerYear; this.price = price; }
	 */
	@Builder
	public Car(String brand, String model, String color, String registerNumber, int registerYear, int price,
			Owner owner) {
		super();
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.registerYear = registerYear;
		this.price = price;
		this.owner = owner;
	}
	

}
