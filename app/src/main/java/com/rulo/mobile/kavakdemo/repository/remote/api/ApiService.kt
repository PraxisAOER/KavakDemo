package com.rulo.mobile.kavakdemo.repository.remote.api

import com.rulo.mobile.kavakdemo.repository.remote.api.response.GnomesResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("rrafols/mobile_test/master/data.json")
    fun getPopulation(): Call<GnomesResponse>
}