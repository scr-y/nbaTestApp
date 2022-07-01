package com.example.testapp.data.repository

import androidx.annotation.WorkerThread
import com.example.testapp.data.database.FavoritesPlayers
import com.example.testapp.data.database.FavoritesPlayersDao
import kotlinx.coroutines.flow.Flow

class FavoritesPlayersRepository(private val favDao: FavoritesPlayersDao ) {
    companion object {
        private const val TAG = "Error: class -> FavoritesPlayersRepository: "
    }
    val allFavoritesPlayers: Flow<List<FavoritesPlayers>> = favDao.getAlphabetizedPlayers()

    @WorkerThread
    suspend fun delete(favPlayer: FavoritesPlayers) {
        favDao.delete(favPlayer)
    }
}