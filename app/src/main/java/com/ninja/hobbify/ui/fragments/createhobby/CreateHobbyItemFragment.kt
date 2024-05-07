package com.ninja.hobbify.ui.fragments.createhobby


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ninja.hobbify.R
import com.ninja.hobbify.data.models.Habit
import com.ninja.hobbify.databinding.FragmentCreateHebitItemBinding
import com.ninja.hobbify.ui.viewmodels.HobbyViewModel
import com.ninja.hobbify.util.Calculation


/**
 * A simple [Fragment] subclass.
 * Use the [CreateHobbyItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateHobbyItemFragment : Fragment(),  TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private lateinit var habitViewModel: HobbyViewModel

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private lateinit var binding: FragmentCreateHebitItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateHebitItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)

        binding.btnConfirm.setOnClickListener{
            addHabitToDatabase()
        }

        //Pick a date and time
        pickDateAndTime()

        //Selected and image to put into our list
        drawableSelecteds()
    }

    private fun drawableSelecteds() {
        binding.ivFastFoodSelected.setOnClickListener {
            binding.ivFastFoodSelected.isSelected = !binding.ivFastFoodSelected.isSelected
            drawableSelected = R.drawable.camera_selected

            //de-select the other options when we pick an image
            binding.ivSmokingSelected.isSelected = false
            binding.ivTeaSelected.isSelected = false
        }

        binding.ivSmokingSelected.setOnClickListener {
            binding.ivSmokingSelected.isSelected = !binding.ivSmokingSelected.isSelected
            drawableSelected = R.drawable.books_selected

            //de-select the other options when we pick an image
            binding.ivFastFoodSelected.isSelected = false
            binding.ivTeaSelected.isSelected = false
        }

        binding.ivTeaSelected.setOnClickListener {
            binding.ivTeaSelected.isSelected = !binding.ivTeaSelected.isSelected
            drawableSelected = R.drawable.music_selected

            //de-select the other options when we pick an image
            binding.ivFastFoodSelected.isSelected = false
            binding.ivSmokingSelected.isSelected = false
        }
    }

    private fun pickDateAndTime() {
        binding.btnPickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.btnPickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }
    }

    private fun addHabitToDatabase() {
        //Get text from editTexts
        title = binding.etHabitTitle.text.toString()
        description = binding.etHabitDescription.text.toString()

        //Create a timestamp string for our recyclerview
        timeStamp = "$cleanDate $cleanTime"

        //Check that the form is complete before submitting data to the database
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val habit = Habit(0, title, description, timeStamp, drawableSelected)

            //add the habit if all the fields are filled
            habitViewModel.addHabits(habit)
            Toast.makeText(context, "Habit created successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_createHebitItemFragment_to_habitList)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }



    //get the current time
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    //get the current date
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    //get the time set
    override fun onTimeSet(TimePicker: TimePicker?, p1: Int, p2: Int) {
        Log.d("Fragment", "Time: $p1:$p2")

        cleanTime = Calculation.cleanTime(p1, p2)
        binding.tvTimeSelected.text = "Time: $cleanTime"
    }

    //get the date set
    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {

        cleanDate = Calculation.cleanDate(dayX, monthX, yearX)
        binding.tvDateSelected.text = "Date: $cleanDate"
    }
}