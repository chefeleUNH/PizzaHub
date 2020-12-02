package edu.newhaven.pizzahub.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.controller.ShoppingCartAdapter
import edu.newhaven.pizzahub.model.ShoppingCart
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class ShoppingCartActivity : AppCompatActivity() {

    private val title = "Shopping Cart"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        // set the support action bar title
        supportActionBar?.title = title

        // configure the recycler view
        rv_cart.adapter = ShoppingCartAdapter(ShoppingCart.items)
        rv_cart.layoutManager = LinearLayoutManager(this)

        // set the onClick
        btn_place_order.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }

        // need to explicitly set the selected item on the nav bar when it's not the first item
        bottom_navigation.selectedItemId = R.id.item_shopping_cart

        // add behavior to bottom nav bar
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_pizzerias -> {
                    // Respond to navigation item 1 click
                    val intent = Intent(this, PizzeriaListActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.item_shopping_cart -> {
                    // Do nothing as this activity is already running
                    true
                }
                else -> false
            }
        }
    }
}