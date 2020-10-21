package edu.newhaven.pizzahub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.newhaven.pizzahub.controller.PizzeriaAdapter
import edu.newhaven.pizzahub.model.Pizzeria
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.name

    private val db = FirebaseFirestore.getInstance()

    private lateinit var pizzeriaAdapter: PizzeriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val query: Query = db
            .collection("pizzerias")
            .orderBy("ready_in")
            .limit(50)

        val options: FirestoreRecyclerOptions<Pizzeria> = FirestoreRecyclerOptions.Builder<Pizzeria>()
            .setQuery(query, Pizzeria::class.java)
            .build()

        pizzeriaAdapter = PizzeriaAdapter(options, resources)

        rv_pizzeria_view.adapter = pizzeriaAdapter
        rv_pizzeria_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        pizzeriaAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        pizzeriaAdapter.stopListening()
    }
}