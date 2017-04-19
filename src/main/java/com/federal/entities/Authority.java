package com.federal.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

/**
 * Created by sagar on 4/11/17.
 */

public class Authority implements GrantedAuthority {

    private final String authority;

    public Authority(String authority){this.authority=authority;}

    @Override
    public String getAuthority() {
        return authority;
    }
}
