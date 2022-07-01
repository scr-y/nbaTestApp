package com.example.testapp

import android.app.Application
import com.example.testapp.data.database.FavoritesPlayersDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application(){
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { FavoritesPlayersDatabase.getDatabase(this, applicationScope) }
    val favDao by lazy { database.favoritesPlayersDao() }
}