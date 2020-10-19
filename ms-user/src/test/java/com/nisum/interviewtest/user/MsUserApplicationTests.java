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
		when(userRepository.findByUsername("jparedesr91@gmail.com")).thenReturn(new User());
		assertEquals(new UserResponseDTO(), userService.search("jparedesr91@gmail.com"));
	}

}
