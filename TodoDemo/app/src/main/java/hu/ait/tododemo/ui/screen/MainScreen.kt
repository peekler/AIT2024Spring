package hu.ait.tododemo.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    todoViewModel: TodoViewModel = hiltViewModel(),
    onNavigateToSummary: (Int, Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var showAddDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Todo app")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        showAddDialog = true
                    }) {
                        Icon(Icons.Filled.Add, null)
                    }

                    IconButton(onClick = {
                        todoViewModel.clearAllTodos()
                    }) {
                        Icon(Icons.Filled.Delete, null)
                    }
                    IconButton(onClick = {
                        coroutineScope.launch {
                            onNavigateToSummary(
                                todoViewModel.getAllTodoNum(),
                                todoViewModel.getImportantTodoNum()
                            )
                        }
                    }) {
                        Icon(Icons.Filled.Info, null)
                    }
                }
            )
        },
        content = {
            TodoListContent(
                Modifier.padding(it), todoViewModel, onNavigateToSummary
            )

            if (showAddDialog) {
                AddNewTodoDialog(todoViewModel,
                    onDismissRequest = {
                        showAddDialog = false
                    })
            }
        }
    )
}

@Composable
fun TodoListContent(
    modifier: Modifier,
    todoViewModel: TodoViewModel,
    onNavigateToSummary: (Int, Int) -> Unit
) {
    val todoList by todoViewModel.getAllToDoList().collectAsState(emptyList())

    var todoToEdit: TodoItem? by rememberSaveable {
        mutableStateOf(null)
    }
    var showEditTodoDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        // show TodoItems from the ViewModel in a LayzColumn
        if (todoList.isEmpty()) {
            Text(text = "No items")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(todoList) {
                    TodoCard(it,
                        onTodoCheckChange = { checkValue ->
                            todoViewModel.changeTodoState(it, checkValue)
                        },
                        onRemoveItem = { todoViewModel.removeTodoItem(it) },
                        onEditItem = {
                            todoToEdit = it
                            showEditTodoDialog = true
                        }
                    )
                }
            }

            if (showEditTodoDialog) {
                AddNewTodoDialog(todoViewModel = todoViewModel,
                    todoToEdit = todoToEdit,
                    ) {
                    showEditTodoDialog = false
                }
            }
        }
    }
}

@Composable
fun TodoCard(
    todoItem: TodoItem,
    onTodoCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {},
    onEditItem: (TodoItem) -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

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
                .padding(20.dp)
                .animateContentSize()
        ) {


            Row(
                modifier = Modifier
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
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        onEditItem(todoItem)
                    },
                    tint = Color.Blue
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
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else
                            Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            "Less"
                        } else {
                            "More"
                        }
                    )
                }
            }

            if (expanded) {
                Text(text = todoItem.description)
            }
        }
    }
}

@Composable
fun AddNewTodoDialog(
    todoViewModel: TodoViewModel,
    todoToEdit: TodoItem? = null,
    onDismissRequest: () -> Unit
) {
    var todoTitle by rememberSaveable {
        mutableStateOf(todoToEdit?.title ?: "")
    }
    var todoDescription by rememberSaveable {
        mutableStateOf(todoToEdit?.description ?: "")
    }
    var todoImportant by rememberSaveable {
        mutableStateOf(
            if (todoToEdit != null) {
                todoToEdit.priority == TodoPriority.HIGH
            } else {
                false
            }
        )
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Text(
                    text = if (todoToEdit == null)  "Add Todo" else "Edit Todo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )


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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = todoImportant, onCheckedChange = { todoImportant = it })
                    Text(text = "Important")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        if (todoToEdit == null) {
                            todoViewModel.addTodoList(
                                TodoItem(
                                    title = todoTitle,
                                    description = todoDescription,
                                    createDate = Date(System.currentTimeMillis()).toString(),
                                    priority = if (todoImportant) TodoPriority.HIGH else TodoPriority.NORMAL,
                                    isDone = false
                                )
                            )
                        } else {
                            val editedTodo = todoToEdit.copy(
                                title = todoTitle,
                                description = todoDescription,
                                priority = if (todoImportant)
                                    TodoPriority.HIGH else TodoPriority.NORMAL,
                            )
                            todoViewModel.editTodoItem(editedTodo)
                        }
                        onDismissRequest()
                    }) {
                        Text(text = "Save")
                    }
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancel")
                    }
                }

            }
        }
    }
}




