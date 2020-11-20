package edu.newhaven.pizzahub.model

import java.io.Serializable

data class MenuItem(
    val name: String = "",
    val photo: String = "",
    val price: Double = 0.0
) : Serializable