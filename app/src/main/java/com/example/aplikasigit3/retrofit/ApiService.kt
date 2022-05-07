package com.example.aplikasigit3.retrofit

import com.example.aplikasigit3.DetailUser
import com.example.aplikasigit3.SearchResponse
import com.example.aplikasigit3.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //memanggil list user
    @GET("users")
    fun getAllUser(
        @Header("Authorization") token: String
    ): Call<List<User>>

    //memanggil list follower
    @GET("users/{login}/followers")
    fun getAllUserFollower(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<List<User>>

    //memanggil list following
    @GET("users/{login}/following")
    fun getAllUserFollowing(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<List<User>>

    //memanggil detail user
    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<DetailUser>

    //memanggil list user berdasarkan pencarian
    @GET("search/users")
    fun getDetailUserSearch(
        @Query("q") login: String,
        @Header("Authorization") token: String
    ): Call<SearchResponse>
}