package com.example.myprojet.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myprojet.R
import com.example.myprojet.network.Api
import com.example.myprojet.network.TaskListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import form.FormActivity
import kotlinx.coroutines.launch


class TaskListFragment: Fragment() {

    private val viewModel: TaskListViewModel by viewModels()

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task? ?: return@registerForActivityResult
         /*Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")*/
        //taskList = taskList + task
        viewModel.create(task)
    }
    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task? ?: return@registerForActivityResult
        /*Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")*/
        //taskList = taskList.map { if (it.id == task.id) task else it }
        viewModel.update(task)
    }
    private val adapter = TaskListAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_list)
        recyclerView.adapter = adapter
        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                adapter.currentList = newList
                adapter.notifyDataSetChanged()            }
        }

        val addButton = view.findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {


            val intent = Intent(context, FormActivity::class.java)
            createTask.launch(intent)

        }

        adapter.onClickDelete = { task ->
           // taskList = taskList - task
            viewModel.delete(task)
        }

        adapter.onClickEdit = { task ->
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }



    }


    override fun onResume(){
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            val userInfoTextView= view?.findViewById<TextView>(R.id.user_Info_TextView)
            userInfoTextView?.text = "${userInfo.firstName} ${userInfo.lastName}"
            val avatarImageView= view?.findViewById<ImageButton>(R.id.im_avatar)
            avatarImageView?.load("https://goo.gl/gEgYUd"){
            crossfade(true)
                transformations(CircleCropTransformation())
                    error(R.drawable.ic_launcher_background)
            }
            viewModel.refresh() // on demande de rafraîchir les données sans attendre le retour directement

        }
    }



}
