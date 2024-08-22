package com.medicalapplication.medicalapplication.repostory;


import com.medicalapplication.medicalapplication.entity.User;
import com.medicalapplication.medicalapplication.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepostory extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    List<User> findByUserRole(UserRole userRole);




}
