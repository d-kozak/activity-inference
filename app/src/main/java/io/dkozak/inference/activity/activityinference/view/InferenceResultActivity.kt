package io.dkozak.inference.activity.activityinference.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.dkozak.inference.activity.activityinference.R
import io.dkozak.inference.activity.activityinference.viewmodel.InferenceResultViewModel

class InferenceResultActivity : AppCompatActivity() {

    private lateinit var inferenceResultViewModel: InferenceResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inference_result)
        inferenceResultViewModel = ViewModelProviders.of(this).get(InferenceResultViewModel::class.java)

        val inferenceListRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val inferenceListAdapter = InferenceListAdapter(this)
        inferenceListRecyclerView.layoutManager = LinearLayoutManager(this)
        inferenceListRecyclerView.adapter = inferenceListAdapter

        inferenceResultViewModel.inferenceResults.observe(this, Observer { newResults ->
            inferenceListAdapter.inferenceResults = newResults
        })

    }

}
