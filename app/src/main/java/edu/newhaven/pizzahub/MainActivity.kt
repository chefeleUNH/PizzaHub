package edu.newhaven.pizzahub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.name

    private val db = FirebaseFirestore.getInstance()

    private var adapter : FirestoreRecyclerAdapter<Pizzeria, PizzeriaViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val query: Query = db
            .collection("pizzerias")
            .orderBy("name")
            .limit(50)

        val options: FirestoreRecyclerOptions<Pizzeria> = FirestoreRecyclerOptions.Builder<Pizzeria>()
            .setQuery(query, Pizzeria::class.java)
            .build()

        adapter = object : FirestoreRecyclerAdapter<Pizzeria, PizzeriaViewHolder>(options) {

            override fun onBindViewHolder(
                holder: PizzeriaViewHolder,
                position: Int,
                model: Pizzeria
            ) {
                holder.txtName.text = model.name
                val resID = resources.getIdentifier(model.logo, "drawable", packageName)
                holder.imgLogo.setImageResource(resID)
            }

            override fun onCreateViewHolder(group: ViewGroup, i: Int): PizzeriaViewHolder {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.view_holder for each item
                val view = LayoutInflater.from(group.context)
                    .inflate(R.layout.view_holder, group, false)
                return PizzeriaViewHolder(view)
            }

            override fun getItemCount(): Int {
                return super.getItemCount()
            }

            override fun onError(e: FirebaseFirestoreException) {
                Log.e("error", e!!.message)
            }
        }
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }
}