package com.example.testapp.data.repository

import android.util.Log
import com.example.testapp.data.api.ApiUtils
import com.example.testapp.data.model.PlayersListModel
import retrofit2.Response

class SearchPlayerRepository {
    companion object {
        private const val TAG = "Error: class -> SearchPlayerRepository: "
    }

    suspend fun getSearchedPlayersList(name: String): Response<PlayersListModel>? {
        return try {
            ApiUtils.api.searchedPlayersList(name)
        } catch (e: Throwable) {
            Log.e(TAG, e.localizedMessage)
            null
        }
    }
}