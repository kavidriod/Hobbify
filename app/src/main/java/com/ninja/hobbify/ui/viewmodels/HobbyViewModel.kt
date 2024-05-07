package com.ninja.hobbify.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ninja.hobbify.data.database.HabitDatabase
import com.ninja.hobbify.data.models.Habit
import com.ninja.hobbify.logic.repository.HabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HobbyViewModel(application: Application) : AndroidViewModel(application){
    private val repository: HabitsRepository
    val getAllHabits: LiveData<List<Habit>>

    init {
        val habitDao= HabitDatabase.getDatabase(application).habitDao()
        repository = HabitsRepository(habitDao)

        getAllHabits = repository.getAllHabits
    }

    fun addHabits(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(habit)
        }
    }


    fun updateHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHabit(habit)
        }
    }

    fun deleteAllHabit(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHabit()
        }
    }
}