package com.steft.travel_app.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.steft.travel_app.dao.BundleDao
import com.steft.travel_app.dto.Converters
import com.steft.travel_app.dao.TravelAgencyDao
import com.steft.travel_app.model.Bundle
import com.steft.travel_app.model.CustomLocation
import com.steft.travel_app.model.Location
import com.steft.travel_app.model.TravelAgency

/**
 * Singleton pattern implemented for room database object
 */
@Database(entities = [TravelAgency::class, Location::class, Bundle::class, CustomLocation::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun travelAgencyDao(): TravelAgencyDao
    abstract fun locationDao(): LocationDao
    abstract fun bundleDao(): BundleDao

    companion object {
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, AppDatabase::class.java, "travel_app")
                            .createFromAsset("database/travel_app")
                            .build()
                }
            }
            return instance!!
        }
    }
}


/**
 * Firebase uses singleton pattern internally
 */
fun firebaseDb() = Firebase.firestore