package hu.ait.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.tictactoe.databinding.ActivityMainBinding
import hu.ait.tictactoe.view.TicTacToeView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClear.setOnClickListener {
            binding.ticTacToeView.resetGame()
        }
    }

    public fun showMessage(msg: String) {
        binding.tvResult.text = msg
        // show a Toast.. or Snackbar..
    }

    public fun isFlagModeOn() : Boolean {
        return binding.cbFlag.isChecked
    }
}