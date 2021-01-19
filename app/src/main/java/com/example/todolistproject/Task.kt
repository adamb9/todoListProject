package com.example.todolistproject

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.OffsetDateTime

@Entity
data class Task(
        @PrimaryKey(autoGenerate = true) val id: Int? = null,
        var name: String,
        var priority: Int? = 0,
        var desc: String,
        val dateCreated: String,
        var dateDue: String,
        var dateCompleted: String? = null,
        var isCompleted: Boolean = false
) : Serializable