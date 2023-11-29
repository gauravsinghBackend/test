package com.example.LoginPage.ContactUs.Service;


import com.example.LoginPage.ContactUs.Dto.ContactDto;
import com.example.LoginPage.ContactUs.Entity.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ContactService {

ResponseEntity<List<ContactEntity>>getContact();
ResponseEntity<String>postContact(ContactDto contactDto);
}
