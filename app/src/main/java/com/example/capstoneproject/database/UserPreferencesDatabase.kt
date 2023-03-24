package com.example.capstoneproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.capstoneproject.dataobjects.UserPreferences
import com.example.capstoneproject.interfaces.UserPreferencesDAO

@Database(entities = [UserPreferences::class], version = 1, exportSchema = false)
abstract class UserPreferencesDatabase: RoomDatabase() {
    abstract val userPreferencesDAO: UserPreferencesDAO
}