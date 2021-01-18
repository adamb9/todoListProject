package com.example.todolistproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val tasks: ArrayList<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.extras != null) {
            val added_task: Task? = intent.extras?.get("task") as Task
            addTasks(added_task)
        }else{
            addTasks()
        }

        rv_task_list.layoutManager = LinearLayoutManager( this )

        rv_task_list.adapter = TaskAdapter(tasks, this)

        btn_new_task.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

    }

    fun addTasks(added_task: Task? = null){
        if (added_task != null) {
            tasks.add(added_task)
        }
        tasks.add(Task("Create tasks app", 0, "Test out your kotlin skills by creating a tasks app"))
        tasks.add(Task("Test App", 1, "Test out the app you made"))
        tasks.add(Task("Connect App to DB", 2, "Further develop the tasks app to connect it to a DB"))
        tasks.add(Task("Change Theme and Style", 1, "Make the app beautiful!"))

    }

}