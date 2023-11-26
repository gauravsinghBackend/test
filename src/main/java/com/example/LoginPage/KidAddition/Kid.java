package com.example.LoginPage.KidAddition;

import com.example.LoginPage.Models.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Kid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profilePicture;
    private String name;
    private String dob;
    @ManyToOne
    private User user;
    private boolean deleted = false;
    private KidGender kidGender;
    // false means Kid is added make it true if delete request Comes
    //Make the query of kids by parent return only false marked KIDS
}


// gender: string,
//         profilePicture: string,
//         name: string,
//         dob: string
