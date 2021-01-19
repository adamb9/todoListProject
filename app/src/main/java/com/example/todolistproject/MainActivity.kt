package com.example.todolistproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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

        val dbTasks: List<Task>? = taskDao?.getUncompletedTasks()

        if (dbTasks != null) {
            for(task in dbTasks){
                addTasks(task)
            }
        }

        if(intent.extras != null) {
            val addedTask: Task? = intent.extras?.get("task") as Task
            addTasks(addedTask)
        }

        rv_task_list.layoutManager = LinearLayoutManager( this )

        rv_task_list.adapter = TaskAdapter(tasks, this)

        btn_new_task.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

    }

    fun addTasks(addedTask: Task? = null){
        if (addedTask != null) {
            tasks.add(addedTask)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.btn_filter) {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)

    }


}