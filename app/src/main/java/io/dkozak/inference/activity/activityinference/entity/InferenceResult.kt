package io.dkozak.inference.activity.activityinference.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
class InferenceResult {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var type: Int = -1

    var confidence: Int = 0

    var timestamp: Timestamp = Timestamp(System.currentTimeMillis())

    override fun toString(): String {
        return "InferenceResult(id=$id, type=$type, confidence=$confidence, timestamp=$timestamp)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InferenceResult

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}