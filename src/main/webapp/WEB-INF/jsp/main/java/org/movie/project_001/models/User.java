package org.movie.project_001.models;

import org.movie.project_001.utils.UserRoles;

import java.util.UUID;

public class User {

    private UUID userId= UUID.randomUUID();
    private String username;
    private String password;
    private String email;
    private UserRoles role = UserRoles.USER;

    public User() {
    }

    public User(String username, String email,UserRoles role,String password) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRoles role) {
        this.role = role;

    }
}

