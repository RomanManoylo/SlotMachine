package com.manoilo.testgame.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manoilo.testgame.viewmodel.PlayViewModel

@Suppress("UNCHECKED_CAST")
class PlayViewModelFactory(private val fruitsProvider: FruitProvider) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        PlayViewModel(fruitsProvider) as T
}