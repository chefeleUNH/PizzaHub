package edu.newhaven.pizzahub.model

import java.io.Serializable

data class Pizzeria(
    val logo: String = "",
    val name: String = "",
    val ready_in: Int = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0
) : Serializable {
    var id: String = ""
    var distance: String = ""
}