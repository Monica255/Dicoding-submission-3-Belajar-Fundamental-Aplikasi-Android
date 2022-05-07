package com.example.aplikasigit3.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigit3.BuildConfig
import com.example.aplikasigit3.SearchResponse
import com.example.aplikasigit3.User
import com.example.aplikasigit3.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response
import java.util.*

class MainViewModel : ViewModel() {
    var filtered = mutableListOf<User>()
    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user

    private val _searchUser = MutableLiveData<List<User>>()
    val searchUser: LiveData<List<User>> = _searchUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUser()
    }

    private fun search() {
        filtered.clear()
        val filteredtext = newText.lowercase(Locale.getDefault())
        if (filteredtext.isNotEmpty()) {
            fetchUserSearch(filteredtext)
        }
    }

    private fun fetchUserSearch(searchUser: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getDetailUserSearch(searchUser, TOKEN)
        api.enqueue(object : retrofit2.Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _searchUser.value = responseBody.items
                    }

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    var newText: String = ""
        set(value) {
            field = value
            search()
        }


    private fun getUser() {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getAllUser(token = TOKEN)
        api.enqueue(object : retrofit2.Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _user.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val TOKEN = "token ${BuildConfig.APITOKEN}"
    }
}