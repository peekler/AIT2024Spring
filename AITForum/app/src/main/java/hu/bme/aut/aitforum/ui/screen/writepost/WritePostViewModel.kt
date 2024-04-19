package hu.bme.aut.aitforum.ui.screen.writepost

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.bme.aut.aitforum.data.Post
import hu.bme.aut.aitforum.ui.screen.login.LoginUiState

class WritePostViewModel : ViewModel() {

    var writePostUiState: WritePostUiState by mutableStateOf(WritePostUiState.Init)

    fun uploadPost(
        postTitle: String, postBody: String, imgUrl: String = ""
    ) {
        writePostUiState = WritePostUiState.LoadingPostUpload
        val newPost = Post(
            FirebaseAuth.getInstance().uid!!,
            FirebaseAuth.getInstance().currentUser?.email!!,
            postTitle,
            postBody,
            imgUrl
        )
        val postsCollection = FirebaseFirestore.getInstance().collection(
            "posts")
        postsCollection.add(newPost)
            .addOnSuccessListener{
                writePostUiState = WritePostUiState.PostUploadSuccess
            }
            .addOnFailureListener{
                writePostUiState = WritePostUiState.ErrorDuringPostUpload(
                    "Post upload failed")
            }
    }

}

sealed interface WritePostUiState {
    object Init : WritePostUiState
    object LoadingPostUpload : WritePostUiState
    object PostUploadSuccess : WritePostUiState
    data class ErrorDuringPostUpload(val error: String?) : WritePostUiState
}