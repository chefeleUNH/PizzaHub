package edu.newhaven.pizzahub.model

class MenuItemDoesNotMatchPizzeria : Exception()

object ShoppingCart {
    var pizzeria: Pizzeria? = null  // a shopping cart can only contain one pizzeria at a time, and won't contain one until the user adds an item to the cart
    var items = mutableListOf<MenuItem>()
    val total get() = if (items.isEmpty()) 0.0 else items.sumByDouble{ it.price }

    fun add(item: MenuItem, pizzeria: Pizzeria) {
        // if the pizzeria hasn't been set yet, set it now
        if (this.pizzeria == null) {
            this.pizzeria = pizzeria
        }

        // if the pizzerias don't match, throw an error
        if (this.pizzeria != pizzeria) {
            throw MenuItemDoesNotMatchPizzeria()
        }

        // the pizzerias match, so add the item
        items.add(item)
    }

    fun reset() {
        pizzeria = null
        items.clear()
    }
}