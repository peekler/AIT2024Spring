package hu.ait.composenavigationdemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    Column {
        Text(text = "Main Screen", fontSize = 30.sp)
        
        Button(onClick = {  }) {
            Text(text = "Show details")
        }
    }
}