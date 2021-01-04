package com.nisum.interviewtest.user.service;

import com.nisum.interviewtest.user.dto.ResponseDTO;
import com.nisum.interviewtest.user.dto.UserDataDTO;
import com.nisum.interviewtest.user.dto.UserResponseDTO;
import com.nisum.interviewtest.user.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    UserResponseDTO signin(String username, String password);
    UserResponseDTO signup(UserDataDTO userDto);
    User saveUser(User user);
    ResponseDTO search(HttpServletRequest req, String username);
    ResponseDTO whoami(HttpServletRequest req);
    String refresh(String username);
}
