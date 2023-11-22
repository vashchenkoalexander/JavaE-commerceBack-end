package com.payoya.diplomaproject.api.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role  {
    USER(
            Set.of(
                    Permission.USER_CREATE,
                    Permission.USER_READ,
                    Permission.USER_UPDATE,
                    Permission.USER_DELETE
            )
    ),
    MODERATOR(
            Set.of(
                    Permission.USER_CREATE,
                    Permission.USER_READ,
                    Permission.USER_UPDATE,
                    Permission.USER_DELETE,
                    Permission.MODERATOR_CREATE,
                    Permission.MODERATOR_READ,
                    Permission.MODERATOR_UPDATE,
                    Permission.MODERATOR_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    Permission.USER_CREATE,
                    Permission.USER_READ,
                    Permission.USER_UPDATE,
                    Permission.USER_DELETE,
                    Permission.MODERATOR_CREATE,
                    Permission.MODERATOR_READ,
                    Permission.MODERATOR_UPDATE,
                    Permission.MODERATOR_DELETE,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE

            )
    );

    private final Set<Permission> permissions;

    Role(Set<Permission> strings) {
        this.permissions = strings;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

}
