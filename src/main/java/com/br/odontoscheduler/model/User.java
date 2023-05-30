package com.br.odontoscheduler.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Document
public class User implements UserDetails {
    @Id
    private String id;

    private String username;
    private String password;
    private String name;
    private String document;
    private String phoneNumber;

    @Indexed(unique = true)
    private String email;

    public User(String username, String password, String name, String document, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.document = document;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
