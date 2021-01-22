package com.andyeseapps.kontaktdatabasen.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.andyeseapps.kontaktdatabasen.entities.Contact;

import java.util.List;

@Dao
public interface ContactDAO {

    // Allowing the insert of the same contact multiple times by passing a conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAllContacts();

    @Query("SELECT * FROM contact_table ORDER BY last_name ASC")
    LiveData<List<Contact>> getAlphabetizedContactsByLastNameAsc();

    @Query("DELETE FROM contact_table WHERE contactID = :contactID")
    void deleteContactWithID(int contactID);

    @Query("SELECT * FROM contact_table WHERE category_categoryID = :categoryId ORDER BY last_name ASC")
    LiveData<List<Contact>> getContactsWithGivenCategory(int categoryId);

    @Query("DELETE FROM contact_table WHERE category_categoryID = :categoryID")
    void deleteContactByCategoryID(int categoryID);
}