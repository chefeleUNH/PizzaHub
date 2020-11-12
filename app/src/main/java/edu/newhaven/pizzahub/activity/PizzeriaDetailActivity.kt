package edu.newhaven.pizzahub.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import edu.newhaven.pizzahub.R
import edu.newhaven.pizzahub.glide.GlideApp
import edu.newhaven.pizzahub.model.Pizzeria
import kotlinx.android.synthetic.main.pizzeria_detail_view.*

class PizzeriaDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pizzeria_detail_view)

        val pizzeria = intent.getSerializableExtra("PIZZERIA") as? Pizzeria ?: return

        // set the name
        tv_detail_name.text = pizzeria.name

        // create a spinny thing
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        // bind the pizzeria logo using the Glide generated API + Firebase UI Storage
        val storageReference = Firebase.storage.getReferenceFromUrl(pizzeria.logo)
        GlideApp
            .with(this)
            .load(storageReference)
            .placeholder(circularProgressDrawable)
            .into(iv_detail_logo)
    }
}