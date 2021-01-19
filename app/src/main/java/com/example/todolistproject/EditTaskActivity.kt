package com.example.todolistproject

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_edit_task.*
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var taskDao: TaskDao? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        db = AppDatabase.getAppDataBase(context = this)
        taskDao = db?.taskDao()

        var selectedTask: Task? = null
        if(intent.extras != null) {
            selectedTask = intent.extras?.get("task") as Task
        }

        et_edit_taskname.setText(selectedTask?.name, TextView.BufferType.EDITABLE)
        et_edit_taskdesc.setText(selectedTask?.desc, TextView.BufferType.EDITABLE)

        var priority: Int? = selectedTask?.priority
        sb_edit_priority.setProgress(priority!!)
        sb_edit_priority.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

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

        tv_edit_due_date.text = selectedTask?.dateDue

        var cal = Calendar.getInstance()


        var dueDate = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.UK)

            tv_edit_due_date.text = sdf.format(cal.time)
            dueDate = sdf.format(cal.time)
            println(dueDate)

        }

        tv_edit_due_date.setOnClickListener {
            DatePickerDialog(this@EditTaskActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        btn_confirm_edit_task.setOnClickListener(){
            val taskname = et_edit_taskname.text.toString()
            val taskdesc = et_edit_taskdesc.text.toString()

            selectedTask?.name = taskname
            selectedTask?.desc = taskdesc
            selectedTask?.priority = priority
            selectedTask?.dateDue = dueDate

            if (selectedTask != null) {
                taskDao?.updateTask(selectedTask)
                val intent = Intent(this, FullTaskActivity::class.java).apply {
                    putExtra("task", selectedTask)
                }

                startActivity(intent)
            }

        }

    }
}