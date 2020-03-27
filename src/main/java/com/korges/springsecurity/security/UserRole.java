package com.korges.springsecurity.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.korges.springsecurity.security.UserPermission.*;

@Getter
@AllArgsConstructor
enum UserRole {
    ADMIN(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE, USER_READ, USER_WRITE)),
    USER(Sets.newHashSet(USER_READ, USER_WRITE)),
    GUEST(Sets.newHashSet());

    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
