package hu.bme.aut.aitforum.ui.screen.messages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.bme.aut.aitforum.data.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    messagesViewModel: MessagesViewModel = viewModel(),
    onWritePost: ()->Unit = {}
) {
    val postListState = messagesViewModel.postsList().collectAsState(
        initial = MessagesUIState.Init)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AIT Forum") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =
                    MaterialTheme.colorScheme.secondaryContainer
                ),
                actions = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(Icons.Filled.Info, contentDescription = "Info")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onWritePost()
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                )
            }
        }
    ) { contentPadding ->
        // Screen content
        Column(modifier = Modifier.padding(contentPadding)) {
            if (postListState.value == MessagesUIState.Init) {
                Text(text = "Initializing..")
            }
            else if (postListState.value == MessagesUIState.Loading) {
                CircularProgressIndicator()
            } else if (postListState.value is MessagesUIState.Success) {
                // show messages in a list...
                LazyColumn() {
                    items((postListState.value as MessagesUIState.Success).postList){
                        //Text(text = it.post.title)
                        PostCard(post = it.post,
                            onRemoveItem = {
                                messagesViewModel.deletePost(it.postId)
                            },
                            currentUserId = FirebaseAuth.getInstance().uid!!
                        )
                    }
                }

            } else if (postListState.value is MessagesUIState.Error) {
                // show error...
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    post: Post,
    onRemoveItem: () -> Unit = {},
    currentUserId: String = ""
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = post.title,
                    )
                    Text(
                        text = post.body,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentUserId.equals(post.uid)) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.clickable {
                                onRemoveItem()
                            },
                            tint = Color.Red
                        )
                    }
                }
            }

            if (post.imgUrl.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.imgUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}