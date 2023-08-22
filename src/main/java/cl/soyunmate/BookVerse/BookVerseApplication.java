package cl.soyunmate.BookVerse;

import cl.soyunmate.BookVerse.model.RoleEntity;
import cl.soyunmate.BookVerse.model.UserEntity;
import cl.soyunmate.BookVerse.model.enums.ERole;
import cl.soyunmate.BookVerse.repository.UserRepository;
import cl.soyunmate.BookVerse.service.IRoleService;
import cl.soyunmate.BookVerse.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class BookVerseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookVerseApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;



	@Bean
	CommandLineRunner init() {
		return args -> {
			UserEntity userEntity = UserEntity.builder()
					.email("admin@gmail.com")
					.username("admin")
					.firstName("test")
					.lastName("pudu")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.id(1L)
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			userRepository.save(userEntity);
		};
	}


}
