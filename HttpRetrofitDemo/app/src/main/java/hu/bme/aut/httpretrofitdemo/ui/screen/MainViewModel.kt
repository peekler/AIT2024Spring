package hu.bme.aut.httpretrofitdemo.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.httpretrofitdemo.data.RoverPhotos
import hu.bme.aut.httpretrofitdemo.network.MarsAPI
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var marsAPI: MarsAPI
) : ViewModel() {

    var marsUIState: MarsUiState by mutableStateOf(MarsUiState.Init)

    fun getRoverPhotos(date: String, apiKey: String) {
        marsUIState = MarsUiState.Loading

        viewModelScope.launch {
            try {
                val result = marsAPI.getRoverPhotos(date, apiKey)

                marsUIState = MarsUiState.Success(result)
            } catch (e: Exception) {
                marsUIState = MarsUiState.Error(e.message!!)
            }
        }
    }
}

sealed interface MarsUiState {
    object Init : MarsUiState
    object Loading : MarsUiState
    data class Success(val roverPhotos: RoverPhotos) : MarsUiState
    data class Error(val errorMsg: String) : MarsUiState
}