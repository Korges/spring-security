package com.korges.springsecurity.jwt;

import lombok.Getter;

@Getter
public class UsernameAndPasswordAuthenticationRequest {
    private String username;
    private String password;
}
