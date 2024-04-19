package hu.bme.aut.aitforum.ui.screen.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import hu.bme.aut.aitforum.data.Post
import hu.bme.aut.aitforum.data.PostWithId
import hu.bme.aut.aitforum.ui.screen.login.LoginUiState
import hu.bme.aut.aitforum.ui.screen.writepost.WritePostViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class MessagesViewModel : ViewModel() {
    var messagesUiState: MessagesUIState by mutableStateOf(MessagesUIState.Init)

    fun postsList() = callbackFlow {
        val snapshotListener =
            FirebaseFirestore.getInstance().collection("posts")
                .addSnapshotListener() { snapshot, e ->
                    val response = if (snapshot != null) {
                        val postList = snapshot.toObjects(Post::class.java)
                        val postWithIdList = mutableListOf<PostWithId>()

                        postList.forEachIndexed { index, post ->
                            postWithIdList.add(PostWithId(snapshot.documents[index].id, post))
                        }

                        MessagesUIState.Success(
                            postWithIdList
                        )
                    } else {
                        MessagesUIState.Error(e?.message.toString())
                    }

                    trySend(response) // emit this value through the flow
                }
        awaitClose { // when we navigate out from the screen,
            // the flow stosp and we stop here the firebase listener
            snapshotListener.remove()
        }
    }

    fun deletePost(postKey: String) {
        FirebaseFirestore.getInstance().collection(
            "posts"
        ).document(postKey).delete()
    }


}

sealed interface MessagesUIState {
    object Init : MessagesUIState
    object Loading : MessagesUIState
    data class Success(val postList: List<PostWithId>) : MessagesUIState
    data class Error(val error: String?) : MessagesUIState
}


