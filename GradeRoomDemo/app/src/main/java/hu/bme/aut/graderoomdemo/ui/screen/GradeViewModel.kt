package hu.bme.aut.graderoomdemo.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.graderoomdemo.data.AppDatabase
import hu.bme.aut.graderoomdemo.data.Grade
import hu.bme.aut.graderoomdemo.data.GradeDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradeViewModel @Inject constructor(
    private val gradeDAO: GradeDAO
) : ViewModel() {

    fun addGrade(grade: Grade) {
        viewModelScope.launch(Dispatchers.IO) {
            gradeDAO.insert(grade)
        }
    }

    fun getAllGrades() : Flow<List<Grade>> {
        return gradeDAO.getAllGrades()
    }

    fun deleteAllGrades() {
        viewModelScope.launch(Dispatchers.IO) {
            gradeDAO.deleteAllGrades()
        }
    }
}