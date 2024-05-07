package com.ninja.hobbify.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,
    val description: String,
    val startTime: String,
    val imageId: Int
) : Parcelable
