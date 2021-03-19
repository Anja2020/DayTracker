package com.example.daytracker.ui.main.dayQuality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daytracker.ui.main.database.DayDatabaseDao

class DayQualityViewModelFactory(private val dataSource: DayDatabaseDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DayQualityViewModel::class.java)) {
            return DayQualityViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}