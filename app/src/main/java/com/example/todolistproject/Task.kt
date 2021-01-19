package com.example.todolistproject

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.OffsetDateTime

@Entity
data class Task(
        @PrimaryKey(autoGenerate = true) val id: Int? = null,
        val name: String,
        val priority: Int? = 0,
        val desc: String,
        val dateCreated: String,
        val dateDue: String,
        val dateCompleted: String? = null,
        var isCompleted: Boolean = false
) : Serializable