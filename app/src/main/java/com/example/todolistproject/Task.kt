package com.example.todolistproject

import java.io.Serializable

data class Task(val name: String, val priority: Int? = 0, val desc: String) : Serializable