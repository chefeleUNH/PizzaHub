package edu.newhaven.pizzahub.controller

import android.content.res.Resources
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.model.Pizzeria
import edu.newhaven.pizzahub.view.PizzeriaViewHolder
import java.math.RoundingMode
import java.text.DecimalFormat

class PizzeriaAdapter(options: FirestoreRecyclerOptions<Pizzeria>,
                      private var resources: Resources) :
    FirestoreRecyclerAdapter<Pizzeria, PizzeriaViewHolder>(options) {

    private val TAG = javaClass.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzeriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pizzeria_view, parent, false)
        return PizzeriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PizzeriaViewHolder, position: Int, model: Pizzeria) {
        val resID = resources.getIdentifier(model.logo, "drawable", "edu.newhaven.pizzahub")
        holder.ivLogo.setImageResource(resID)
        holder.tvName.text = model.name
        holder.tvReadyIn.text = "ready in ${model.ready_in} minutes"
        holder.tvDistance.text = "${model.distance} miles"
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
                it.distance = df.format(distMiles) // rounded to 1 decimal place
            }
        }
        notifyDataSetChanged()
    }
}