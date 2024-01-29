package com.example.cache;

import com.example.cache.domain.entity.User;
import com.example.cache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class CacheApplication implements ApplicationRunner {

	//private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

//		userRepository.save(User.builder().name("noa").email("noa@fastcampus.co.kr").build());
//		userRepository.save(User.builder().name("bob").email("bob@fastcampus.co.kr").build());
//		userRepository.save(User.builder().name("greg").email("greg@fastcampus.co.kr").build());

	}
}
