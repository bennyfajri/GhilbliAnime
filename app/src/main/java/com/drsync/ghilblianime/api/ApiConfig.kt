package com.drsync.ghilblianime.api

import com.drsync.ghilblianime.models.ServerResponse
import retrofit2.http.GET

interface ApiConfig {

    @GET("films")
    suspend fun getMovie() : List<ServerResponse>
}