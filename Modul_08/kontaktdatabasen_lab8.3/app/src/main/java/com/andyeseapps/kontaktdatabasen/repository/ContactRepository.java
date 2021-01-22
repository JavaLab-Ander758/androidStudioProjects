package com.andyeseapps.kontaktdatabasen.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.andyeseapps.kontaktdatabasen.dao.ContactDAO;
import com.andyeseapps.kontaktdatabasen.database.ContactRoomDatabase;
import com.andyeseapps.kontaktdatabasen.entities.Contact;

import java.util.List;

public class ContactRepository {

    private ContactDAO mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    public ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        mContactDao = db.contactDAO();
        mAllContacts = mContactDao.getAlphabetizedContactsByLastNameAsc();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    public void insert(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> mContactDao.insert(contact));
    }

    public void deleteContactWithID(int contactID) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> mContactDao.deleteContactWithID(contactID));
    }

    public LiveData<List<Contact>> getAllContactsFromCategory(int categoryId) {
        return  mContactDao.getContactsWithGivenCategory(categoryId);
    }

    public void deleteContactByCategoryID(int categoryID) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> mContactDao.deleteContactByCategoryID(categoryID));
    }
}
