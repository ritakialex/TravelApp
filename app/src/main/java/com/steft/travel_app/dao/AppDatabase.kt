package com.steft.travel_app.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.steft.travel_app.model.Bundle
import com.steft.travel_app.model.TravelAgency
import com.steft.travel_app.model.TravelAgent

@Database(entities = [TravelAgency::class, TravelAgent::class, Bundle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun travelAgencyDao(): TravelAgencyDao
    abstract fun travelAgentDao(): TravelAgentDao
    abstract fun bundleDao(): BundleDao

    companion object {
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context,AppDatabase::class.java, "travel_app")
                            .build()
                }
            }
            return instance!!
        }
    }
}