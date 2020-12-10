package edu.newhaven.pizzahub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.glide.GlideApp
import edu.newhaven.pizzahub.model.MenuItem
import edu.newhaven.pizzahub.model.MenuItemDoesNotMatchPizzeria
import edu.newhaven.pizzahub.model.Pizzeria
import edu.newhaven.pizzahub.model.ShoppingCart
import kotlinx.android.synthetic.main.activity_menu_detail.*


class MenuDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        // unpack the intent
        val menuItem = intent.getSerializableExtra("MENU_ITEM") as? MenuItem ?: return
        val pizzeria = intent.getSerializableExtra("PIZZERIA") as? Pizzeria ?: return

        // set the support action bar title
        supportActionBar?.title = menuItem.name

        // set the detail view fields
        tv_detail_menu_name.text = menuItem.name

        // create a spinny thing
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        // bind the menu item photo using the Glide generated API + Firebase UI Storage
        val storageReference = Firebase.storage.getReferenceFromUrl(menuItem.photo)
        GlideApp
            .with(this)
            .load(storageReference)
            .placeholder(circularProgressDrawable)
            .into(iv_detail_photo)

        btn_add_to_cart.setOnClickListener {
            try {
                ShoppingCart.add(menuItem, pizzeria)
                val toast = Toast.makeText(this, "Added to cart", Toast.LENGTH_LONG)
                toast.show()
            } catch (e: MenuItemDoesNotMatchPizzeria) {
                AlertDialog.Builder(this)
                    .setTitle(R.string.invalid_request)
                    .setMessage(R.string.invalid_cart_item)
                    .setPositiveButton(R.string.ok, null)
                    .show()
            }
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