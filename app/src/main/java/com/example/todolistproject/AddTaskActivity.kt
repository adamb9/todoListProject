package com.example.todolistproject

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var taskDao: TaskDao? = null

    @RequiresApi(Build.VERSION_CODES.N)
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


        tv_due_date.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        val currentDate = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        println("///////////////")
        println(currentDate)

        var cal = Calendar.getInstance()


        var dueDate = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            tv_due_date.text = sdf.format(cal.time)
            dueDate = sdf.format(cal.time)
            println(dueDate)

        }

        tv_due_date.setOnClickListener {
            DatePickerDialog(this@AddTaskActivity, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }



        btn_add_task.setOnClickListener{
            val taskname = et_new_taskname.text.toString()
            val taskdesc = et_new_taskdesc.text.toString()

            val new_task = Task(name = taskname, priority = priority, desc = taskdesc, dateCreated = currentDate, dateDue = dueDate)
            taskDao?.insertTask(new_task)
            println(new_task.name)
            println(new_task.desc)
            println(new_task.priority)
            println(new_task.dateCreated)
            println(new_task.dateDue)

            val intent = Intent(this, MainActivity::class.java)
                /*.apply {
                putExtra("task", new_task)
            }*/
            startActivity(intent)

        }
    }
}