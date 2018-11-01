package io.dkozak.inference.activity.activityinference.entity

import androidx.room.TypeConverter
import java.sql.Date


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

