package com.example.todolistproject


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_task.view.*

class TaskAdapter(val tasks: ArrayList<Task>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private var db: AppDatabase? = null
    private var taskDao: TaskDao? = null

    // Gets the number of tasks in the list
    override fun getItemCount(): Int {
        return tasks.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_task, parent, false))
    }

    // Binds each task in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db = AppDatabase.getAppDataBase(context = context)
        taskDao = db?.taskDao()

        holder?.tvTaskName?.text = tasks.get(position).name
        when(tasks.get(position).priority) {
            2 -> holder?.ivTaskColor?.setImageResource(R.drawable.redcircle_foreground)
            1 -> holder?.ivTaskColor?.setImageResource(R.drawable.orangecircle_foreground)
            0 -> holder?.ivTaskColor?.setImageResource(R.drawable.greencircle_foreground)
        }
        holder?.btnCompleteTask.setOnClickListener{
            var selectedTask = tasks[position]
            selectedTask.isCompleted = true
            taskDao?.updateTask(selectedTask)
            tasks.removeAt(position)
            notifyDataSetChanged()
        }
        holder?.taskView.setOnClickListener{
            val intent = Intent(context, FullTaskActivity::class.java).apply {
            putExtra("task", tasks[position])
            }

            context.startActivity(intent)
        }
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the fields that we will add each task to
    val tvTaskName = view.tv_taskname
    val ivTaskColor = view.iv_taskcolor
    val btnCompleteTask = view.btn_completetask
    val taskView = view
}