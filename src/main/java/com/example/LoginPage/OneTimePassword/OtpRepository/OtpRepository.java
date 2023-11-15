package com.example.LoginPage.OneTimePassword.OtpRepository;

import com.example.LoginPage.OneTimePassword.OTPmodel.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OTP,Integer> {
    @Query("SELECT o FROM OTP o WHERE o.phone = :phone AND o.id = (SELECT MAX(oo.id) FROM OTP oo WHERE oo.phone = :phone)")
    Optional<OTP> findByPhone(@Param("phone") String phone);
}
