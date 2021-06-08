package com.hazir.Hazirlaniyor.api.controllers;

import com.hazir.Hazirlaniyor.business.concretes.ContactManager;
import com.hazir.Hazirlaniyor.entity.concretes.Contact;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/contact")
@AllArgsConstructor
public class ContactController {
    private final ContactManager contactManager;
@GetMapping
    public List<Contact> getAll(){
    return this.contactManager.getContacts();
}
@GetMapping(path = "{firstName}")
    public List<Contact> findContactAccountByName(@PathVariable("firstName")String firstName){
    return this.contactManager.findContactByName(firstName);
}
@PostMapping
    public void addContact(@RequestBody Contact contact){
      this.contactManager.addNewContact(contact);
}
@DeleteMapping(path = "{id}")
    public void deleteContactById(@PathVariable("Id")Long Id){
    this.contactManager.deleteContactById(Id);
}

}
