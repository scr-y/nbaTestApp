package com.example.testapp.presentation.ui.fragments.playerPage

import androidx.lifecycle.*
import com.example.testapp.data.database.FavoritesPlayers
import com.example.testapp.data.database.FavoritesPlayersDao
import com.example.testapp.data.model.PlayersListModel
import com.example.testapp.data.repository.PlayerInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PlayerInfoViewModel(private val playerRepository: PlayerInfoRepository) : ViewModel() {

    fun getPlayerInfo(playerId: Int): LiveData<Response<PlayersListModel.PlayerInfo>> {
        val result = MutableLiveData<Response<PlayersListModel.PlayerInfo>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(playerRepository.getPlayerInfo(playerId))
        }
        return result
    }

    fun insert(favPlayer: FavoritesPlayers) = viewModelScope.launch {
        playerRepository.insert(favPlayer)
    }

    fun checkIfFavorites(id: Int): LiveData<FavoritesPlayers?> {
        return playerRepository.checkIfFavorites(id).asLiveData()
    }
}
