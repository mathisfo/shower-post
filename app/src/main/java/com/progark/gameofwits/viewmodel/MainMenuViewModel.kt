package com.progark.gameofwits.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import storage.Repository
import storage.Storage

class MainMenuViewModel(private val repository: Repository = Storage.getInstance()) : ViewModel() {
    // Input fields
    val usernameInput = ObservableField<String>()
    val pinInput = ObservableField<String>()

    val validOrError = MutableLiveData(Pair(false, ""))
    val pinCode = 123


    fun registerUser() {
        val nameVal = usernameInput.get()
        val pinVal = pinInput.get()
        if (nameVal.isNullOrEmpty()) {
            validOrError.postValue(Pair(false, "The username is invalid"))
            return
        }
        if (pinVal.isNullOrEmpty() && pinVal?.toInt() != pinCode) {
            validOrError.postValue(Pair(false, "The pincode is invalid"))
            return
        }
        viewModelScope.launch {
            repository.createUser(nameVal)
            validOrError.postValue(Pair(true, ""))
        }
    }
}