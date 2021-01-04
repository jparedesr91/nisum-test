package com.nisum.interviewtest.user;

import com.nisum.interviewtest.user.dto.ResponseDTO;
import com.nisum.interviewtest.user.dto.UserResponseDTO;
import com.nisum.interviewtest.user.model.Phone;
import com.nisum.interviewtest.user.model.Role;
import com.nisum.interviewtest.user.model.User;
import com.nisum.interviewtest.user.repository.UserRepository;
import com.nisum.interviewtest.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MsUserApplicationTests {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	@Mock
	PasswordEncoder passwordEncoder;

	final String name = "Admin";
	final String username = "admin@admin.com";
	final boolean active = true;

	@Test
	public void testGetUserByUsername() {
		Phone phone = Phone.builder()
				.cityCode("123")
				.countryCode("123")
				.number("123")
				.build();

		List<Phone> phonesList = new ArrayList<>();
		phonesList.add(phone);

		List<Role> rolesList = new ArrayList<>();
		rolesList.add(Role.ROLE_ADMIN);

		UUID uuid = UUID.randomUUID();
		User user = User.builder()
				.active(active)
				.id(uuid)
				.name(name)
				.username(username)
				.password(passwordEncoder.encode("admin"))
				.phones(phonesList)
				.roles(rolesList)
				.build();

		when(userRepository.findByUsername(username)).thenReturn(user);
		ResponseDTO resultOpt = userService.search(null,username);
		UserResponseDTO result = resultOpt.getData();
		assertEquals(uuid, result.getId());
		assertEquals(name, result.getName());
		assertEquals(active, result.isActive());
	}

	@Test
	public void saveToDo() {
		List<Role> rolesList = new ArrayList<>();
		rolesList.add(Role.ROLE_ADMIN);

		Phone phone = Phone.builder()
				.cityCode("123")
				.countryCode("123")
				.number("123")
				.build();

		List<Phone> phonesList = new ArrayList<>();
		phonesList.add(phone);

		UUID uuid = UUID.randomUUID();

		User user = User.builder()
				.active(active)
				.id(uuid)
				.name(name)
				.username(username)
				.password(passwordEncoder.encode("admin"))
				.phones(phonesList)
				.roles(rolesList)
				.build();

		when(userRepository.save(user)).thenReturn(user);
		User result = userService.saveUser(user);
		assertEquals(uuid, result.getId());
		assertEquals(name, result.getName());
		assertEquals(active, result.isActive());
	}

}
