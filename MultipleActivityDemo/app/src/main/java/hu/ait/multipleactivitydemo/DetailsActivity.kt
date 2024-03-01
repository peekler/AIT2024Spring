package hu.ait.multipleactivitydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.multipleactivitydemo.databinding.ActivityDetailsBinding
import hu.ait.multipleactivitydemo.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(MainActivity.KEY_DATA)) {
            binding.tvData.text = intent.getStringExtra(MainActivity.KEY_DATA)
        }
    }
}