package hu.ait.tododemo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.tododemo.data.TodoItem
import hu.ait.tododemo.data.TodoPriority
import java.util.Date
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun MainScreen(
    todoViewModel: TodoViewModel = viewModel(),
    onNavigateToSummary: (Int, Int) -> Unit
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
            onValueChange = { todoTitle = it },
            label = { Text(text = "Enter todo here:") }
        )
        OutlinedTextField(
            value = todoDescription,
            onValueChange = { todoDescription = it },
            label = { Text(text = "Enter todo description:") }
        )
        Row() {
            Checkbox(checked = todoImportant, onCheckedChange = { todoImportant = it })
            Text(text = "Important")
        }
        Row {

            Button(onClick = {
                todoViewModel.addTodoList(
                    TodoItem(
                        id = "0",
                        title = todoTitle,
                        description = todoDescription,
                        createDate = Date(System.currentTimeMillis()).toString(),
                        priority = if (todoImportant) TodoPriority.HIGH else TodoPriority.NORMAL,
                        isDone = false
                    )
                )
            }) {
                Text(text = "Add")
            }

            Button(onClick = {
                onNavigateToSummary(
                    todoViewModel.getAllTodoNum(),
                    todoViewModel.getImportantTodoNum()
                )
            }) {
                Text(text = "Sum")
            }

            Button(onClick = {
                todoViewModel.clearAllTodos()
            }) {
                Text(text = "Delete all")
            }
        }

        // show TodoItems from the ViewModel in a LayzColumn
        if (todoViewModel.getAllToDoList().isEmpty()) {
            Text(text = "No items")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(todoViewModel.getAllToDoList()) {
                    TodoCard(it,
                        onTodoCheckChange = { checkValue ->
                            todoViewModel.changeTodoState(it, checkValue)
                        },
                        onRemoveItem = { todoViewModel.removeTodoItem(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun TodoCard(
    todoItem: TodoItem,
    onTodoCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {}
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
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = todoItem.priority.getIcon()),
                contentDescription = "Priority",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )


            Text(todoItem.title, modifier = Modifier.fillMaxWidth(0.2f))
            Spacer(modifier = Modifier.fillMaxSize(0.55f))
            Checkbox(
                checked = todoItem.isDone,
                onCheckedChange = { onTodoCheckChange(it) }
            )
            Spacer(modifier = Modifier.width(10.dp))
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

