package com.ninja.hobbify.ui.fragments.hobbylist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ninja.hobbify.data.models.Habit
import com.ninja.hobbify.databinding.RecyclerHabitItemBinding
import com.ninja.hobbify.ui.fragments.hobbylist.HobbyListDirections
import com.ninja.hobbify.util.Calculation

class HobbyListAdapter : RecyclerView.Adapter<HobbyListAdapter.HabitViewHolder>() {

    var habits = emptyList<Habit>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HobbyListAdapter.HabitViewHolder {
        val binding = RecyclerHabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HobbyListAdapter.HabitViewHolder, position: Int) {
       var currentHabit = habits[position]
        holder.binding.ivHabitIcon.setImageResource(currentHabit.imageId)
        holder.binding.tvItemDescription.text = currentHabit.description
        holder.binding.tvTimeElapsed.text = Calculation.calculateTimeBetweenDates(currentHabit.startTime)
        holder.binding.tvItemCreatedTimeStamp.text = "Since: ${currentHabit.startTime}"
        holder.binding.tvItemTitle.text = currentHabit.title
    }

    override fun getItemCount(): Int {
       return habits.size
    }

    fun setData(habit: List<Habit>) {
this.habits = habit
        notifyDataSetChanged()
    }

    inner  class HabitViewHolder (val binding: RecyclerHabitItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
           binding.cvCardView.setOnClickListener {
               val position = adapterPosition
               if (position != RecyclerView.NO_POSITION) {
                   val habit = habits[position]
                   val action = HobbyListDirections.actionHabitListToUpdateHabitItemFragment(habit)
                   binding.root.findNavController().navigate(action)
               }
           }
        }
    }

    }