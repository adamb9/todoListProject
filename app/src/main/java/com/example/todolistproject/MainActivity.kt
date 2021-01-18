package com.example.todolistproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val tasks: ArrayList<Task> = ArrayList()
    private var db: AppDatabase? = null
    private var taskDao: TaskDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getAppDataBase(context = this)
        taskDao = db?.taskDao()

        val dbTasks: List<Task>? = taskDao?.getTasks()

        if (dbTasks != null) {
            for(task in dbTasks){
                addTasks(task)
            }
        }

        if(intent.extras != null) {
            val added_task: Task? = intent.extras?.get("task") as Task
            addTasks(added_task)
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
        /*tasks.add(Task(name = "Create tasks app", priority = 0, desc = "Test out your kotlin skills by creating a tasks app"))
        tasks.add(Task(name = "Test App", priority = 1, desc = "Test out the app you made"))
        tasks.add(Task(name = "Connect App to DB", priority = 2, desc = "Further develop the tasks app to connect it to a DB"))
        tasks.add(Task(name = "Change Theme and Style", priority = 1, desc = "Make the app beautiful!"))
        */
    }

}