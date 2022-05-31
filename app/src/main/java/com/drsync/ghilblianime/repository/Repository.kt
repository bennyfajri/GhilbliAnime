package com.drsync.ghilblianime.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.drsync.ghilblianime.api.RetrofitInstance
import com.drsync.ghilblianime.models.ServerResponse
import com.drsync.ghilblianime.util.Resource

class Repository {

    fun getFilm(): LiveData<Resource<List<ServerResponse>>> = liveData {
        val data = MutableLiveData<Resource<List<ServerResponse>>>()
        emit(Resource.Loading)
        try {
            val response = RetrofitInstance.api.getMovie()
            data.value = Resource.Success(response)
        } catch (e: Exception) {
            data.value = Resource.Error(e.message.toString())
        }
        emitSource(data)
    }
}