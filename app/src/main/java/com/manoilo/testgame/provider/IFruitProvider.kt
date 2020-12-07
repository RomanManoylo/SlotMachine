package com.manoilo.testgame.provider

import com.manoilo.testgame.model.Fruit

interface IFruitProvider {
    fun getRandomFruit(): Fruit
}