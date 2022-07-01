package com.example.testapp.data.api

import com.example.testapp.data.model.PlayersListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("players")
    suspend fun playersList(): Response<PlayersListModel>

    @GET("players")
    suspend fun searchedPlayersList(@Query("search") name: String): Response<PlayersListModel>

    @GET("players/{id}")
    suspend fun playerFullInfo(@Path("id") id: Int): Response<PlayersListModel.PlayerInfo>
}

object ApiUtils {
    val api: ApiService get() = RetrofitClient.getClient("https://www.balldontlie.io/api/v1/")
}