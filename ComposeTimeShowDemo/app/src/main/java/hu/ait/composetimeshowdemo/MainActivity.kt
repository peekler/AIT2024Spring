package hu.ait.composetimeshowdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import hu.ait.composetimeshowdemo.ui.theme.ComposeTimeShowDemoTheme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTimeShowDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // This is where we start...
                    //Greeting("AIT")
                    ShowTime()
                }
            }
        }
    }
}


@Composable
fun ShowTime() {
    var current by rememberSaveable { mutableStateOf("0") }

    Column {
        Text(text = "Time: $current",
            fontSize = 30.sp)

        ClickableText(
            text = AnnotatedString("Click me $current"),
            onClick = {
                current = "DEMO"
            })

        Button(onClick = {
           current = Date(System.currentTimeMillis()).toString()
        }) {
            Text(text = "Show time")
        }
    }
}


@Composable
fun Greeting(name: String, nrTxt: Int = 5, showTexts: Boolean = true,  modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        if (showTexts) {
            for (i in 0..nrTxt) {
                Text(
                    text = "Hello BEST AIT TEAM!",
                    modifier = modifier
                )
            }
        }

    }
}
