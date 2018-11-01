package io.dkozak.inference.activity.activityinference

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.dkozak.inference.activity.activityinference.dao.InferenceResultDao
import io.dkozak.inference.activity.activityinference.entity.Converters
import io.dkozak.inference.activity.activityinference.entity.InferenceResult


@Database(entities = [InferenceResult::class], version = 1)
@TypeConverters(Converters::class)
abstract class ActivityInferenceDatabase : RoomDatabase() {

    abstract fun inferenceResultDao(): InferenceResultDao

    companion object {
        private var INSTANCE: ActivityInferenceDatabase? = null

        fun getInstance(context: Context): ActivityInferenceDatabase {
            if (INSTANCE == null) {
                synchronized(ActivityInferenceDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ActivityInferenceDatabase::class.java,
                            "ActivityInferenceDatabase"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}


