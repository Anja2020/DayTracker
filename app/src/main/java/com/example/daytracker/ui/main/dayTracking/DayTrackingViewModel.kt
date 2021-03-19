package com.example.daytracker.ui.main.dayTracking

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.daytracker.ui.main.database.DayDatabaseDao
import com.example.daytracker.ui.main.database.DayQuality
import kotlinx.coroutines.launch
import java.time.LocalDate

class DayTrackingViewModel(
    val database: DayDatabaseDao,
    val application: Application
) : ViewModel() {

    val days: LiveData<List<DayQuality>> = database.getAllDays()
    private var recordedToday = MutableLiveData<DayQuality>()

    val addButtonVisible = Transformations.map(recordedToday) {
        // it != null
        true
    }

    val deleteButtonVisible = Transformations.map(days) {
        it?.isNotEmpty()
    }


    init {
        viewModelScope.launch {
            recordedToday.value = getLatestDayFromDB()
        }
    }

    fun onDeleteDayClicked(dayId: Long) {
        viewModelScope.launch {
            deleteDay(dayId)
        }
    }

    private suspend fun getLatestDayFromDB(): DayQuality? {
        var day = database.getCurrentDay()
        Log.i(
            "DayTracking",
            "record Time: ${day?.recordTime} and system time: ${System.currentTimeMillis()}"
        )
        return if (day?.recordTime != LocalDate.of(2020, 3,16)) {
            null
        } else {
            day
        }
    }

    private val _navigateToDayQuality = MutableLiveData<Boolean>()
    val navigateToDayQuality: LiveData<Boolean>
        get() = _navigateToDayQuality

    fun onAddDay() {
        _navigateToDayQuality.value = true
    }

    fun onDeleteAll() {
        viewModelScope.launch {
            deleteAll()
            recordedToday.value = null
        }
    }

    private suspend fun deleteAll() {
        database.clear()
    }

    private suspend fun deleteDay(id: Long) {
        database.clear(id)
    }


    fun doneNavigation() {
        _navigateToDayQuality.value = false
    }
}