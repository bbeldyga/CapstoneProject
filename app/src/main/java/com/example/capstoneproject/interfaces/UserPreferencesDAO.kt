package com.example.capstoneproject.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.capstoneproject.dataobjects.UserPreferences
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferencesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userPreferences: UserPreferences)

    @Update
    suspend fun update(userPreferences: UserPreferences)

    @Delete
    suspend fun delete(userPreferences: UserPreferences)

    @Query("SELECT * FROM userPreferenceTable WHERE email = :email")
    fun get(email: String): LiveData<UserPreferences>
}