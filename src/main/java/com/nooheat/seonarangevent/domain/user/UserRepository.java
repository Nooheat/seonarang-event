package com.nooheat.seonarangevent.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;


public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u "+
            "FROM User u "+
            "ORDER BY u.createdDate DESC")
    Stream<User> findAllDesc();
}