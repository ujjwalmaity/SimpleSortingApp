package dev.ujjwal.simplesortingapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ujjwal.simplesortingapp.R
import dev.ujjwal.simplesortingapp.model.UserList
import dev.ujjwal.simplesortingapp.view.adapter.UserListAdapter
import dev.ujjwal.simplesortingapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userListAdapter = UserListAdapter(this, arrayListOf())

    private lateinit var viewModel: MainViewModel
    private lateinit var userList: MutableLiveData<UserList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        userList = viewModel.getUserList()

        userList.observe(this, Observer { userList ->
            userList?.let {
                userListAdapter.updateUser(it.list!!)
            }
        })
    }
}