package com.Contact_US.Contact.Service;

import com.Contact_US.Contact.Dao.ContactDao;
import com.Contact_US.Contact.Dto.ContactDto;
import com.Contact_US.Contact.Entity.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ContactService {

ResponseEntity<List<ContactEntity>>getContact();
ResponseEntity<String>postContact(ContactDto contactDto);
}
