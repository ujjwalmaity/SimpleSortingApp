package dev.ujjwal.simplesortingapp.model

import retrofit2.Call
import retrofit2.http.GET

interface UserDetailsService {

    @GET("dev/profile/listAll")
    fun getUsers(): Call<UserList>
}