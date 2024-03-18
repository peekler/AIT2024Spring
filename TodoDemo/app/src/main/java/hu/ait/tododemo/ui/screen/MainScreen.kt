package hu.ait.tododemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.tododemo.data.TodoItem
import hu.ait.tododemo.data.TodoPriority

@Composable
fun MainScreen(
    todoViewModel: TodoViewModel = viewModel(),
    onNavigateToSummary: () -> Unit
) {
    var todoTitle by rememberSaveable {
        mutableStateOf("")
    }
    var todoDescription by rememberSaveable {
        mutableStateOf("")
    }
    var todoImportant by rememberSaveable {
        mutableStateOf(false)
    }


    Column {
        OutlinedTextField(
            value = todoTitle,
            onValueChange = {todoTitle=it},
            label = { Text(text = "Enter todo here:") }
        )
        OutlinedTextField(
            value = todoDescription,
            onValueChange = {todoDescription=it},
            label = { Text(text = "Enter todo description:") }
        )
        Row() {
            Checkbox(checked = todoImportant, onCheckedChange = {todoImportant=it})
            Text(text = "Important")
        }
        Row {
            Button(onClick = {

                todoViewModel.addTodoList(
                    TodoItem("1", todoTitle, todoDescription,
                        "2024", TodoPriority.HIGH, false)
                )

            }) {
                Text(text = "Add")
            }
            Button(onClick = {
                onNavigateToSummary()
            }) {
                Text(text = "Sum")
            }
        }

        //LazyColumn - print todos from the ViewModel...

    }
}