package com.korges.springsecurity.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum UserPermission {
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;
}
