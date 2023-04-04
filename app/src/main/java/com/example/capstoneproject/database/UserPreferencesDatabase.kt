package com.example.capstoneproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.capstoneproject.dataobjects.UserPreferences
import com.example.capstoneproject.interfaces.UserPreferencesDAO

@Database(entities = [UserPreferences::class], version = 1, exportSchema = false)
abstract class UserPreferencesDatabase: RoomDatabase() {
    abstract val userPreferencesDAO: UserPreferencesDAO

    companion object{
        @Volatile
        private var INSTANCE: UserPreferencesDatabase? = null

        fun getInstance(context: Context): UserPreferencesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        UserPreferencesDatabase::class.java,
                        "userPreferencesDatabase").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}