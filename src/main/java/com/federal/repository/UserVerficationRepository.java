package com.federal.repository;

import com.federal.entities.UserVerficationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sagar on 4/18/17.
 */
public interface UserVerficationRepository extends JpaRepository<UserVerficationToken,Long> {

    UserVerficationToken findByToken(String token);
}
