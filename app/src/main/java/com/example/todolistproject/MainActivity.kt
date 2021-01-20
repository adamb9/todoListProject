package com.example.todolistproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val tasks: ArrayList<Task> = ArrayList<Task>()
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

        var adapter = TaskAdapter(tasks, this)
        rv_task_list.adapter = adapter

        btn_new_task.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }


        btn_filter_tasks.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this,btn_filter_tasks)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.sort_date_desc -> {
                        var sortedList: ArrayList<Task> = ArrayList(tasks.sortedWith(compareBy({it.dateCreated})).reversed())
                        adapter.update(sortedList)
                    }
                    R.id.sort_date_asc ->{
                        var sortedList: ArrayList<Task> = ArrayList(tasks.sortedWith(compareBy({it.dateCreated})))
                        adapter.update(sortedList)
                    }
                    R.id.sort_priority_desc ->{
                        var sortedList: ArrayList<Task> = ArrayList(tasks.sortedWith(compareBy({it.priority})).reversed())
                        adapter.update(sortedList)
                    }
                    R.id.sort_priority_asc ->{
                        var sortedList: ArrayList<Task> = ArrayList(tasks.sortedWith(compareBy({it.priority})))
                        adapter.update(sortedList)
                    }
                }
                true
            })
            popupMenu.show()
        }

    }



    fun addTasks(addedTask: Task? = null){
        if (addedTask != null) {
            tasks.add(addedTask)
        }
    }


}