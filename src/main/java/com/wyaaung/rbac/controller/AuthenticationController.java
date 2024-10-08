package com.wyaaung.rbac.controller;

import com.wyaaung.rbac.domain.User;
import com.wyaaung.rbac.dto.AuthRequestDto;
import com.wyaaung.rbac.dto.AuthResponseDto;
import com.wyaaung.rbac.dto.RegisterDto;
import com.wyaaung.rbac.dto.TokenDto;
import com.wyaaung.rbac.dto.UserDetailsDto;
import com.wyaaung.rbac.service.AuthenticationService;
import com.wyaaung.rbac.transformer.AuthResponseTransformer;
import com.wyaaung.rbac.transformer.TokenTransformer;
import com.wyaaung.rbac.transformer.UserTransformer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  @ResponseStatus(OK)
  public UserDetailsDto registerUser(@RequestBody RegisterDto registerDto) {
    return UserTransformer.toUserDetailsDto(authenticationService.registerUser(UserTransformer.toUserFromRegisterDto(registerDto)));
  }

  @PostMapping("/authenticate")
  @ResponseStatus(OK)
  public AuthResponseDto authenticateUser(@RequestBody AuthRequestDto authRequestDto) {
    User user = new User();
    user.setUsername(authRequestDto.username());
    user.setPassword(authRequestDto.password());
    return AuthResponseTransformer.toDto(authenticationService.authenticateUser(user));
  }

  @PostMapping("/refresh-token")
  @ResponseStatus(OK)
  public TokenDto refreshToken(@RequestHeader(AUTHORIZATION) String authorization) {
    return TokenTransformer.toDto(authenticationService.refreshToken(authorization));
  }
}
