package com.example.LoginPage.KidAddition;

import com.example.LoginPage.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class KidService {
    @Autowired
    private KidRepository kidRepository;

    public Kid addKid(User user, String name, String dob, String profilePicture,String gender)
    {
        Kid kid=new Kid();
        kid.setName(name);
        kid.setDob(dob);
        kid.setProfilePicture(profilePicture);
        kid.setUser(user);
        KidGender genderEnum = KidGender.fromString(gender);
        kid.setKidGender(genderEnum);
        Kid savedKid=kidRepository.save(kid);
        //Kid can be Dublicate Entry

        return kid;
    }
}
