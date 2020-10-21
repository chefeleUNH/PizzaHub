package edu.newhaven.pizzahub.model

import kotlin.random.Random

data class Pizzeria(val logo: String = "", val name: String = "", val ready_in: Int = 0) {
    //TODO: replace this with actual GPS distance
    val distance = Random.nextInt(1, 20) // temp. placeholder
}