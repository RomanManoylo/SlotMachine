package com.manoilo.testgame.model

enum class PayTable(val fruitsType: Fruit, val multiplier: Int, val probability: Int) {
    TRIPLE_ORANGE(Fruit.ORANGE, 250, 1),
    TRIPLE_STRAWBERRY(Fruit.STRAWBERRY, 100, 2),
    TRIPLE_GRAPES(Fruit.GRAPES, 50, 3),
    TRIPLE_COCONUT(Fruit.COCONUT, 20, 4),
    TRIPLE_PEACH(Fruit.PEACH, 10, 5),
    TRIPLE_BANANA(Fruit.BANANA, 5, 6),
    TRIPLE_KIWI(Fruit.KIWI, 2, 7)
}