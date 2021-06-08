package com.hazir.Hazirlaniyor.business.concretes;

import com.hazir.Hazirlaniyor.business.abstracts.ContactService;
import com.hazir.Hazirlaniyor.dataAccess.abstracts.ContactDao;
import com.hazir.Hazirlaniyor.entity.concretes.Contact;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactManager  implements ContactService {
private final ContactDao contactDao;
    @Override
    public List<Contact> getContacts() {
        return this.contactDao.findAll();
    }

    @Override
    public List<Contact> findContactByName(String firstName) {
        return this.contactDao.findContactByFirstName(firstName);
    }

    @Override
    public void addNewContact(Contact contact) {
 this.contactDao.save(contact);
    }

    @Override
    public void deleteContactById(Long Id) {
   this.contactDao.deleteById(Id);
    }
}
