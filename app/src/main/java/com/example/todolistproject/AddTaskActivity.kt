package com.example.todolistproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var taskDao: TaskDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        db = AppDatabase.getAppDataBase(context = this)
        taskDao = db?.taskDao()

        var priority: Int? = 0
        sb_priority.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                priority = i
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
        })

        btn_add_task.setOnClickListener{
            val taskname = et_new_taskname.text.toString()
            val taskdesc = et_new_taskdesc.text.toString()

            val new_task = Task(name = taskname, priority = priority, desc = taskdesc)
            taskDao?.insertTask(new_task)
            println(new_task.name)
            println(new_task.desc)
            println(new_task.priority)

            val intent = Intent(this, MainActivity::class.java)
                /*.apply {
                putExtra("task", new_task)
            }*/
            startActivity(intent)

        }
    }
}