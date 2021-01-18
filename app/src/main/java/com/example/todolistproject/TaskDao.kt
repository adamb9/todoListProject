package com.example.todolistproject

import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task : Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM Task WHERE name == :name")
    fun getTaskByName(name : String): List<Task>

    @Query("SELECT * FROM Task")
    fun getTasks(): List<Task>
}