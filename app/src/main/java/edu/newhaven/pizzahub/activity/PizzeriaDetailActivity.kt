package edu.newhaven.pizzahub.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.controller.MenuItemAdapter
import edu.newhaven.pizzahub.glide.GlideApp
import edu.newhaven.pizzahub.model.MenuItem
import edu.newhaven.pizzahub.model.Pizzeria
import kotlinx.android.synthetic.main.pizzeria_detail_view.*

class PizzeriaDetailActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var menuItemAdapter: MenuItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pizzeria_detail_view)

        // get the pizzeria from the intent
        val pizzeria = intent.getSerializableExtra("PIZZERIA") as? Pizzeria ?: return

        // set the support action bar title
        supportActionBar?.title = pizzeria.name

        // set the detail view fields
        tv_detail_ready_in.text = "ready in ${pizzeria.ready_in} minutes"

        // create a spinny thing
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        // bind the pizzeria logo using the Glide generated API + Firebase UI Storage
        val storageReference = Firebase.storage.getReferenceFromUrl(pizzeria.logo)
        GlideApp
            .with(this)
            .load(storageReference)
            .placeholder(circularProgressDrawable)
            .into(iv_detail_logo)

        // load the menu
        val query: Query = db
            .collection("pizzerias")
            .document(pizzeria.id)
            .collection("menu")

        val options: FirestoreRecyclerOptions<MenuItem> =
            FirestoreRecyclerOptions.Builder<MenuItem>()
                .setQuery(query, MenuItem::class.java)
                .build()

        menuItemAdapter = MenuItemAdapter(options)

        rv_menu.adapter = menuItemAdapter
        rv_menu.layoutManager = LinearLayoutManager(this)

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

    override fun onStart() {
        super.onStart()
        menuItemAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        menuItemAdapter.stopListening()
    }
}