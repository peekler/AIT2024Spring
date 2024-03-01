package hu.ait.layoutinflaterdemo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.layoutinflaterdemo.databinding.ActivityMainBinding
import hu.ait.layoutinflaterdemo.databinding.TodoRowBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            saveTodo()
        }
    }

    private fun saveTodo() {
        var todoBinding = TodoRowBinding.inflate(layoutInflater)

        todoBinding.tvTodo.text = binding.etTodo.text.toString()
        todoBinding.btnDelete.setOnClickListener {
            binding.layoutContent.removeView(todoBinding.root)
        }

        binding.layoutContent.addView(todoBinding.root)
    }


}