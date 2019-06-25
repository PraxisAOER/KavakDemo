package com.rulo.mobile.kavakdemo.repository.local.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rulo.mobile.kavakdemo.repository.local.database.BrastlewarkDB
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeFriend
import com.rulo.mobile.kavakdemo.repository.remote.api.ApiClient
import com.rulo.mobile.kavakdemo.repository.remote.api.response.GnomesResponse
import com.rulo.mobile.kavakdemo.repository.remote.model.RemoteGnome
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class BrastlewarkGnomesViewModel(app: Application) : AndroidViewModel(app) {
    private val database = BrastlewarkDB.getInstance(app)

    fun getRemoteGnomes() {
        ApiClient.service.getPopulation().enqueue(object : retrofit2.Callback<GnomesResponse> {
            override fun onFailure(call: Call<GnomesResponse>, t: Throwable) {
                t.printStackTrace()
                Timber.wtf(t)
            }

            override fun onResponse(call: Call<GnomesResponse>, response: Response<GnomesResponse>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val remoteGnomesList: ArrayList<RemoteGnome> = response.body()!!.remoteGnomes
                        if (remoteGnomesList.isNotEmpty()) {

                            //Inserting gnomes
                            GlobalScope.async {
                                remoteGnomesList.map { currentGnome ->
                                    Timber.wtf(currentGnome.toString())
                                    database.gnomeDao().insertAll(currentGnome.toLocalGnome())
                                    if (currentGnome.friends.isNotEmpty()) {
                                        currentGnome.friends.map {
                                            val searchedGnome =
                                                remoteGnomesList.firstOrNull { gnome -> gnome.name == it }
                                            if (searchedGnome != null) {
                                                val gnomeFriend = GnomeFriend(
                                                    gnomeId = currentGnome.id,
                                                    friendId = searchedGnome.id
                                                )
                                                Timber.wtf(
                                                    "Looking for friends ${currentGnome.id} friend is $gnomeFriend"
                                                )
                                                database.gnomeFriendDao().insert(
                                                    gnomeFriend
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            /**
                            GlobalScope.async {
                            remoteGnomesList
                            .filter { gnome -> gnome.friends.isNotEmpty() }
                            .sortedBy { gnome -> gnome.id }.map { remoteGnome ->
                            Timber.wtf("With fiends: ${remoteGnome.id}")
                            remoteGnome.friends.map {
                            val searchedGnome =
                            remoteGnomesList.firstOrNull { gnome -> gnome.name == it }
                            if (searchedGnome != null) {
                            Timber.wtf(
                            GnomeFriend(
                            remoteGnome.id,
                            searchedGnome.id
                            ).toString()
                            )
                            database.gnomeFriendDao().insert(
                            GnomeFriend(
                            remoteGnome.id,
                            searchedGnome.id
                            )
                            )
                            }
                            }
                            }
                            }
                             **/

                        }
                    }

                }
            }
        })
    }
}