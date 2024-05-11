package hu.bme.aut.httpretrofitdemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.httpretrofitdemo.data.RoverPhotos
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var earthDate by rememberSaveable { mutableStateOf("2015-6-3") }

  
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            label = {
                Text(text = "Earth date")
            },
            value = earthDate,
            onValueChange = {
                earthDate = it
            },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.DateRange, null)
            }
        )

        Button(onClick = {
            mainViewModel.getRoverPhotos(earthDate, "DEMO_KEY")
        }) {
            Text(text = "Refresh")
        }

        when (mainViewModel.marsUIState) {
            is MarsUiState.Init -> {}
            is MarsUiState.Loading -> CircularProgressIndicator()
            is MarsUiState.Success -> ResultScreen(
                (mainViewModel.marsUIState as MarsUiState.Success).roverPhotos)
            is MarsUiState.Error -> Text(
                text = "Error: " +
                        "${(mainViewModel.marsUIState as MarsUiState.Error).errorMsg}"
            )
        }
    }
}

@Composable
fun ResultScreen(roverPhotsResult: RoverPhotos) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(roverPhotsResult.photos!!) {
            RoverPhotoCard(roverName = it!!.rover!!.name!!,
                earthDate = it!!.earthDate!!,
                photoUrl = it!!.imgSrc!!)
        }
    }
}


@Composable
fun RoverPhotoCard (
    roverName: String,
    earthDate: String,
    photoUrl: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = roverName
            )
            Text(
                text = earthDate
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(250.dp)
            )
        }
    }
}