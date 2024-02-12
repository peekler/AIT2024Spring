package hu.ait.aittimeshowdemo


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
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


            //if (binding.etName.text.isNotEmpty()) { }

            val current = Date(System.currentTimeMillis()).toString()
            val userName = binding.etName.text.toString()

            binding.tvData.text="$userName time: $current"

            //Toast.makeText(this, "hello $userName", Toast.LENGTH_LONG).show()

            Snackbar.make(
                binding.root, "hello $userName", Snackbar.LENGTH_LONG
            ).setAction("Undo"
            ) {
                // code..
            }.show()

            Log.d("TAG_DEMO", "Button pressed $current")

            revealCard()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun revealCard() {
        val x = binding.cardView.getRight()
        val y = binding.cardView.getBottom()

        val startRadius = 0
        val endRadius = Math.hypot(binding.cardView.getWidth().toDouble(),
            binding.cardView.getHeight().toDouble()).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(
            binding.cardView,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        //anim.duration = 5000

        binding.cardView.setVisibility(View.VISIBLE)
        anim.start()
    }

}