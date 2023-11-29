package com.example.LoginPage.ContactUs.Respository;

import com.example.LoginPage.ContactUs.Entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

}
