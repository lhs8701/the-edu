package joeuncamp.dabombackend.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    final String role;
}
