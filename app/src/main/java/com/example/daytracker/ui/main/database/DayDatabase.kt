package com.example.daytracker.ui.main.database

import android.content.Context
import androidx.room.*
import java.time.LocalDate

@Entity(tableName = "daily_quality_table")
data class DayQuality(
    @PrimaryKey(autoGenerate = true)
    var dayId: Long = 0L,

    @ColumnInfo(name = "record_time")
    val recordTime: LocalDate?,

    @ColumnInfo(name = "day_quality")
    var dayQuality: Int = -1

)

@Database(entities = [DayQuality::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DayDatabase : RoomDatabase() {

    abstract val dayDatabaseDao: DayDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: DayDatabase? = null

        fun getInstance(context: Context): DayDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DayDatabase::class.java,
                        "day_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}