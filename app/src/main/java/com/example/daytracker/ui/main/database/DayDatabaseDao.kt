package com.example.daytracker.ui.main.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DayDatabaseDao {

    @Insert
    suspend fun insert(day: DayQuality)

    @Update
    suspend fun update(day: DayQuality)

    @Query("SELECT * FROM daily_quality_table WHERE dayId = :key")
    suspend fun get(key: Long): DayQuality?

    @Query("SELECT * FROM daily_quality_table ORDER BY dayId DESC LIMIT 1")
    suspend fun getCurrentDay(): DayQuality?

    @Query("SELECT * FROM daily_quality_table ORDER BY record_time DESC")
    fun getAllDays(): LiveData<List<DayQuality>>

    @Query("DELETE FROM daily_quality_table WHERE dayId = :key")
    suspend fun clear(key: Long)

    @Query("DELETE FROM daily_quality_table")
    suspend fun clear()

}