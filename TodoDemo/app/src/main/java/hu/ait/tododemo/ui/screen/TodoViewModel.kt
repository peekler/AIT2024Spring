package hu.ait.tododemo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.tododemo.data.TodoDAO
import hu.ait.tododemo.data.TodoItem
import hu.ait.tododemo.data.TodoPriority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    val todoDAO: TodoDAO
) : ViewModel() {

    fun getAllToDoList(): Flow<List<TodoItem>> {
        return todoDAO.getAllTodos()
    }

    suspend fun getAllTodoNum(): Int {
        return todoDAO.getTodosNum()
    }

    suspend fun getImportantTodoNum(): Int {
        return todoDAO.getImportantTodosNum()
    }

    fun addTodoList(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.insert(todoItem)
        }
    }


    fun removeTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.delete(todoItem)
        }
    }

    fun editTodoItem(editedTodo: TodoItem) {
        viewModelScope.launch {
            todoDAO.update(editedTodo)
        }
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val updatedTodo = todoItem.copy()
        updatedTodo.isDone = value
        viewModelScope.launch {
            todoDAO.update(updatedTodo)
        }
    }

    fun clearAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.deleteAllTodos()
        }
    }

}