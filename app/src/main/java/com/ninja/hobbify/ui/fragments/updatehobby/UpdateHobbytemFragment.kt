package com.ninja.hobbify.ui.fragments.updatehobby


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ninja.hobbify.R
import com.ninja.hobbify.data.models.Habit
import com.ninja.hobbify.databinding.FragmentUpdateHabitItemBinding
import com.ninja.hobbify.ui.viewmodels.HobbyViewModel
import com.ninja.hobbify.util.Calculation

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateHobbytemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateHobbytemFragment : Fragment() , TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private  lateinit var binding:FragmentUpdateHabitItemBinding


    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private val args by navArgs<UpdateHobbytemFragmentArgs>()

    private lateinit var habitViewModel: HobbyViewModel

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUpdateHabitItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        habitViewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)

        binding.etHabitTitleUpdate.setText(args.selectedHabit.title)
        binding.etHabitDescriptionUpdate.setText(args.selectedHabit.description)


        //Pick a drawable
        drawableSelected()

        //Pick the date and time again
        pickDateAndTime()

        //Confirm changes and update the selected item
        binding.btnConfirmUpdate.setOnClickListener {
            updateHabit()
        }

        //Show the options menu in this fragment
        setHasOptionsMenu(true)


    }


    private fun updateHabit() {
        //Get text from editTexts
        title = binding.etHabitTitleUpdate.text.toString()
        description = binding.etHabitDescriptionUpdate.text.toString()

        //Create a timestamp string for our recyclerview
        timeStamp = "$cleanDate $cleanTime"

        //Check that the form is complete before submitting data to the database
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val habit =
                Habit(args.selectedHabit.id, title, description, timeStamp, drawableSelected)

            //add the habit if all the fields are filled
            habitViewModel.updateHabit(habit)
            Toast.makeText(context, "Habit updated! successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_updateHabitItemFragment_to_habitList)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    // Create a selector for our icons which will appear in the recycler view
    private fun drawableSelected() {
        binding.ivFastFoodSelectedUpdate.setOnClickListener {
            binding.ivFastFoodSelectedUpdate.isSelected = !binding.ivFastFoodSelectedUpdate.isSelected
            drawableSelected = R.drawable.camera_selected

            //de-select the other options when we pick an image
            binding.ivSmokingSelectedUpdate.isSelected = false
            binding.ivTeaSelectedUpdate.isSelected = false
        }

        binding.ivSmokingSelectedUpdate.setOnClickListener {
            binding.ivSmokingSelectedUpdate.isSelected = !binding.ivSmokingSelectedUpdate.isSelected
            drawableSelected = R.drawable.books_selected

            //de-select the other options when we pick an image
            binding.ivFastFoodSelectedUpdate.isSelected = false
            binding.ivTeaSelectedUpdate.isSelected = false
        }

        binding.ivTeaSelectedUpdate.setOnClickListener {
            binding.ivTeaSelectedUpdate.isSelected = !binding.ivTeaSelectedUpdate.isSelected
            drawableSelected = R.drawable.music_selected

            //de-select the other options when we pick an image
            binding.ivFastFoodSelectedUpdate.isSelected = false
            binding.ivSmokingSelectedUpdate.isSelected = false
        }
    }

    //Handle date and time picking
    @RequiresApi(Build.VERSION_CODES.N)
    //set on click listeners for our data and time pickers
    private fun pickDateAndTime() {
       binding.btnPickDateUpdate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.btnPickTimeUpdate.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }

    }

    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        cleanTime = Calculation.cleanTime(p1, p2)
        binding.tvTimeSelectedUpdate.text = "Time: $cleanTime"
    }

    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculation.cleanDate(dayX, monthX, yearX)
        binding.tvDateSelectedUpdate.text = "Date: $cleanDate"
    }

    //Create options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_single_habit -> {
                deleteHabit(args.selectedHabit)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //------------------------------------------

    //Delete a single Habit
    private fun deleteHabit(habit: Habit) {
        habitViewModel.deleteHabit(habit)
        Toast.makeText(context, "Habit successfully deleted!", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_updateHabitItemFragment_to_habitList)
    }
}