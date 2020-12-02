package edu.newhaven.pizzahub.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.model.MenuItem
import edu.newhaven.pizzahub.view.ShoppingCartViewHolder

class ShoppingCartAdapter(private val items: List<MenuItem>): RecyclerView.Adapter<ShoppingCartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_shopping_cart, parent, false)
        return ShoppingCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.tvCartMenuName.text = items[position].name
        holder.tvCartMenuPrice.text = "$${items[position].price}"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}