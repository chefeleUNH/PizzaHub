package edu.newhaven.pizzahub.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.controller.PizzeriaAdapter
import edu.newhaven.pizzahub.model.Pizzeria
import kotlinx.android.synthetic.main.pizzeria_list_view.*

const val FINE_LOCATION_REQUEST_CODE = 0

class PizzeriaListActivity : AppCompatActivity(), PizzeriaAdapter.OnDataChanged {

    private val TAG = javaClass.name

    private val title = "Pizzerias"

    private val db = FirebaseFirestore.getInstance()

    private lateinit var pizzeriaAdapter: PizzeriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pizzeria_list_view)

        // set the support action bar title
        supportActionBar?.title = title

        val query: Query = db
            .collection("pizzerias")
            .orderBy("ready_in")
            .limit(50)

        val options: FirestoreRecyclerOptions<Pizzeria> =
            FirestoreRecyclerOptions.Builder<Pizzeria>()
                .setQuery(query, Pizzeria::class.java)
                .build()

        pizzeriaAdapter = PizzeriaAdapter(options, this)

        rv_pizzeria_view.adapter = pizzeriaAdapter
        rv_pizzeria_view.layoutManager = LinearLayoutManager(this)

        // add behavior to bottom nav bar
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_pizzerias -> {
                    // Do nothing as this activity is already running
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
        pizzeriaAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        pizzeriaAdapter.stopListening()
    }

    private fun updateDistances() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { loc: Location? ->
                    pizzeriaAdapter.updateAllDistances(loc)
                }
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                FINE_LOCATION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            FINE_LOCATION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    Log.d(TAG, "Permission Granted for ${permissions[0]}")
                    updateDistances()
                } else {
                    Log.d(TAG, "Permission Denied")
                }
                return
            }
        }
    }

    override fun dataChanged() {
        updateDistances()
    }
}