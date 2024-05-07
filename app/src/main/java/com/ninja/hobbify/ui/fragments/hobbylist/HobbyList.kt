package com.ninja.hobbify.ui.fragments.hobbylist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ninja.hobbify.R
import com.ninja.hobbify.data.models.Habit
import com.ninja.hobbify.databinding.FragmentHabitListBinding
import com.ninja.hobbify.ui.fragments.hobbylist.adapters.HobbyListAdapter
import com.ninja.hobbify.ui.viewmodels.HobbyViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HobbyList.newInstance] factory method to
 * create an instance of this fragment.
 */
class HobbyList : Fragment(R.layout.fragment_habit_list) {

    private lateinit var fragmentHabitListBinding: FragmentHabitListBinding

    private lateinit var habitList: List<Habit>
    private lateinit var habitViewModel: HobbyViewModel
    private lateinit var adapter: HobbyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHabitListBinding = FragmentHabitListBinding.inflate(inflater, container, false)
        return fragmentHabitListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = HobbyListAdapter()
        fragmentHabitListBinding.rvHabits.adapter = adapter
        fragmentHabitListBinding.rvHabits.layoutManager = LinearLayoutManager(context)

        habitViewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)

        habitViewModel.getAllHabits.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            habitList = it

            if(it.isEmpty()) {
                fragmentHabitListBinding.tvEmptyView.visibility = View.VISIBLE
                fragmentHabitListBinding.rvHabits.visibility = View.GONE
            }else {
                fragmentHabitListBinding.tvEmptyView.visibility = View.GONE
                fragmentHabitListBinding.rvHabits.visibility = View.VISIBLE

            }
        })

        setHasOptionsMenu(true)

        fragmentHabitListBinding.swipeToRefresh.setOnRefreshListener {
            adapter.setData(habitList)
            fragmentHabitListBinding.swipeToRefresh.isRefreshing = false
        }

        fragmentHabitListBinding.fabAdd.setOnClickListener{
            findNavController().navigate(R.id.action_habitList_to_createHebitItemFragment)
        }


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete_habit -> {
                habitViewModel.deleteAllHabit()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}