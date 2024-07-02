package com.practice.vehicledb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j

//CommandLineRunner : 스프링 부트 어플리케이션 시작전에 run()이 실행되므로 예제 데이터 준비하기 적합
public class VehicledbApplication implements CommandLineRunner {
//	@Autowired
//	private CarRepository carRepository;
//	@Autowired
//	private OwnerRepository ownerRepository;
//	@Autowired
//	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(VehicledbApplication.class, args);
		log.info("Application started");
	}

	@Override
	@Transactional
	/*
	 * could not initialize proxy => owner 는 지연 로딩 대상이므로 LazyInitializationException
	 * 위 에러가 발생하는 이유는 준영속 상태의 엔티티에서 지연 로딩을 할 때 발생한다.
	 * 
	 * carRepository.findXXX() 메소드가 시작될 때 트랜잭션이 시작되고, 메소드가 종료될 때 트랜잭션이 커밋되기 때문이다.
	 * 트랜잭션이 커밋되면 영속성 컨텍스트가 종료되고 car는 준영속 상태이기에, 프록시를 사용한 지연 로딩에서 에러가 발생하는 것이다.
	 * 
	 * 이를 해결하는 간단한 방법으로 @Transactional을 사용하면 된다. getTeamFromMember()
	 * 메소드에 @Transactional을 사용해서 메소드의 시작과 끝을 트랜잭션의 범위로 설정해주면 해결된다.
	 * 
	 * 트랜잭션A(부모) 내부에 트랜잭션B(자식)가 실행되는 경우, 트랜잭션A에 트랜잭션 B가 합류한다. 다시말해 커밋이나 롤백의 기준이 트랜잭션
	 * A만 존재하는 경우와 같아진다.
	 */
	public void run(String... args) throws Exception {
		// 예제 데이터
//		Owner owner1 = Owner.builder().firstname("흥민").secondname("손").build();
//		Owner owner2 = Owner.builder().firstname("재성").secondname("이").build();
//		Owner owner3 = Owner.builder().firstname("승우").secondname("이").build();
//		Owner owner4 = Owner.builder().firstname("희찬").secondname("황").build();
//
//		ownerRepository.saveAll(Arrays.asList(owner1, owner2, owner3, owner4));
//
//		carRepository.save(Car.builder().model("레이").brand("기아").color("검정").registerNumber("22수7055")
//				.registerYear(2015).price(1300).owner(owner1).build());
//		carRepository.save(Car.builder().model("모닝").brand("현대").color("파랑").registerNumber("99하9999")
//				.registerYear(2009).price(800).owner(owner2).build());
//		carRepository.save(Car.builder().model("트랙스").brand("쉐보레").color("초록").registerNumber("11호1111")
//				.registerYear(2024).price(2500).owner(owner3).build());
//		carRepository.save(Car.builder().model("K5").brand("기아").color("그레이").registerNumber("267마2476")
//				.registerYear(2021).price(3500).owner(owner4).build());

//		for (Car car : carRepository.findAll())
//			log.info(car.getId() + " " + car.getBrand() + " " + car.getModel() + "" + car.getOwner().getFirstname());
/*
 *  아래 사이트 이용 
 *  https://www.devglan.com/online-tools/bcrypt-hash-generator
 */
//		userRepository.save(User.builder().username("user").password("$2a$04$1Yc9QXLEU1om4XJXXHgweuegqZxIjMhU7PaBGzHWDSJk5CklmaZyK").role("USER").build());
//		userRepository.save(User.builder().username("admin").password("$2a$04$Nm35KDK8b7tU49/bQp0l9u6LmQj1/gQEm0iNlbcvOJVhtWNzv.28K").role("ADMIN").build());
	}
}
