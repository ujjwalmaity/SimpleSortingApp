package dev.ujjwal.simplesortingapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ujjwal.simplesortingapp.R
import dev.ujjwal.simplesortingapp.model.UserDetail
import dev.ujjwal.simplesortingapp.model.UserList
import dev.ujjwal.simplesortingapp.view.adapter.UserListAdapter
import dev.ujjwal.simplesortingapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val userListAdapter = UserListAdapter(this, arrayListOf())

    private lateinit var viewModel: MainViewModel
    private lateinit var userList: MutableLiveData<UserList>

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

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
                val allUsers = arrayListOf<UserDetail>()
                val activeUsers = arrayListOf<UserDetail>()
                val leftUsers = arrayListOf<UserDetail>()
                val onboardedUsers = arrayListOf<UserDetail>()

                /** Update dayDiff in users*/
                /** Sort out users by active, left & onboarded */
                for (user in it.list!!) {
                    user.dayDiff = getDayDiff(user.date!!.replace("/", ""))

                    when (user.status) {
                        "active" -> activeUsers.add(user)
                        "left" -> leftUsers.add(user)
                        "onboarded" -> onboardedUsers.add(user)
                    }
                }

                /** Sort out activeUsers, leftUsers, onboardedUsers by DayDiff */
                var sortedList = activeUsers.sortedByDescending { it.dayDiff }
                activeUsers.clear()
                activeUsers.addAll(sortedList)
                sortedList = leftUsers.sortedByDescending { it.dayDiff }
                leftUsers.clear()
                leftUsers.addAll(sortedList)
                sortedList = onboardedUsers.sortedByDescending { it.dayDiff }
                onboardedUsers.clear()
                onboardedUsers.addAll(sortedList)

                /** activeUsers + leftUsers + onboardedUsers = allUsers */
                allUsers.addAll(activeUsers)
                allUsers.addAll(leftUsers)
                allUsers.addAll(onboardedUsers)

                /** Update recycler view */
                userListAdapter.updateUser(allUsers)
            }
        })
    }

    private fun getDayDiff(date: String): Int {
        val sdf = SimpleDateFormat("ddMMyyyy")

        val previousDate = sdf.parse(date)
        //val previousDate = sdf.parse("01102019")
        //val previousTimestamp = sdf.format(previousDate)
        //Log.i(TAG, previousTimestamp.toString())   // 01102019

        val presentLong = Date(System.currentTimeMillis()).time
        val presentTimestamp = sdf.format(presentLong)
        //Log.i(TAG, presentTimestamp.toString())    // 11062020
        val presentDate = sdf.parse(presentTimestamp)

        //Log.i(TAG, previousDate.toString())
        //Log.i(TAG, presentDate.toString())

        val diff: Long = presentDate.time - previousDate.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        Log.i(TAG, days.toString())
        return days.toInt()
    }
}