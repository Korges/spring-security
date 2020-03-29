package com.korges.springsecurity.entity;

import com.korges.springsecurity.security.UserPermission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Enumerated(EnumType.STRING)
    private UserPermission userPermission;

    @Autowired
    public Permission(UserPermission userPermission) {
        this.userPermission = userPermission;
    }
}