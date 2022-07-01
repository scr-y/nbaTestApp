package com.example.testapp.presentation.ui.fragments.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.database.FavoritesPlayers
import com.example.testapp.data.repository.FavoritesPlayersRepository
import kotlinx.coroutines.launch

class FavoritesPlayersViewModel(private val favoritesPlayerRepository: FavoritesPlayersRepository) : ViewModel() {
    val allWords: LiveData<List<FavoritesPlayers>> = favoritesPlayerRepository.allFavoritesPlayers.asLiveData()

    fun delete(favPlayer: FavoritesPlayers) = viewModelScope.launch {
        favoritesPlayerRepository.delete(favPlayer)
    }
}