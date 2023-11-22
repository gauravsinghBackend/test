package com.example.LoginPage.Models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "tokenauthentification")
public class UserAuthentificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String token;
    private Date expirationTime;
    private boolean status;

    public boolean isExpired() {
        // Check if expirationTime is set and if it's before the current date and time
        return expirationTime != null && expirationTime.before(new Date());
    }
}
