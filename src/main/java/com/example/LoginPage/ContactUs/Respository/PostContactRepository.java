package com.example.LoginPage.ContactUs.Respository;

import com.example.LoginPage.ContactUs.Entity.PostContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostContactRepository extends JpaRepository<PostContactEntity, Long> {

}
