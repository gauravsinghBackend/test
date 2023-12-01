package com.example.LoginPage.ContactUs.Service;


import com.example.LoginPage.ContactUs.Dto.ContactDto;
import com.example.LoginPage.ContactUs.Dto.PostContactDto;
import com.example.LoginPage.ContactUs.Entity.ContactEntity;
import com.example.LoginPage.ContactUs.Entity.PostContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ContactService {

    ResponseEntity<List<ContactEntity>>getContact();
    ResponseEntity<PostContactEntity>postLoginContactUs(PostContactDto postContactDto);
}
