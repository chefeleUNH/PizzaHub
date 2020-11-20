package edu.newhaven.pizzahub.controller

import android.content.Context
import android.content.Intent
import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.activity.PizzeriaDetailActivity
import edu.newhaven.pizzahub.glide.GlideApp
import edu.newhaven.pizzahub.model.MenuItem
import edu.newhaven.pizzahub.model.Pizzeria
import edu.newhaven.pizzahub.view.MenuItemViewHolder
import edu.newhaven.pizzahub.view.PizzeriaViewHolder
import java.math.RoundingMode
import java.text.DecimalFormat

class MenuItemAdapter(options: FirestoreRecyclerOptions<MenuItem>, private val context: Context) :
    FirestoreRecyclerAdapter<MenuItem, MenuItemViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_list_row, parent, false)
        return MenuItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int, model: MenuItem) {
        // this will fire when the user clicks a row in the recycler view
//        holder.itemView.setOnClickListener {
//            // load the detail view
//            val intent = Intent(context, PizzeriaDetailActivity::class.java).apply {
//                putExtra("PIZZERIA", model)
//            }
//            context.startActivity(intent)
//        }

        // create a spinny thing
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        // bind the pizzeria logo using the Glide generated API + Firebase UI Storage
        val storageReference = Firebase.storage.getReferenceFromUrl(model.photo)
        GlideApp
            .with(holder.ivPhoto)
            .load(storageReference)
            .placeholder(circularProgressDrawable)
            .into(holder.ivPhoto)

        // bind everything else
        holder.tvMenuName.text = model.name
        holder.tvPrice.text = "$${model.price}"
    }
}