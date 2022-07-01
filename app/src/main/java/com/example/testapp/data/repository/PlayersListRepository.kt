package com.example.testapp.data.repository

import android.util.Log
import com.example.testapp.data.api.ApiUtils
import com.example.testapp.data.model.PlayersListModel
import retrofit2.Response

class PlayersListRepository {
    companion object {
        private const val TAG = "Error: class -> PlayersListRepository: "
    }

    suspend fun getPlayersList(): Response<PlayersListModel>? {
        return try {
            ApiUtils.api.playersList()
        } catch (e: Throwable) {
            Log.e(TAG, e.localizedMessage)
            null
        }
    }
}