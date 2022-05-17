package com.pegasus.ams.mgmt.repository;

import com.pegasus.ams.mgmt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsername(String username);

    Optional<User> findFirstByEmail(String email);

    @Query("select u from User u where lower(u.username) = lower(:request) or lower(u.email) = lower(:request) ")
    Optional<User> findFirstByEmailOrUsername(@Param("request") String request);

    @Query("select u from User u join u.roles r where (:firstName is null or u.firstName like %:firstName%) " +
                                                "and (:lastName is null or u.lastName like %:lastName%) " +
                                                "and (:email is null or u.email like %:email%) ")
    Page<User> searchUsers(@Param("firstName") String firstName,
                           @Param("lastName") String lastName,
                           @Param("email") String email,
                           Pageable pageable);

    @Query("select u from User u where lower(u.username) = lower(:username)and lower(u.username) <> lower(:username)")
    Optional<User> findExistByUsername(String username);

    @Query("select u from User u where lower(u.email) = lower(:email) and lower(u.email) <> lower(:email)")
    Optional<User> findExistByEmail(String email);
}
