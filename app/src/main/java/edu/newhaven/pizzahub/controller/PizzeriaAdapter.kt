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
import edu.newhaven.pizzahub.model.Pizzeria
import edu.newhaven.pizzahub.view.PizzeriaViewHolder
import java.math.RoundingMode
import java.text.DecimalFormat

class PizzeriaAdapter(options: FirestoreRecyclerOptions<Pizzeria>, private val context: Context,
                      private val onDataChanged: OnDataChanged) :
    FirestoreRecyclerAdapter<Pizzeria, PizzeriaViewHolder>(options) {

    interface OnDataChanged {
        fun dataChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzeriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pizzeria_list_row, parent, false)
        return PizzeriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PizzeriaViewHolder, position: Int, model: Pizzeria) {
        // this will fire when the user clicks a row in the recycler view
        holder.itemView.setOnClickListener {
            // load the detail view
            val intent = Intent(context, PizzeriaDetailActivity::class.java).apply {
                putExtra("PIZZERIA", model)
            }
            context.startActivity(intent)
        }

        // create a spinny thing
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        // bind the pizzeria logo using the Glide generated API + Firebase UI Storage
        val storageReference = Firebase.storage.getReferenceFromUrl(model.logo)
        GlideApp
            .with(holder.ivLogo)
            .load(storageReference)
            .placeholder(circularProgressDrawable)
            .into(holder.ivLogo)

        // bind everything else
        holder.tvName.text = model.name
        holder.tvReadyIn.text = "ready in ${model.ready_in} minutes"
        holder.tvDistance.text = model.distance

        // set the ID field
        model.id = snapshots.getSnapshot(position).id
    }

    override fun onDataChanged() {
        super.onDataChanged()
        onDataChanged.dataChanged()
    }

    fun updateAllDistances(loc: Location?) {
        snapshots.forEach {
            val dest = Location("")
            dest.latitude = it.lat
            dest.longitude = it.lon
            val distMeters = loc?.distanceTo(dest)    // in meters
            val distMiles = distMeters?.times(0.000621371) // in miles
            if (distMiles != null) {
                val df = DecimalFormat("#.#")
                df.roundingMode = RoundingMode.CEILING
                it.distance = "${df.format(distMiles)} miles" // rounded to 1 decimal place
            }
        }
        notifyDataSetChanged()
    }
}