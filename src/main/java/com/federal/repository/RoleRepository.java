package com.federal.repository;

import com.federal.entities.Roles;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sagar on 4/15/17.
 */
public interface RoleRepository extends CrudRepository<Roles,Long> {

    Roles findByname(String name);
}
