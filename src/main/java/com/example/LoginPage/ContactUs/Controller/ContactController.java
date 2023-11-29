package com.example.LoginPage.ContactUs.Controller;

import com.example.LoginPage.ContactUs.Dto.ContactDto;
import com.example.LoginPage.ContactUs.Entity.ContactEntity;
import com.example.LoginPage.ContactUs.ServiceImpl.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    ContactServiceImpl contactService;


    @GetMapping("/get-contact")
    public ResponseEntity<List<ContactEntity>>GetContact(){
        return contactService.getContact();
    }


    @PostMapping("/post-contact")
    public ResponseEntity<String> PostContact(@RequestBody ContactDto contactDto){
        return contactService.postContact(contactDto);
    }
}
