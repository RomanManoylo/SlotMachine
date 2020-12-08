package com.manoilo.testgame.provider

import com.manoilo.testgame.model.Fruit
import com.manoilo.testgame.model.PayTable
import kotlin.random.Random

class FruitProvider : IFruitProvider {
    private val probabilitySum = calculateProbabilitySum()

    override fun getRandomFruit(): Fruit =
        Fruit.values()[Fruit.values().indices.random()]

    override fun getWinItem(): Fruit {
        val probability = Random.nextInt(probabilitySum)
        var searchSumProbability = 0

        PayTable.values().forEach {
            searchSumProbability += it.probability
            if (searchSumProbability > probability)
                return it.fruitsType
        }

        return Fruit.values()[0]
    }

    private fun calculateProbabilitySum(): Int {
        var sum = 0
        PayTable.values().forEach { sum += it.probability }
        return sum
    }

}