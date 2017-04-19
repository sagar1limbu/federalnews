package com.federal.repository;

import com.federal.entities.Users;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sagar on 4/11/17.
 */
public interface UserRepository extends CrudRepository<Users,Long> {

    Users findByUsername(String username);
    Users findByEmail(String email);
}
