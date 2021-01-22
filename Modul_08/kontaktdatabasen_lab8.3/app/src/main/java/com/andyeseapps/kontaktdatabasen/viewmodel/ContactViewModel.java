package com.andyeseapps.kontaktdatabasen.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.andyeseapps.kontaktdatabasen.entities.Contact;
import com.andyeseapps.kontaktdatabasen.repository.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository mRepository;

    private LiveData<List<Contact>> mAllContacts;

    public ContactViewModel (Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    public void insert(Contact contact) {
        mRepository.insert(contact);
    }

    public void deleteContactWithID(int contactId) { mRepository.deleteContactWithID(contactId);}

    public LiveData<List<Contact>> getContactsFromCategory(int categoryId) {
        return mRepository.getAllContactsFromCategory(categoryId);
    }

    public void deleteContactByCategoryID(int categoryID) {
        mRepository.deleteContactByCategoryID(categoryID);
    }
}
