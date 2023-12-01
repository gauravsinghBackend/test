package com.example.LoginPage.ContactUs.Controller;

import com.example.LoginPage.ContactUs.Dto.ContactDto;
import com.example.LoginPage.ContactUs.Dto.PostContactDto;
import com.example.LoginPage.ContactUs.Entity.ContactEntity;
import com.example.LoginPage.ContactUs.Entity.PostContactEntity;
import com.example.LoginPage.ContactUs.ServiceImpl.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    ContactServiceImpl contactService;

    @Autowired
    PostContactEntity postContactEntity;


    @GetMapping("/get-contact")
    public ResponseEntity<List<ContactEntity>>GetContact(){
        return contactService.getContact();
    }


    @PostMapping("/post-contact")
    public ResponseEntity<String> PostContact(@RequestBody ContactDto contactDto){
        return contactService.postContact(contactDto);
    }

    @PostMapping("/contact-us/post")
    public ResponseEntity<PostContactEntity> PostLoginContact(@RequestBody PostContactDto postContactDto){
        return contactService.postLoginContactUs(postContactDto);
    }
}
