package com.Contact_US.Contact.Controller;

import com.Contact_US.Contact.Dto.ContactDto;
import com.Contact_US.Contact.Entity.ContactEntity;
import com.Contact_US.Contact.ServiceImpl.ContactServiceImpl;
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
