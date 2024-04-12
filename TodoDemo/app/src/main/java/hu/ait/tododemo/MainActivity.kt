package hu.ait.tododemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.tododemo.ui.navigation.MainNavigation
import hu.ait.tododemo.ui.screen.MainScreen
import hu.ait.tododemo.ui.screen.SummaryScreen
import hu.ait.tododemo.ui.theme.TodoDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoAppNavHost()
                }
            }
        }
    }
}

@Composable
fun TodoAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.MainScreen.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(MainNavigation.MainScreen.route) {
            MainScreen(
                onNavigateToSummary = { all, important ->
                    navController.navigate(
                        MainNavigation.SummaryScreen.createRoute(all, important))
                }
            )
        }

        composable(MainNavigation.SummaryScreen.route,
            // extract all and important arguments
            arguments = listOf(
                navArgument("all"){type = NavType.IntType},
                navArgument("important"){type = NavType.IntType})
            ) {
            val numalltodo = it.arguments?.getInt("all")
            val numimportant = it.arguments?.getInt("important")
            if (numalltodo != null && numimportant != null) {
                SummaryScreen(
                    numalltodo = numalltodo,
                    numimportanttodo = numimportant
                )
            }
        }
    }
}