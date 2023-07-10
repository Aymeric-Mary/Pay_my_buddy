package com.openclassrooms.pay_my_buddy.repository;

import com.openclassrooms.pay_my_buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("""
            select u from User u
            where :currentUser not member of u.connections
            and u <> :currentUser
            """)
    List<User> findConnectableUsers(User currentUser);
}
