package com.manoilo.testgame

import CoroutinesTestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manoilo.testgame.model.Fruit
import com.manoilo.testgame.provider.FruitProvider
import com.manoilo.testgame.util.DEFAULT_BET_VALUE
import com.manoilo.testgame.util.DEFAULT_CREDIT_VALUE
import com.manoilo.testgame.util.MAX_BET_VALUE
import com.manoilo.testgame.viewmodel.PlayViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    @Test
    fun setBetMaxValue() {
        val viewModel = PlayViewModel(FruitProvider())
        viewModel.setMaxBet()
        assertEquals(viewModel.betSize.value, MAX_BET_VALUE)
    }

    @Test
    fun increaseBetValue() {
        val viewModel = PlayViewModel(FruitProvider())
        val betValue = DEFAULT_BET_VALUE
        viewModel.betSize.value = betValue
        viewModel.increaseBet()
        assertEquals(viewModel.betSize.value, betValue + DEFAULT_BET_VALUE)
    }

    @Test
    fun setBetValueToMinAfterBetValueIsMax() {
        val viewModel = PlayViewModel(FruitProvider())
        val betValue = MAX_BET_VALUE
        viewModel.betSize.value = betValue
        viewModel.increaseBet()
        assertEquals(viewModel.betSize.value, DEFAULT_BET_VALUE)
    }

    @Test
    fun chargeMoney() {
        val viewModel = PlayViewModel(FruitProvider())
        viewModel.betSize.value = DEFAULT_BET_VALUE
        viewModel.credit.value = DEFAULT_CREDIT_VALUE
        viewModel.chargeMoney()
        assertEquals(viewModel.credit.value, DEFAULT_CREDIT_VALUE - DEFAULT_BET_VALUE)
    }

    @Test
    fun notEnoughMoneyForSpin() {
        val viewModel = PlayViewModel(FruitProvider())
        viewModel.credit.value = 0
        viewModel.betSize.value = DEFAULT_BET_VALUE
        viewModel.spin()
        assertEquals(viewModel.isSpinButtonEnable.value, false)
    }

    @Test
    fun checkEnoughMoneyForSpin() {
        val viewModel = PlayViewModel(FruitProvider())
        viewModel.credit.value = DEFAULT_CREDIT_VALUE
        viewModel.betSize.value = DEFAULT_BET_VALUE
        viewModel.spin()
        assertEquals(viewModel.isSpinButtonEnable.value, true)
    }

    @Test
    fun checkWheelsImageUpdates() {
        val viewModel = PlayViewModel(FruitProvider())
        viewModel.spin()
        assertNotNull(viewModel.fistWheel.value)
        assertNotNull(viewModel.secondWheel.value)
        assertNotNull(viewModel.thirdWheel.value)
    }

    @Test
    fun updateWinPrizeSuccessWhenTreeWheelsTheSame() {
        val viewModel =
            PlayViewModel(FruitProviderStub(listOf(Fruit.BANANA, Fruit.BANANA, Fruit.BANANA)))
        viewModel.betSize.value = DEFAULT_BET_VALUE
        viewModel.credit.value = DEFAULT_CREDIT_VALUE
        mainCoroutineRule.testDispatcher.runBlockingTest {
            viewModel.spin()
        }
        assertNotEquals(viewModel.winSize.value, 0)
    }

    @Test
    fun updateWinPrizeFailWhenTwoWheelsTheSame() {
        val viewModel =
            PlayViewModel(FruitProviderStub(listOf(Fruit.COCONUT, Fruit.COCONUT, Fruit.KIWI)))
        viewModel.betSize.value = DEFAULT_BET_VALUE
        viewModel.credit.value = DEFAULT_CREDIT_VALUE
        mainCoroutineRule.testDispatcher.runBlockingTest {
            viewModel.spin()
        }
        assertEquals(viewModel.winSize.value, 0)
    }

    @Test
    fun updateWinPrizeFailWhenTreeWheelsTheDifferent() {
        val viewModel =
            PlayViewModel(FruitProviderStub(listOf(Fruit.BANANA, Fruit.COCONUT, Fruit.KIWI)))
        viewModel.betSize.value = DEFAULT_BET_VALUE
        viewModel.credit.value = DEFAULT_CREDIT_VALUE
        mainCoroutineRule.testDispatcher.runBlockingTest {
            viewModel.spin()
        }
        assertEquals(viewModel.winSize.value, 0)
    }

}