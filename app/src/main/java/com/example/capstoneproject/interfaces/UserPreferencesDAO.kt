package com.example.capstoneproject.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.capstoneproject.dataobjects.UserPreferences

@Dao
interface UserPreferencesDAO {
    @Insert
    fun insert(userPreferences: UserPreferences)

    @Update
    fun update(userPreferences: UserPreferences)

    @Delete
    fun delete(userPreferences: UserPreferences)

    @Query("SELECT * FROM userPreferenceTable WHERE userId = :userId")
    fun get(userId: Long): LiveData<UserPreferences>
}