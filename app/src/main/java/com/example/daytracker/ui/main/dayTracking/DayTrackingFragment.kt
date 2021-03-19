package com.example.daytracker.ui.main.dayTracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.daytracker.R
import com.example.daytracker.databinding.DayTrackingFragmentBinding
import com.example.daytracker.ui.main.database.DayDatabase

class DayTrackingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: DayTrackingFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.day_tracking_fragment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        // Create ViewModelFactory and get a reference to ViewModel
        val dataSource = DayDatabase.getInstance(application).dayDatabaseDao
        val viewModelFactory = DayTrackingViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DayTrackingViewModel::class.java)

        binding.dayTrackingViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Set up Adapter
        val adapter = DayAdapter(DayQualityListener { dayId ->
            Toast.makeText(context, "Day deleted!", Toast.LENGTH_LONG).show()
            viewModel.onDeleteDayClicked(dayId)
        })

        binding.recyclerView.adapter = adapter

        // Observe LiveData form ViewModel and bind it to Adapter
        viewModel.days.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        // Observe navigation from DayTracker to DayQuality
        viewModel.navigateToDayQuality.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    DayTrackingFragmentDirections.actionDayTrackingToDayQuality()
                )
                viewModel.doneNavigation()
            }
        })

        // Adds a [DividerItemDecoration] between items
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        return binding.root
    }

}