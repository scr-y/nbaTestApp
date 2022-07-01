package com.example.testapp.presentation.ui.fragments.searchPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.model.PlayersListModel
import com.example.testapp.data.repository.PlayersListRepository
import com.example.testapp.data.repository.SearchPlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchPlayerViewModel : ViewModel() {
    private val playerRepository = SearchPlayerRepository()

    fun getSearchedPlayersList(name: String): LiveData<Response<PlayersListModel>> {
        val result = MutableLiveData<Response<PlayersListModel>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(playerRepository.getSearchedPlayersList(name))
        }
        return result
    }}