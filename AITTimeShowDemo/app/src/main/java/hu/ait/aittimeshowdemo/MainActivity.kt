package hu.ait.aittimeshowdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import hu.ait.aittimeshowdemo.databinding.ActivityMainBinding
import java.util.Date


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val tvData = findViewById<TextView>(R.id.tvData)
        //val btnShow = findViewById<Button>(R.id.btnShow)

        binding.btnShow.setOnClickListener {
            val current = Date(System.currentTimeMillis()).toString()

            binding.tvData.text=current

            Toast.makeText(this, current, Toast.LENGTH_LONG).show()
        }

    }

}