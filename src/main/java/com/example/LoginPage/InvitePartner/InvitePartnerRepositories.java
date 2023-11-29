package com.example.LoginPage.InvitePartner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitePartnerRepositories extends JpaRepository<Partner,Long> {
        Partner findByPartnerPhone(String phone);
}
