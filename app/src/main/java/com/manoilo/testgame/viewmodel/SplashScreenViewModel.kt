package com.manoilo.testgame.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashScreenViewModel : ViewModel() {

    val progressLevel by lazy { MutableLiveData<Int>() }

    init {
        progressLevel.value = 0
    }

    fun loadProgressBar() = runBlocking {
        viewModelScope.launch {
            repeat(100) {
                progressLevel.value = progressLevel.value?.plus(1)
                delay(20L)
            }
        }
    }

}