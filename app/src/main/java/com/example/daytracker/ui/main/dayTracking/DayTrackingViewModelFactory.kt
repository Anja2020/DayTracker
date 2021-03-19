package com.example.daytracker.ui.main.dayTracking

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daytracker.ui.main.database.DayDatabaseDao

class DayTrackingViewModelFactory(
    private val dataSource: DayDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DayTrackingViewModel::class.java)) {
            return DayTrackingViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}