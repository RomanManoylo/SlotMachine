package com.manoilo.testgame.provider

import com.manoilo.testgame.model.Fruit

class FruitProvider : IFruitProvider {

    override fun getRandomFruit(): Fruit =
        Fruit.values()[Fruit.values().indices.random()]

}