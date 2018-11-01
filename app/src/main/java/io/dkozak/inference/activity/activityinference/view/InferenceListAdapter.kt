package io.dkozak.inference.activity.activityinference.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.dkozak.inference.activity.activityinference.R
import io.dkozak.inference.activity.activityinference.entity.InferenceResult


class InferenceListAdapter(private val context: Context) :
    RecyclerView.Adapter<InferenceListAdapter.InferenceListViewHolder>() {

    class InferenceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val imageView = itemView.findViewById<ImageView>(R.id.inferenceImageView)
        internal val typeTextView = itemView.findViewById<TextView>(R.id.typeTextView)
        internal val confidenceTextView = itemView.findViewById<TextView>(R.id.confidenceTextView)
        internal val timeTextView = itemView.findViewById<TextView>(R.id.timeTextView)
    }


    private val inflater = LayoutInflater.from(context)

    internal var inferenceResults: List<InferenceResult> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InferenceListViewHolder {
        val view = inflater.inflate(R.layout.inference_list, parent, false)
        return InferenceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: InferenceListViewHolder, position: Int) {
        val inferenceResult = inferenceResults[position]
        val (label, icon) = inferenceResult.getVisualRepresentation(context)
        holder.imageView.setImageResource(icon)
        holder.typeTextView.text = "Activity: $label"
        holder.confidenceTextView.text = "Confidence: ${inferenceResult.confidence}"
        holder.timeTextView.text = "Time: ${inferenceResult.timestamp}"

    }

    override fun getItemCount(): Int = inferenceResults.size
}

