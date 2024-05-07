package com.ninja.hobbify.logic.repository

import androidx.lifecycle.LiveData
import com.ninja.hobbify.data.models.Habit
import com.ninja.hobbify.logic.dao.HabitDao

class HabitsRepository(private val habitDao: HabitDao) {

    val getAllHabits: LiveData<List<Habit>> = habitDao.getAllHabits()

    suspend fun addHabit(habit: Habit) {
        habitDao.addHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit){
        habitDao.deleteHabit(habit)
    }

    suspend fun deleteAllHabit(){
        habitDao.deleteAllHabits()
    }
}