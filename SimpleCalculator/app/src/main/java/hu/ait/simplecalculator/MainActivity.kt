package hu.ait.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import hu.ait.simplecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlus.setOnClickListener {
            calculateVale(Int::plus)
        }



        binding.btnMinus.setOnClickListener {
            //calculateVale(Int::minus)
            calculateVale(::myCalc)
        }
    }



    private fun myCalc(a: Int, b: Int) = 2 * a + b



    private fun calculateVale(op: (Int, Int) -> Int) {
        try {
            if (binding.etNumA.text.isNotEmpty()) {
                if (binding.etNumB.text.isNotEmpty()) {
                    val numA = binding.etNumA.text.toString().toInt()
                    val numB = binding.etNumB.text.toString().toInt()

                    var result = op(numA, numB)


                    binding.tvResult.text = getString(R.string.text_result, result)
                } else {
                    binding.etNumB.error = getString(R.string.error_not_empty)
                }
            } else {
                binding.etNumA.error = "This field can not be empty"
            }
        } catch (e: Exception) {
            binding.tvResult.text = "Error: ${e.message}"
        }
    }
}