package com.andyeseapps.kontaktdatabasen.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.andyeseapps.kontaktdatabasen.dao.CategoryDAO;
import com.andyeseapps.kontaktdatabasen.database.CategoryRoomDatabase;
import com.andyeseapps.kontaktdatabasen.entities.Category;

import java.util.List;

public class CategoryRepository {

    private CategoryDAO mCategoryDao;
    private LiveData<List<Category>> mAllCategories;

    public CategoryRepository(Application application) {
        CategoryRoomDatabase db = CategoryRoomDatabase.getDatabase(application);
        mCategoryDao = db.categoryDAO();
        mAllCategories = mCategoryDao.getCategoriesByCategoryId();
    }

    public LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    public void insert(final Category category) {
        CategoryRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCategoryDao.insert(category);
        });
    }

    public void delete(final Category category) {
        CategoryRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCategoryDao.deleteCategoryWithID(category.getCategoryID());
        });
    }

    public void deleteCategoryWithID(final int categoryID) {
        CategoryRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCategoryDao.deleteCategoryWithID(categoryID);
        });
    }
}