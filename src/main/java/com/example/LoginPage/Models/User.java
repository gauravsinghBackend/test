package com.example.LoginPage.Models;

import com.example.LoginPage.OnBoarding.PregnantChildEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(nullable = false)
    private String name;
//    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
//    @Column(nullable = false)
//    private String password;
//    @Column
//    private boolean isPasswordLess;
    private ParentRole parentrole;
    private String date;
    private PregnantChildEnum pregnantorchild;
    private String dueDate;
    private boolean isFirstPregnancy;
    private boolean haveKids;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<>();

    public User(String name, String email, List<Role> roles) {
        this.name = name;
        this.email = email;
//        this.password = password;
        this.roles = roles;
    }
}