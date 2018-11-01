package io.dkozak.inference.activity.activityinference

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.dkozak.inference.activity.activityinference.dao.InferenceResultDao
import io.dkozak.inference.activity.activityinference.entity.InferenceResult


class InferenceResultViewModel(application: Application) : AndroidViewModel(application) {

    val inferenceResults: LiveData<List<InferenceResult>>

    private val inferenceResultDao: InferenceResultDao

    init {
        val database = ActivityInferenceDatabase.getInstance(application.applicationContext)
        inferenceResultDao = database.inferenceResultDao()
        inferenceResults = inferenceResultDao.getAll()
    }

}

