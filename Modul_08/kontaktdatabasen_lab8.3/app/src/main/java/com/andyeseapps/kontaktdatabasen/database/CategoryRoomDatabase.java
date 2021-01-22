package com.andyeseapps.kontaktdatabasen.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.andyeseapps.kontaktdatabasen.dao.CategoryDAO;
import com.andyeseapps.kontaktdatabasen.entities.Category;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class CategoryRoomDatabase extends RoomDatabase {
    public static final String INITIAL_CATEGORY_DESCRIPTION = "Alle";

    public abstract CategoryDAO categoryDAO();

    private static volatile CategoryRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CategoryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CategoryRoomDatabase.class, "category_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                CategoryDAO categoryDAO = INSTANCE.categoryDAO();

                if (categoryDAO.getAllCategoriesCount() <= 0) {
                    categoryDAO.insert(new Category(INITIAL_CATEGORY_DESCRIPTION));
                    categoryDAO.insert(new Category("Venner"));
                    categoryDAO.insert(new Category("Kollegaers"));
                    categoryDAO.insert(new Category("Familie"));
                }
            });
        }
    };
}