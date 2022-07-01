package com.example.testapp.data.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_players_table")
data class FavoritesPlayers(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)
