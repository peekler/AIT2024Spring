package hu.bme.aut.httpmoneydemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.httpmoneydemo.data.MoneyResult


@Composable
fun MoneyScreen(
    moneyViewModel: MoneyViewModel = hiltViewModel()
) {
    Column {
        Button(onClick = {
            moneyViewModel.getRates("969c37b5a73f8cb2d12c081dcbdc35e6")
        }) {
            Text(text = "Get rates")
        }

        // state machine part
        when (moneyViewModel.moneyUIState) {
            is MoneyUiState.Init -> Text(text = "Press the button...")
            is MoneyUiState.Loading -> CircularProgressIndicator()
            is MoneyUiState.Success -> ResultView(
                moneyResult = (moneyViewModel.moneyUIState as MoneyUiState.Success).moneyResult)
            is MoneyUiState.Error ->
                Text(text = "Error:" +
                        " ${(moneyViewModel.moneyUIState as MoneyUiState.Error).errorMsg}")
        }
    }
}

@Composable
fun ResultView(moneyResult: MoneyResult) {
    Column() {
        Text(text = "Base EUR")
        Text(text ="USD: ${moneyResult.rates?.uSD}")
        Text(text ="HUF: ${moneyResult.rates?.hUF}")
        Text(text ="GBP: ${moneyResult.rates?.gBP}")
    }
}