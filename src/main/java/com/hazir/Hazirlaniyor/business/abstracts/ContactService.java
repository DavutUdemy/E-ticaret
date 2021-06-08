package com.hazir.Hazirlaniyor.business.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getContacts(); //ADMIN PAGE
    List<Contact> findContactByName(String firstName);
    void addNewContact(Contact contact);
    void deleteContactById(Long Id);



}
