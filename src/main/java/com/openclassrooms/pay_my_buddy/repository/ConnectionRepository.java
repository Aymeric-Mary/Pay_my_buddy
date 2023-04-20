package com.openclassrooms.pay_my_buddy.repository;

import com.openclassrooms.pay_my_buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<User, Long> {
}