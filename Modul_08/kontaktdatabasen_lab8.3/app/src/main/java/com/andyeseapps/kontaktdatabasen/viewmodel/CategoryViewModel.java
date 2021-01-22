package com.andyeseapps.kontaktdatabasen.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.andyeseapps.kontaktdatabasen.entities.Category;
import com.andyeseapps.kontaktdatabasen.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository mRepository;

    private LiveData<List<Category>> mAllCategories;

    public CategoryViewModel (Application application) {
        super(application);
        mRepository = new CategoryRepository(application);
        mAllCategories = mRepository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    public void insert(Category category) {
        mRepository.insert(category);
    }

    public void delete(Category category) {
        mRepository.delete(category);
    }

    public void deleteCategoryWithID(int categoryID) {
        mRepository.deleteCategoryWithID(categoryID);
    }
}
