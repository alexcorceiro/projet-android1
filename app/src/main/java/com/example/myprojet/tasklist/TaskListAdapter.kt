package com.example.myprojet.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojet.R

class TaskListAdapter :  RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<Task> = emptyList()
    var onClickDelete: (Task) -> Unit = {}
    var onClickEdit: (Task) -> Unit = {}

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            val textarguments = itemView.findViewById<TextView>(R.id.task_title)
            textarguments.text = task.title
            val textinformation = itemView.findViewById<TextView>(R.id.task_description)
            textinformation.text = task.description
            val buttondeleted = itemView.findViewById<ImageButton>(R.id.image_Button)
            buttondeleted.setOnClickListener{
                onClickDelete(task)
            }

            val editText = itemView.findViewById<ImageButton>(R.id.edit)
                editText.setOnClickListener{
                  onClickEdit(task)
                }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.count()
    }

}