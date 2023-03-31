package com.example.capstoneproject.dataobjects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userPreferenceTable")  //Will have to update version number after this changes
data class UserPreferences (
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = "General")
    val generalPreference: Int,
    @ColumnInfo(name = "Technology")
    val technologyPreference: Int,
    @ColumnInfo(name = "Entertainment")
    val entertainmentPreference: Int,
    @ColumnInfo(name = "Sports")
    val sportsPreference: Int,
    @ColumnInfo(name = "Business")
    val businessPreference: Int,
    @ColumnInfo(name = "Health")
    val healthPreference: Int,
    @ColumnInfo(name = "Science")
    val sciencePreference: Int
)