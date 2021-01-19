package com.example.todolistproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_full_task.*

class FullTaskActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var taskDao: TaskDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_task)

        db = AppDatabase.getAppDataBase(context = this)
        taskDao = db?.taskDao()

        var selectedTask: Task? = null
        if(intent.extras != null) {
            selectedTask = intent.extras?.get("task") as Task
        }

        tv_fulltask_title.text = selectedTask?.name
        tv_fulltask_date_c.text = selectedTask?.dateCreated
        tv_fulltask_dated.text = selectedTask?.dateDue
        tv_fulltask_desc.text = selectedTask?.desc

        when(selectedTask?.priority){
            0 -> tv_fulltask_priority.text = "Low"
            1 -> tv_fulltask_priority.text = "Medium"
            2 -> tv_fulltask_priority.text = "High"
        }


        btn_fulltask_bin.setOnClickListener(){

            if (selectedTask != null) {
                taskDao?.deleteTask(selectedTask)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }


        btn_fulltask_tick.setOnClickListener(){
            selectedTask?.isCompleted = true
            if (selectedTask != null) {
                taskDao?.updateTask(selectedTask)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }


        btn_fulltask_back.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()


    }
}