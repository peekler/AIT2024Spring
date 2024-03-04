package hu.ait.composesideeffectdemo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen() {
    val context = LocalContext.current

    var counter by remember { mutableStateOf(0) }

    /*SideEffect {
        Toast.makeText(context, "RECOMPOSE", Toast.LENGTH_LONG).show()
    }*/

    LaunchedEffect(key1 = Unit) {
        Toast.makeText(context, "LAUNCHED", Toast.LENGTH_LONG).show()
    }

    LaunchedEffect(key1 = counter) {
        Toast.makeText(context, "COUNTCHANGE", Toast.LENGTH_LONG).show()
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            Toast.makeText(context, "DISPOSED", Toast.LENGTH_LONG).show()
        }
    }


    Column {
        Text(text = "$counter")
        Button(onClick = { counter++ }) {
            Text(text = "Increase")
        }
    }

}