package hu.ait.lifecycledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("KEY_SCORE")
        }

        Log.d("TAG_LIFE", "onCreate called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY_SCORE", score)
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG_LIFE", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG_LIFE", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG_LIFE", "onPause called")

        // save score in databse
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG_LIFE", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG_LIFE", "onDestroy called")
    }

}