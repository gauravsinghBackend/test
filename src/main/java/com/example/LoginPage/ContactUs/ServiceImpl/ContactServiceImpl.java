package com.Contact_US.Contact.ServiceImpl;

import com.Contact_US.Contact.Dto.ContactDto;
import com.Contact_US.Contact.Entity.ContactEntity;
import com.Contact_US.Contact.Respository.ContactRepository;
//import com.Contact_US.Contact.Service.ContactService;
import com.Contact_US.Contact.Service.ContactService;
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
}
