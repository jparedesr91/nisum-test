package com.nisum.interviewtest.user;

import com.nisum.interviewtest.user.dto.UserResponseDTO;
import com.nisum.interviewtest.user.model.User;
import com.nisum.interviewtest.user.repository.UserRepository;
import com.nisum.interviewtest.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MsUserApplicationTests {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	@Test
	public void testSearchData() {
		LocalDateTime rightNow = LocalDateTime.now();
		when(userRepository.findByUsername("jparedesr91@gmail.com")).thenReturn(new User(UUID.fromString("4bb2a34a-a074-4cb6-86cd-9d00ec74f93b"),"julio@gmail.com","julio","password",true, rightNow,rightNow,rightNow,null,null));
		assertEquals(new UserResponseDTO(UUID.fromString("4bb2a34a-a074-4cb6-86cd-9d00ec74f93b"),true,rightNow,rightNow,rightNow,null), userService.search("jparedesr91@gmail.com"));
	}

}
