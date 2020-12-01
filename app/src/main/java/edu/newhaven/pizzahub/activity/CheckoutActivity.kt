package edu.newhaven.pizzahub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import edu.newhaven.pizzahub.R
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        btn_confirm_order.setOnClickListener {
            val toast = Toast.makeText(this, "Order confirmed.", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}