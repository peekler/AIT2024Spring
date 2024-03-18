package hu.ait.tododemo.ui.navigation

sealed class MainNavigation(val route: String) {
    object MainScreen : MainNavigation("mainscreen")
    object SummaryScreen : MainNavigation("summaryscreen")
}