package com.andyeseapps.kontaktdatabasen.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.andyeseapps.kontaktdatabasen.entities.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    // Allowing the insert of the same category multiple times by passing a conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Query("DELETE FROM category_table")
    void deleteAllCategories();

    @Query("SELECT * FROM category_table ORDER BY categoryID ASC")
    LiveData<List<Category>> getCategoriesByCategoryId();

    @Query("SELECT COUNT(categoryID) FROM category_table")
    int getAllCategoriesCount();

    @Query("DELETE FROM category_table WHERE categoryID = :categoryID")
    void deleteCategoryWithID(int categoryID);
}