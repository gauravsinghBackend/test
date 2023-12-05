package com.example.LoginPage.InvitePartner;

import com.example.LoginPage.Models.User;
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
    public static  String invitePrt = "abc";
}
