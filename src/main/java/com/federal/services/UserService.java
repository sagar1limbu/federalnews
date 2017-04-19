package com.federal.services;

import com.federal.entities.PasswordResetToken;
import com.federal.entities.UserRoles;
import com.federal.entities.UserVerficationToken;
import com.federal.entities.Users;
import org.apache.catalina.User;

import java.util.Set;

/**
 * Created by sagar on 4/12/17.
 */
public interface UserService {

    PasswordResetToken getPasswordResetToken(final String token );

    void createPasswordResetTokenForUser(final Users users, final String token);

    UserVerficationToken getUserVerficationToken(final String token);

    void createPasswordVerficationToken(final Users users, String token);

    Users findByEmail(String email);

    Users findByUsername(String username);

    Users createUser(Users users, Set<UserRoles> userRoles) throws Exception;

    Users save(Users users);
}
