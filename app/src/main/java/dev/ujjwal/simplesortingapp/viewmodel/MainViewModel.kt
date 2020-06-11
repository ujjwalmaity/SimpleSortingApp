package dev.ujjwal.simplesortingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ujjwal.simplesortingapp.model.UserDetailsService
import dev.ujjwal.simplesortingapp.model.UserDetailsServiceBuilder
import dev.ujjwal.simplesortingapp.model.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private lateinit var userList: MutableLiveData<UserList>

    fun getUserList(): MutableLiveData<UserList> {
        if (!::userList.isInitialized) {
            userList = MutableLiveData()
            fetchUserList()
        }

        return userList
    }

    private fun fetchUserList() {

        val userDetailsService = UserDetailsServiceBuilder.buildService(UserDetailsService::class.java)

        val response = userDetailsService.getUsers()

        response.enqueue(object : Callback<UserList> {
            override fun onFailure(call: Call<UserList>, t: Throwable) {}

            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if (response.isSuccessful) {
                    userList.value = response.body()
                }
            }

        })
    }
}