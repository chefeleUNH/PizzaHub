package edu.newhaven.pizzahub.controller

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import edu.newhaven.pizzahub.view.PizzeriaView
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.model.Pizzeria

class PizzeriaAdapter(options: FirestoreRecyclerOptions<Pizzeria>,
                      private var resources: Resources) :
    FirestoreRecyclerAdapter<Pizzeria, PizzeriaView>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzeriaView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pizzeria_view, parent, false)
        return PizzeriaView(view)
    }

    override fun onBindViewHolder(holder: PizzeriaView, position: Int, model: Pizzeria) {
        val resID = resources.getIdentifier(model.logo, "drawable", "edu.newhaven.pizzahub")
        holder.ivLogo.setImageResource(resID)
        holder.tvName.text = model.name
        holder.tvReadyIn.text = "ready in ${model.ready_in} minutes"
        holder.tvDistance.text = "${model.distance} miles"
    }

}