package hu.bme.aut.customlayoutdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.customlayoutdemo.ui.theme.CustomLayoutDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomLayoutDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    BasicDiagonalLayout(modifier.padding(8.dp)) {
        Text("This is a")
        Text("simple text")
        Text("and the items")
        Text("are organized diagonally.")
        Text("- That's it.")
    }
}


@Composable
fun BasicDiagonalLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {

            var xPosition = 0
            var yPosition = 0

            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = xPosition, y = yPosition)
                // Record the y co-ord placed up to
                yPosition += 100
                //yPosition += placeable.height
                xPosition += 100
                //xPosition += placeable.width
            }
        }
    }
}