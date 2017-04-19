package com.federal;

import com.federal.entities.Roles;
import com.federal.entities.UserRoles;
import com.federal.entities.Users;
import com.federal.services.UserService;
import com.federal.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class FederalNepalApplication implements CommandLineRunner {


	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(FederalNepalApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Users users = new Users();
		users.setFirstName("sagar");
		users.setLastName("limbu");
		users.setUsername("sagar");
		users.setPassword(SecurityUtility.passwordEncoder().encode("password"));
		users.setEmail("lovely11sagar@gmail.com");

		Set<UserRoles> userRoles = new HashSet<>();
		Roles roles = new Roles();
		roles.setName("ADMIN");
		roles.setRoleId(1);
		userRoles.add(new UserRoles(users,roles));
		userService.createUser(users,userRoles);


	}
}
