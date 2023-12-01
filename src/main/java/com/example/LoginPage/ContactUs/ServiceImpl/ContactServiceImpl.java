package com.example.LoginPage.ContactUs.ServiceImpl;


import com.example.LoginPage.ContactUs.Dto.ContactDto;
import com.example.LoginPage.ContactUs.Dto.PostContactDto;
import com.example.LoginPage.ContactUs.Entity.ContactEntity;
import com.example.LoginPage.ContactUs.Entity.PostContactEntity;
import com.example.LoginPage.ContactUs.Respository.ContactRepository;
import com.example.LoginPage.ContactUs.Respository.PostContactRepository;
import com.example.LoginPage.ContactUs.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//public class ContactServiceImpl implements ContactService {
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PostContactRepository postContactRepository;


    @Override
    public ResponseEntity<List<ContactEntity>> getContact() {
        List<ContactEntity> AllContact = contactRepository.findAll();
        return new ResponseEntity<List<ContactEntity>>(AllContact, HttpStatus.OK) ;
    }

    public ResponseEntity<String> postContact(ContactDto contactDto) {
    ContactEntity contactEntity = new ContactEntity();
        contactEntity.setName(contactDto.getName());
        contactEntity.setDesignation(contactDto.getDesignation());
        contactEntity.setOrganisation(contactDto.getOrganisation());
        contactEntity.setEmail(contactDto.getEmail());
        contactEntity.setPhoneNo(contactDto.getPhoneNo());
        contactRepository.save(contactEntity);
        return new ResponseEntity<String>("User Saved Successfully", HttpStatus.OK);
    }

    public ResponseEntity <PostContactEntity> postLoginContactUs(PostContactDto postContactDto) {
        PostContactEntity postContactEntity = new PostContactEntity();
        postContactEntity.setName(postContactDto.getName());
        postContactEntity.setEmail(postContactDto.getEmail());
        postContactEntity.setPhoneNumber(postContactDto.getPhoneNumber());
        postContactEntity.setMessage(postContactDto.getMessage());
        postContactRepository.save(postContactEntity);
        return new ResponseEntity<PostContactEntity>(postContactEntity, HttpStatus.OK);
    }
}
