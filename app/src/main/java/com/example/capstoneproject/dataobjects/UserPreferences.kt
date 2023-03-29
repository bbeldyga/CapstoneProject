package com.example.capstoneproject.dataobjects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userPreferenceTable")  //Will have to update version number after this changes
data class UserPreferences (
    @PrimaryKey
    val email: String = "",
    @ColumnInfo(name = "General")
    val generalPreference: Int = 3,
    @ColumnInfo(name = "Technology")
    val technologyPreference: Int = 3,
    @ColumnInfo(name = "Entertainment")
    val entertainmentPreference: Int = 3,
    @ColumnInfo(name = "Sports")
    val sportsPreference: Int = 3,
    @ColumnInfo(name = "Business")
    val businessPreference: Int = 3,
    @ColumnInfo(name = "Health")
    val healthPreference: Int = 3,
    @ColumnInfo(name = "Science")
    val sciencePreference: Int = 3
)