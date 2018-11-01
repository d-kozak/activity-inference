package io.dkozak.inference.activity.activityinference.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.dkozak.inference.activity.activityinference.entity.InferenceResult

@Dao
interface InferenceResultDao {

    @Insert
    fun insert(inferenceResult: InferenceResult)

    @Query("select * from InferenceResult")
    fun getAll(): LiveData<List<InferenceResult>>

    @Query("select * from InferenceResult")
    fun getAllNow(): List<InferenceResult>
}