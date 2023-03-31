package com.example.capstoneproject.interfaces

import androidx.room.*
import com.example.capstoneproject.dataobjects.UserPreferences

@Dao
interface UserPreferencesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userPreferences: UserPreferences)

    @Update
    suspend fun update(userPreferences: UserPreferences)

    @Delete
    suspend fun delete(userPreferences: UserPreferences)

    @Query("SELECT * FROM userPreferenceTable WHERE email = :email")
    suspend fun get(email: String): UserPreferences
}