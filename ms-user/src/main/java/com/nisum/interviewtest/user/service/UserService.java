package com.nisum.interviewtest.user.service;

import com.nisum.interviewtest.user.dto.UserDataDTO;
import com.nisum.interviewtest.user.dto.UserResponseDTO;
import com.nisum.interviewtest.user.errors.EntityNotFoundException;
import com.nisum.interviewtest.user.errors.ExistingValueException;
import com.nisum.interviewtest.user.errors.InvalidUserPasswordException;
import com.nisum.interviewtest.user.model.User;
import com.nisum.interviewtest.user.repository.UserRepository;
import com.nisum.interviewtest.user.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import static com.nisum.interviewtest.user.mapping.UserMap.USER_MAP;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtTokenProvider jwtTokenProvider;

  private final AuthenticationManager authenticationManager;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
  }

  public UserResponseDTO signin(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      User userDB = userRepository.findByUsername(username);
      UserResponseDTO userResponseDTO = USER_MAP.mapToResponseDto(userDB, jwtTokenProvider.createToken(username,userRepository.findByUsername(username).getRoles()));
      userDB.setLoginTime(LocalDateTime.now());
      userRepository.save(userDB);
      return userResponseDTO;
    } catch (AuthenticationException e) {
      throw new InvalidUserPasswordException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public UserResponseDTO signup(UserDataDTO userDto) {
    User user = USER_MAP.map(userDto);
    if (!userRepository.existsByUsername(user.getUsername())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
      return USER_MAP.mapToResponseDto(userRepository.findByUsername(user.getUsername()), jwtTokenProvider.createToken(user.getUsername(),userRepository.findByUsername(userDto.getUsername()).getRoles()));
    } else {
      throw new ExistingValueException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public UserResponseDTO search(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new EntityNotFoundException(User.class, "username", username);
    }
    return USER_MAP.mapToResponseDto(user);
  }

  public UserResponseDTO whoami(HttpServletRequest req) {
    return USER_MAP.mapToResponseDto(userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))));
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(username,userRepository.findByUsername(username).getRoles());
  }

}
