package edu.newhaven.pizzahub.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import edu.newhaven.pizzahub.R
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_checkout.bottom_navigation

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        btn_confirm_order.setOnClickListener {
            val toast = Toast.makeText(this, "Order confirmed.", Toast.LENGTH_SHORT)
            toast.show()
        }

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
                    // Respond to navigation item 2 click
                    val intent = Intent(this, ShoppingCartActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}