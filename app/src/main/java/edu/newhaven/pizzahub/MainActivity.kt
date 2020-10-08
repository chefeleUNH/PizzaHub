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

        Log.i(TAG, "query: $query")

        // Configure recycler adapter options:
        //  * query is the Query object defined above.
        //  * Chat.class instructs the adapter to convert each DocumentSnapshot to a Chat object

        val options: FirestoreRecyclerOptions<Pizzeria> = FirestoreRecyclerOptions.Builder<Pizzeria>()
            .setQuery(query, Pizzeria::class.java)
            .build()

        Log.i(TAG, "options: $options")


        adapter = object : FirestoreRecyclerAdapter<Pizzeria, PizzeriaViewHolder>(options) {

            override fun onBindViewHolder(
                holder: PizzeriaViewHolder,
                position: Int,
                model: Pizzeria
            ) {
                holder.name.text = model.name
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

        Log.i(TAG, "adapter: $adapter")

        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        Log.i(TAG, "$mRecyclerView")
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
        Log.i(TAG, "start listening")
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
        Log.i(TAG, "stop listening")

    }
}