package hu.bme.aut.graderoomdemo.ui.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.graderoomdemo.data.Grade

import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeScreen(
    gradeViewModel: GradeViewModel = hiltViewModel()
) {
    var studentName by rememberSaveable { mutableStateOf("") }
    var grade by rememberSaveable { mutableStateOf("") }

    val gradesList by gradeViewModel.getAllGrades().collectAsState(emptyList())

    Column {
        OutlinedTextField(
            label = { Text(text = "Student name") },
            value = studentName,
            onValueChange = {
                studentName = it
            })
        OutlinedTextField(
            label = { Text(text = "Grade") },
            value = grade,
            onValueChange = {
                grade = it
            })

        Button(onClick = {
            gradeViewModel.deleteAllGrades()
        }) {
            Text(text = "Delete all")
        }

        Button(onClick = {
            gradeViewModel.addGrade(
                Grade(0, studentName, grade)
            )
        }) {
            Text(text = "Save")
        }

        LazyColumn {
            items(gradesList) {
                Text(
                    text = "${it.studentName} - ${it.grade}",
                    fontSize = 22.sp
                )
            }
        }
    }
}