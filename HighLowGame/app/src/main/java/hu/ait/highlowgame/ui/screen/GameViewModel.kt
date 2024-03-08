package hu.ait.highlowgame.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {
    var generatedNum by mutableStateOf(0)
    var counter by mutableStateOf(0)

    init {
        generateNewNum()
    }

    fun generateNewNum() {
        generatedNum = Random(System.currentTimeMillis()).nextInt(3)
        counter = 0
    }

    fun increaseCounter() {
        counter++
    }
}