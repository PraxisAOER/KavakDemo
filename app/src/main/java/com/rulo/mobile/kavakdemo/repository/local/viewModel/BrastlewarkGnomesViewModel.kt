package com.rulo.mobile.kavakdemo.repository.local.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rulo.mobile.kavakdemo.repository.local.PreferencesHelper
import com.rulo.mobile.kavakdemo.repository.local.database.BrastlewarkDB
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeFriend
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeProfessionsRelation
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
    private val gnomesCounterLive = MutableLiveData(0)
    private val preferencesHelper = PreferencesHelper(app)

    val mustGetRemoteGnomes: MutableLiveData<Boolean?> = MutableLiveData(null)


    fun getRemoteGnomes() {
        val needToUpdate = preferencesHelper.itsTimeToUpdate()
        mustGetRemoteGnomes.postValue(needToUpdate)
        if (needToUpdate) {
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
                                    val professionsMap = HashMap<Int, String>()
                                    val professionsRel = HashMap<Int, Int>()
                                    database.gnomeFriendDao().clearAll()
                                    database.professionsCatalogDao().clearAll()
                                    remoteGnomesList.map { currentGnome ->
                                        //  Timber.wtf(currentGnome.toString())
                                        database.gnomeDao().insertAll(currentGnome.toLocalGnome())

                                        //Friends
                                        if (currentGnome.friends.isNotEmpty()) {
                                            currentGnome.friends.map {
                                                val searchedGnome =
                                                    remoteGnomesList.firstOrNull { gnome -> gnome.name == it }
                                                if (searchedGnome != null) {
                                                    val gnomeFriend = GnomeFriend(
                                                        gnomeId = currentGnome.id,
                                                        friendId = searchedGnome.id
                                                    )
                                                    database.gnomeFriendDao().insert(
                                                        gnomeFriend
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                //GettingProperties
                                GlobalScope.async {
                                    val professions = remoteGnomesList
                                        .asSequence()
                                        .filter { x -> x.professions.isNotEmpty() }
                                        .map { it.professions }.flatten().distinct().sortedDescending()
                                        .toList()
                                    Timber.wtf(professions.toString())
                                }
                            }
                        }

                    }
                }
            })
        }
    }

    fun insertRelation(rel: GnomeProfessionsRelation) {
        GlobalScope.async {
            database.gnomeProfessionDao().insertAll(
                rel
            )
        }
    }

    fun getGnomes() =
        database.gnomeDao().countGnomes()
}