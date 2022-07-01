package com.example.testapp.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.testapp.data.api.ApiUtils
import com.example.testapp.data.database.FavoritesPlayers
import com.example.testapp.data.database.FavoritesPlayersDao
import com.example.testapp.data.database.FavoritesPlayersDatabase
import com.example.testapp.data.model.PlayersListModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PlayerInfoRepository(private val favDao: FavoritesPlayersDao ) {
    companion object {
        private const val TAG = "Error: class -> PlayerInfoRepository: "
    }

    fun checkIfFavorites(id:Int): Flow<FavoritesPlayers?> {
        return favDao.loadSingle(id)
    }


    @WorkerThread
    suspend fun insert(favPlayer: FavoritesPlayers) {
        favDao.insert(favPlayer)
    }
    suspend fun getPlayerInfo(playerId: Int): Response<PlayersListModel.PlayerInfo>? {
        return try {
            ApiUtils.api.playerFullInfo(playerId)
        } catch (e: Throwable) {
            Log.e(TAG, e.localizedMessage)
            null
        }
    }


}