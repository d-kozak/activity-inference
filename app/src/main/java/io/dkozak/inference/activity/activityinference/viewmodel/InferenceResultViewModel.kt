package io.dkozak.inference.activity.activityinference.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.dkozak.inference.activity.activityinference.ActivityInferenceDatabase
import io.dkozak.inference.activity.activityinference.dao.InferenceResultDao
import io.dkozak.inference.activity.activityinference.entity.InferenceResult


class InferenceResultViewModel(application: Application) : AndroidViewModel(application) {

    private val inferenceResultDao: InferenceResultDao =
        ActivityInferenceDatabase.getInstance(application).inferenceResultDao()

    val inferenceResults: LiveData<List<InferenceResult>> = inferenceResultDao.getAll()
    
}

