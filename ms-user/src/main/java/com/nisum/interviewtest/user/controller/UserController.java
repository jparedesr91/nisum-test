package com.nisum.interviewtest.user.controller;

import com.nisum.interviewtest.user.dto.ResponseDTO;
import com.nisum.interviewtest.user.dto.UserDataDTO;
import com.nisum.interviewtest.user.dto.UserResponseDTO;
import com.nisum.interviewtest.user.model.UserTokenSession;
import com.nisum.interviewtest.user.service.UserService;
import com.nisum.interviewtest.user.service.UserServiceImpl;
import com.nisum.interviewtest.user.service.UserTokenSessionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
@Api(tags = "users")
public class UserController {

  private final UserService userService;
  private final UserTokenSessionService userTokenSessionService;

  public UserController(UserService userService, UserTokenSessionService userTokenSessionService) {
    this.userService = userService;
    this.userTokenSessionService = userTokenSessionService;
  }

  @PostMapping("/signin")
  @ApiOperation(value = "${UserController.signin}")
  @ApiResponses(value = {
          @ApiResponse(code = 400, message = "Something went wrong"),
          @ApiResponse(code = 403, message = "Access denied"),
          @ApiResponse(code = 404, message = "The user doesn't exist"),
          @ApiResponse(code = 422, message = "Username is already in use")})
  public ResponseEntity<ResponseDTO> login(
      @ApiParam("Email") @RequestParam String email,
      @ApiParam("Password") @RequestParam String password, @ApiParam(hidden = true) @CookieValue(name = "JSESSIONID", defaultValue = "JSESSIONID") String sessionId) {

    UserResponseDTO userResponseDTO = userService.signin(email, password);

    UserTokenSession userTokenSession = new UserTokenSession(email, userResponseDTO.getToken(), sessionId, (long)3600);
    userTokenSession = userTokenSessionService.saveUserTokenSessionMapping(userTokenSession);
    log.info("User "+email+" successfully logged in. User, Token and Session mapping stored."+userTokenSession);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Message", "Success");
    responseHeaders.add("Set-Cookie", userTokenSession.getSessionId());

    return new ResponseEntity<>(ResponseDTO.builder()
            .data(userResponseDTO)
            .mensaje("Usuario loggeado exitosamente!!")
            .build(), responseHeaders, HttpStatus.OK);
  }

  @PostMapping("/signup")
  @ApiOperation(value = "${UserController.signup}")
  @ApiResponses(value = {
          @ApiResponse(code = 400, message = "Something went wrong"),
          @ApiResponse(code = 403, message = "Access denied"),
          @ApiResponse(code = 404, message = "Entity Not Found"),
          @ApiResponse(code = 422, message = "Username is already in use")})
  public ResponseEntity<ResponseDTO> signup(@ApiParam("Signup User") @Valid @RequestBody UserDataDTO user, @ApiParam(hidden = true) @CookieValue(name = "JSESSIONID", defaultValue = "JSESSIONID") String sessionId) {

    UserResponseDTO userResponseDTO = userService.signup(user);

    UserTokenSession userTokenSession = new UserTokenSession(user.getUsername(), userResponseDTO.getToken(), sessionId, (long)3600);
    userTokenSession = userTokenSessionService.saveUserTokenSessionMapping(userTokenSession);
    log.info("User "+user.getUsername()+" successfully logged in. User, Token and Session mapping stored."+userTokenSession);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Message", "Success");
    responseHeaders.add("Set-Cookie", userTokenSession.getSessionId());

    return new ResponseEntity<>(ResponseDTO.builder()
            .data(userResponseDTO)
            .mensaje("Usuario registrado exitosamente!!")
            .build(), responseHeaders, HttpStatus.CREATED);
  }

  @GetMapping(value = "/{email}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Something went wrong"),
      @ApiResponse(code = 403, message = "Access denied"),
      @ApiResponse(code = 404, message = "The user doesn't exist"),
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public ResponseEntity<ResponseDTO> search(HttpServletRequest req, @ApiParam("email") @PathVariable String email) {
    return new ResponseEntity<>(userService.search(req,email), HttpStatus.OK);
  }

  @GetMapping(value = "/me")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Something went wrong"),
      @ApiResponse(code = 403, message = "Access denied"),
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public ResponseEntity<ResponseDTO> whoami(HttpServletRequest req) {
    return new ResponseEntity<>(userService.whoami(req), HttpStatus.OK);
  }

  @GetMapping("/refresh")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public ResponseEntity<String> refresh(HttpServletRequest req) {
    return new ResponseEntity<>(userService.refresh(req.getRemoteUser()), HttpStatus.OK);
  }

}
