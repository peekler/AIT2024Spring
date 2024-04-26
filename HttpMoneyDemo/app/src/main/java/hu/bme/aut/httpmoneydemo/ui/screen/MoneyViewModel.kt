package hu.bme.aut.httpmoneydemo.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.httpmoneydemo.data.MoneyResult
import hu.bme.aut.httpmoneydemo.network.MoneyAPI
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoneyViewModel @Inject constructor(
    private val moneyAPI: MoneyAPI
) : ViewModel() {

    var moneyUIState: MoneyUiState by mutableStateOf(MoneyUiState.Init)

    fun getRates(accessKey: String) {
        // exec network call and change UI states properly..
        moneyUIState = MoneyUiState.Loading

        viewModelScope.launch {
            try {
                val moneyResult = moneyAPI.getRates(accessKey)

                moneyUIState = MoneyUiState.Success(moneyResult)
            } catch (e: Exception) {
                moneyUIState = MoneyUiState.Error(e.message!!)
            }
        }
    }
}


sealed interface MoneyUiState {
    object Init : MoneyUiState
    object Loading : MoneyUiState
    data class Success(val moneyResult: MoneyResult) : MoneyUiState
    data class Error(val errorMsg: String) : MoneyUiState
}