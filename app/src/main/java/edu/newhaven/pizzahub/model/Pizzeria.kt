package edu.newhaven.pizzahub.model

data class Pizzeria(
    val logo: String = "",
    val name: String = "",
    val ready_in: Int = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0
) {
    var distance: String = ""
}