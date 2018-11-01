package io.dkozak.inference.activity.activityinference.entity

import androidx.room.TypeConverter
import java.sql.Timestamp


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long): Timestamp = Timestamp(value)

    @TypeConverter
    fun dateToTimestamp(timestamp: Timestamp): Long = timestamp.time
}

