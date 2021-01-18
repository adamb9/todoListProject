package com.example.todolistproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_task.view.*

class TaskAdapter(val tasks: ArrayList<Task>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

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
        holder?.tvTaskName?.text = tasks.get(position).name
        when(tasks.get(position).priority) {
            0 -> holder?.ivTaskColor?.setImageResource(R.drawable.redcircle_foreground)
            1 -> holder?.ivTaskColor?.setImageResource(R.drawable.orangecircle_foreground)
            2 -> holder?.ivTaskColor?.setImageResource(R.drawable.greencircle_foreground)
        }
        holder?.btnRemoveTask.setOnClickListener{
            tasks.removeAt(position)
            notifyDataSetChanged()
        }
        holder?.taskView.setOnClickListener{
            Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
        }
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the fields that we will add each task to
    val tvTaskName = view.tv_taskname
    val ivTaskColor = view.iv_taskcolor
    val btnRemoveTask = view.btn_removetask
    val taskView = view
}