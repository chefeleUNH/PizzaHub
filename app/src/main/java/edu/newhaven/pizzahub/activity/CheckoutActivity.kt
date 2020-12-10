package edu.newhaven.pizzahub.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.model.ShoppingCart
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_checkout.bottom_navigation

class CheckoutActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val title = "Checkout"
    private val TAG = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // set the support action bar title
        supportActionBar?.title = title

        // set the cart total
        tv_total.text = "Total: $${ShoppingCart.total.toString()}"

        btn_confirm_order.setOnClickListener {
            confirmOrder()
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

    private fun confirmOrder() {
        val order = hashMapOf(
            "pizzeria_id" to ShoppingCart.pizzeria?.id,
            "total" to ShoppingCart.total
        )
        db.collection("orders")
            .add(order)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                AlertDialog.Builder(this)
                    .setTitle(R.string.order_confirmed)
                    .setMessage(getString(R.string.thank_you, ShoppingCart.total))
                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { _, _ ->
                        ShoppingCart.reset()
                        tv_total.text = getString(R.string.empty_shopping_cart)
                        btn_confirm_order.isEnabled = false
                    })
                    .show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}