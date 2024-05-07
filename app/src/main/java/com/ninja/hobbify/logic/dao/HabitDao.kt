package com.ninja.hobbify.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ninja.hobbify.data.models.Habit

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
      fun addHabit(habit: Habit)

    @Update
     fun updateHabit(habit: Habit)

    @Delete
     fun deleteHabit(habit: Habit)

    @Query("SELECT * from habit_table order by id desc")
    fun getAllHabits(): LiveData<List<Habit>>

    @Query("DELETE FROM habit_table")
     fun deleteAllHabits()

}