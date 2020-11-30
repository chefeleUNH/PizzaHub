package edu.newhaven.pizzahub.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.newhaven.pizzahub.R

class ShoppingCartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvCartMenuName: TextView = view.findViewById(R.id.tv_cart_menu_name)
    var tvCartMenuPrice: TextView = view.findViewById(R.id.tv_cart_menu_price)
}