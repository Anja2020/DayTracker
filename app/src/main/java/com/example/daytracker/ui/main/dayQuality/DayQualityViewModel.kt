package com.example.daytracker.ui.main.dayQuality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daytracker.ui.main.database.DayDatabaseDao
import com.example.daytracker.ui.main.database.DayQuality
import kotlinx.coroutines.launch
import java.time.LocalDate

class DayQualityViewModel(private val database: DayDatabaseDao) : ViewModel() {

    private val _navigateToDayTracking = MutableLiveData<Boolean>()
    val navigateToDayTracking: LiveData<Boolean>
        get() = _navigateToDayTracking

    fun onNavigateBack() {
        _navigateToDayTracking.value = true
    }

    fun doneNavigation() {
        _navigateToDayTracking.value = false
    }

    fun onAddDay(quality: Int, date: LocalDate?) {
        viewModelScope.launch {
            val newDay = DayQuality(dayQuality = quality, recordTime = date)
            database.insert(newDay)

            // Trigger Navigation back
            _navigateToDayTracking.value = true
        }
    }
}