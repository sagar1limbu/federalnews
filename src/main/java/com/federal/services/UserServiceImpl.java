package com.federal.services;

import com.federal.entities.PasswordResetToken;
import com.federal.entities.UserRoles;
import com.federal.entities.UserVerficationToken;
import com.federal.entities.Users;
import com.federal.repository.RoleRepository;
import com.federal.repository.UserRepository;
import com.federal.repository.UserVerficationRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by sagar on 4/12/17.
 */

@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserVerficationRepository userVerficationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserVerficationToken getUserVerficationToken(String token) {
        return userVerficationRepository.findByToken(token);
    }

    @Override
    public void createPasswordVerficationToken(final Users users, final String token) {
        final UserVerficationToken uToken = new UserVerficationToken(token,users);
        userVerficationRepository.save(uToken);

    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return null;
    }

    @Override
    public void createPasswordResetTokenForUser(Users users, String token) {

    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public Users createUser(Users users, Set<UserRoles> userRoles) throws Exception {

        Users localUser= userRepository.findByUsername(users.getUsername());

        if(localUser != null){
            LOG.info("username already exists",users.getUsername());
        }
        else {
            for(UserRoles ur: userRoles){
                roleRepository.save(ur.getRoles());
            }
            users.getUserRoles().addAll(userRoles);
            localUser=userRepository.save(users);
        }

        return localUser;

    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }
}
