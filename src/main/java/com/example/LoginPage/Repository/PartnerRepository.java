package com.example.LoginPage.Repository;

import com.example.LoginPage.Models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner,Long> {
    Partner findByPartnerPhone(String phone);
}
