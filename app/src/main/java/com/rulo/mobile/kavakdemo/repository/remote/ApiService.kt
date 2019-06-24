package com.rulo.mobile.kavakdemo.repository.remote

import com.rulo.mobile.kavakdemo.repository.remote.gnomes.GnomesResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("rrafols/mobile_test/master/data.json")
    fun getPopulation(): Call<GnomesResponse>
}