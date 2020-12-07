package com.manoilo.testgame.model

enum class PayTable(val fruitsType: Fruit, val multiplier: Int) {
    TRIPLE_ORANGE(Fruit.ORANGE, 250),
    TRIPLE_STRAWBERRY(Fruit.STRAWBERRY, 100),
    TRIPLE_GRAPES(Fruit.GRAPES, 50),
    TRIPLE_COCONUT(Fruit.COCONUT, 20),
    TRIPLE_PEACH(Fruit.PEACH, 10),
    TRIPLE_BANANA(Fruit.BANANA, 5),
    TRIPLE_KIWI(Fruit.KIWI, 2)
}