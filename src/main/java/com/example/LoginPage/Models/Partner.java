package com.example.LoginPage.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invitepartner")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @Column(nullable = false)
    private String name;
    private String partnerPhone;
}
