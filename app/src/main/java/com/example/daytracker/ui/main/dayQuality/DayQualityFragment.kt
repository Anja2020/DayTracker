package com.example.daytracker.ui.main.dayQuality

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.daytracker.R
import com.example.daytracker.databinding.DayQualityFragmentBinding
import com.example.daytracker.ui.main.database.DayDatabase
import java.time.LocalDate

class DayQualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DayQualityFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.day_quality_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getInstance(application).dayDatabaseDao

        val viewModelFactory = DayQualityViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(DayQualityViewModel::class.java)

        binding.dayQualityViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToDayTracking.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    DayQualityFragmentDirections.actionDayQualityToDayTracking()
                )
                viewModel.doneNavigation()
            }
        })

        val iconClickListener = View.OnClickListener { view ->
            val datePicker = binding.datePicker
            val year = datePicker.year
            val day = datePicker.dayOfMonth
            val month = datePicker.month
            val selectedDate = LocalDate.of(year, month+1, day)
                //Calendar.getInstance().set(year, month, day)
            val quality = when(view.id) {
                R.id.image_dissatisfied -> 1
                R.id.image_neutral -> 2
                else -> 3
            }
            Log.i("DayTracker", "year ${selectedDate}")
            viewModel.onAddDay(quality, selectedDate)
        }

        binding.imageDissatisfied.setOnClickListener(iconClickListener)
        binding.imageNeutral.setOnClickListener(iconClickListener)
        binding.imageSatisfied.setOnClickListener(iconClickListener)

        return binding.root
    }

}