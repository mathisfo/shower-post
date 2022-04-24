package com.progark.wordwar.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.progark.wordwar.model.Lobby
import com.progark.wordwar.model.Player
import kotlinx.coroutines.launch
import storage.Repository
import storage.Storage

class MainMenuViewModel(private val repository: Repository = Storage.getInstance()) : ViewModel() {
    // Input fields
    val usernameInput = ObservableField<String>()
    val pinInput = ObservableField<String>()

    val _activeLobby = MutableLiveData<Lobby>()
    val activeLobby: LiveData<Lobby> = _activeLobby

    fun setActiveLobby(lobby: Lobby) {
        _activeLobby.postValue(lobby)
    }
}