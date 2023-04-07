package com.example.capstoneproject.dataobjects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userPreferenceTable")
data class UserPreferences (
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = "General")
    val generalPreference: Float,
    @ColumnInfo(name = "Technology")
    val technologyPreference: Float,
    @ColumnInfo(name = "Entertainment")
    val entertainmentPreference: Float,
    @ColumnInfo(name = "Sports")
    val sportsPreference: Float,
    @ColumnInfo(name = "Business")
    val businessPreference: Float,
    @ColumnInfo(name = "Health")
    val healthPreference: Float,
    @ColumnInfo(name = "Science")
    val sciencePreference: Float
)