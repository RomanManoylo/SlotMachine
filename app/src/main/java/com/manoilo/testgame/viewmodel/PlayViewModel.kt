package com.manoilo.testgame.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoilo.testgame.model.Fruit
import com.manoilo.testgame.model.PayTable
import com.manoilo.testgame.provider.IFruitProvider
import com.manoilo.testgame.util.DEFAULT_BET_VALUE
import com.manoilo.testgame.util.DEFAULT_CREDIT_VALUE
import com.manoilo.testgame.util.MAX_BET_VALUE
import kotlinx.coroutines.*

class PlayViewModel(private val fruitsProvider: IFruitProvider) : ViewModel() {

    val credit by lazy { MutableLiveData<Int>() }
    val betSize by lazy { MutableLiveData<Int>() }
    val winSize by lazy { MutableLiveData<Int>() }

    val fistWheel by lazy { MutableLiveData<Fruit>() }
    val secondWheel by lazy { MutableLiveData<Fruit>() }
    val thirdWheel by lazy { MutableLiveData<Fruit>() }

    val isSpinButtonEnable by lazy { MutableLiveData<Boolean>() }

    init {
        credit.value = DEFAULT_CREDIT_VALUE
        betSize.value = DEFAULT_BET_VALUE
        winSize.value = 0
    }

    fun setMaxBet() {
        betSize.value = MAX_BET_VALUE
    }

    fun increaseBet() {
        betSize.value =
            if (betSize.value != MAX_BET_VALUE) betSize.value?.plus(DEFAULT_BET_VALUE) else DEFAULT_BET_VALUE
        checkEnoughMoneyForSpin()
    }

    fun chargeMoney() {
        credit.value = credit.value!! - betSize.value!!
    }

    fun spin() {
        viewModelScope.launch {
            chargeMoney()
            spinAllWheels()
            checkEnoughMoneyForSpin()
        }
    }

    private fun checkEnoughMoneyForSpin() {
        isSpinButtonEnable.value = credit.value!! >= betSize.value!!
    }

    private suspend fun spinAllWheels() {
        viewModelScope.launch {
            val deferredList = listOf(
                async {
                    spinWheel(this, fistWheel)
                },
                async {
                    spinWheel(this, secondWheel)
                },
                async {
                    spinWheel(this, thirdWheel)
                })
            deferredList.awaitAll()
            updateWinPrize()
        }
    }

    private fun spinWheel(scope: CoroutineScope, liveData: MutableLiveData<Fruit>): Job {
        return scope.launch {
            repeat(10) {
                liveData.value = fruitsProvider.getRandomFruit()
                delay(100L)
            }
        }
    }

    /**
     * немного изменил логику побед, чтобы то что выиграл плюсовалось к общей сумме,
     * так как после N-того количества спинов игра заканчивалась
     */

    private fun updateWinPrize() {
        if (fistWheel.value == secondWheel.value && secondWheel.value == thirdWheel.value) {
            PayTable.values().forEach {
                if (it.fruitsType == fistWheel.value) {
                    val winPrize = betSize.value?.times(it.multiplier)
                    winSize.value = winPrize
                    credit.value = credit.value!! + winPrize!!
                }
            }
        } else winSize.value = 0
    }

}