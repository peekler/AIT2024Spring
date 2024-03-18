package hu.ait.tododemo.ui.navigation

sealed class MainNavigation(val route: String) {
    object MainScreen : MainNavigation("mainscreen")
    object SummaryScreen : MainNavigation(
        "summaryscreen?all={all}&important={important}") {
        fun createRoute(all: Int, important: Int) : String {
            return "summaryscreen?all=$all&important=$important"
        }
    }
}