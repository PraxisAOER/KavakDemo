package com.rulo.mobile.kavakdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rulo.mobile.kavakdemo.repository.model.Gnome
import com.rulo.mobile.kavakdemo.repository.remote.ApiClient
import com.rulo.mobile.kavakdemo.repository.remote.gnomes.GnomesResponse
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiClient.service.getPopulation().enqueue(object : retrofit2.Callback<GnomesResponse> {
            override fun onFailure(call: Call<GnomesResponse>, t: Throwable) {
                t.printStackTrace()
                Timber.wtf(t)
            }

            override fun onResponse(call: Call<GnomesResponse>, response: Response<GnomesResponse>) {
                if (response.isSuccessful) {
                    Timber.wtf(response.body().toString())
                    if (response.body() != null) {
                        val heroesList: ArrayList<Gnome> = response.body()!!.gnomes
                        heroesList.map {
                            Timber.wtf(it.toString())
                        }
                    }

                }
            }
        })

    }
}
