package com.korges.springsecurity;

import com.google.common.collect.Sets;
import com.korges.springsecurity.entity.Permission;
import com.korges.springsecurity.entity.User;
import com.korges.springsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.korges.springsecurity.security.UserPermission.ADMIN_READ;
import static com.korges.springsecurity.security.UserPermission.ADMIN_WRITE;
import static com.korges.springsecurity.security.UserPermission.USER_READ;
import static com.korges.springsecurity.security.UserPermission.USER_WRITE;
import static com.korges.springsecurity.security.UserRole.ADMIN;
import static com.korges.springsecurity.security.UserRole.GUEST;
import static com.korges.springsecurity.security.UserRole.USER;

@Component
public class UserRepositoryInit implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserRepositoryInit(PasswordEncoder passwordEncoder,
                              UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initUserRepository();
    }

    private void initUserRepository() {
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(ADMIN)
                .permissions(Sets.newHashSet(
                        new Permission(ADMIN_READ),
                        new Permission(ADMIN_WRITE),
                        new Permission(USER_READ),
                        new Permission(USER_WRITE))
                )
                .build();

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .role(USER)
                .permissions(Sets.newHashSet(
                        new Permission(USER_READ),
                        new Permission(USER_WRITE))
                )
                .build();

        User guest = User.builder()
                .username("guest")
                .password(passwordEncoder.encode("guest"))
                .role(GUEST)
                .permissions(Sets.newHashSet())
                .build();

        List<User> users = Arrays.asList(admin, user, guest);

        this.userRepository.saveAll(users);
    }
}
