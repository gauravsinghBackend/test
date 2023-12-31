package com.example.LoginPage.LoginSignUp;


import com.example.LoginPage.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    User findByEmail(String email);
    User findByPhone(String phone);

    User getUsersById(Long userId);
}
