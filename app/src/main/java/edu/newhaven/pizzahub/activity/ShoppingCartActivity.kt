package edu.newhaven.pizzahub.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.controller.ShoppingCartAdapter
import edu.newhaven.pizzahub.model.ShoppingCart
import kotlinx.android.synthetic.main.shopping_cart.*

class ShoppingCartActivity : AppCompatActivity() {

    private val title = "Shopping Cart"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_cart)

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

        // add behavior to bottom nav bar
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    // Respond to navigation item 1 click
                    val intent = Intent(this, PizzeriaListActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.item2 -> {
                    // Do nothing as this activity is already running
                    true
                }
                else -> false
            }
        }
    }
}