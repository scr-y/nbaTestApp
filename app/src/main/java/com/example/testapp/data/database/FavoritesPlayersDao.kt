package com.example.testapp.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesPlayersDao {
    @Query("SELECT * FROM favorites_players_table ORDER BY name ASC")
    fun getAlphabetizedPlayers(): Flow<List<FavoritesPlayers>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: FavoritesPlayers)

    @Delete()
    fun delete(word: FavoritesPlayers)

    @Query("SELECT * FROM favorites_players_table WHERE id=:id ")
    fun loadSingle(id: Int): Flow<FavoritesPlayers>

}