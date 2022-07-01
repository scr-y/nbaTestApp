package com.example.testapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.testapp.data.model.PlayersListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [FavoritesPlayers::class],  version = 2)
abstract class FavoritesPlayersDatabase : RoomDatabase() {

    abstract fun favoritesPlayersDao(): FavoritesPlayersDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesPlayersDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FavoritesPlayersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesPlayersDatabase::class.java,
                    "favorites_players_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}
