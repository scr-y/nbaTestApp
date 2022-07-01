package com.example.testapp.presentation.ui.fragments.playersList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.model.PlayersListModel
import com.example.testapp.data.repository.PlayersListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PlayersListViewModel : ViewModel() {
    private val playerRepository = PlayersListRepository()

    fun getPlayersList(): LiveData<Response<PlayersListModel>> {
        val result = MutableLiveData<Response<PlayersListModel>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(playerRepository.getPlayersList())
        }
        return result
    }

}