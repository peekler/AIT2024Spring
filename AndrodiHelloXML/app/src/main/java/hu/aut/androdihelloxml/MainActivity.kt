package hu.aut.androdihelloxml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvData = findViewById<TextView>(R.id.tvData)
        val btnTime = findViewById<TextView>(R.id.btnTime)

        btnTime.setOnClickListener {
            tvData.text = Date(System.currentTimeMillis()).toString()
        }
    }
}