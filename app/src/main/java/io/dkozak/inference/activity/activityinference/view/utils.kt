package io.dkozak.inference.activity.activityinference.view

import android.content.Context
import com.google.android.gms.location.DetectedActivity
import io.dkozak.inference.activity.activityinference.R
import io.dkozak.inference.activity.activityinference.entity.InferenceResult


internal fun InferenceResult.getVisualRepresentation(context: Context) = getVisualRepresentationFor(this.type, context)

internal fun getVisualRepresentationFor(type: Int, context: Context): Pair<String, Int> {
    var label = context.getString(R.string.activity_unknown)
    var icon = R.drawable.ic_still

    when (type) {
        DetectedActivity.IN_VEHICLE -> {
            label = context.getString(R.string.activity_in_vehicle)
            icon = R.drawable.ic_driving
        }
        DetectedActivity.ON_BICYCLE -> {
            label = context.getString(R.string.activity_on_bicycle)
            icon = R.drawable.ic_on_bicycle
        }
        DetectedActivity.ON_FOOT -> {
            label = context.getString(R.string.activity_on_foot)
            icon = R.drawable.ic_walking
        }
        DetectedActivity.RUNNING -> {
            label = context.getString(R.string.activity_running)
            icon = R.drawable.ic_running
        }
        DetectedActivity.STILL -> {
            label = context.getString(R.string.activity_still)
        }
        DetectedActivity.TILTING -> {
            label = context.getString(R.string.activity_tilting)
            icon = R.drawable.ic_tilting
        }
        DetectedActivity.WALKING -> {
            label = context.getString(R.string.activity_walking)
            icon = R.drawable.ic_walking
        }
        DetectedActivity.UNKNOWN -> {
            label = context.getString(R.string.activity_unknown)
        }
    }
    return label to icon
}

