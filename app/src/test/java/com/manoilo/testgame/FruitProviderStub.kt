package com.manoilo.testgame

import com.manoilo.testgame.model.Fruit
import com.manoilo.testgame.provider.IFruitProvider

class FruitProviderStub(private val fruits: List<Fruit>) : IFruitProvider {
    var counter = 0
    override fun getRandomFruit(): Fruit {
        counter++
        if (counter == fruits.size)
            counter = 0
        return fruits[counter]
    }
}