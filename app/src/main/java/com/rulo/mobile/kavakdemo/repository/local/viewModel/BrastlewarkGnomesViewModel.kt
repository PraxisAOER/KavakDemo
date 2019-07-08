package com.rulo.mobile.kavakdemo.repository.local.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rulo.mobile.kavakdemo.repository.local.PreferencesHelper
import com.rulo.mobile.kavakdemo.repository.local.database.BrastlewarkDB
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeFriend
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeProfessionsRelation
import com.rulo.mobile.kavakdemo.repository.local.model.ProfessionsCatalogItem
import com.rulo.mobile.kavakdemo.repository.remote.api.ApiClient
import com.rulo.mobile.kavakdemo.repository.remote.api.response.GnomesResponse
import com.rulo.mobile.kavakdemo.repository.remote.model.RemoteGnome
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
                                    database.gnomeFriendDao().clearAll()
                                    database.gnomeProfessionDao().clearAll()
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

                                //Getting Professions
                                GlobalScope.async {

                                    //Filter gnomes who has professions
                                    val gnomesWithProfessions = remoteGnomesList
                                        .asSequence()
                                        .filter { x -> x.professions.isNotEmpty() }

                                    //Get all professions
                                    val professionsMap = gnomesWithProfessions.map { it.professions }
                                        .flatten()
                                        .distinct()
                                        .sorted().toHashSet()


                                    professionsMap.forEach { profession ->
                                        val thisProffesionList = gnomesWithProfessions
                                            .filter { x -> x.professions.contains(profession) }
                                            .map { it.id }
                                            .toList()

                                        with(
                                            database.professionsCatalogDao().getProfessionIdByDescription(profession)
                                        ) {
                                            if (this.isNotEmpty()) {
                                                createRelations(this.first().toInt(), thisProffesionList)
                                            } else {
                                                //Inserting profession
                                                Single.fromCallable {
                                                    database.professionsCatalogDao()
                                                        .insert(ProfessionsCatalogItem(description = profession))
                                                }
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribeOn(Schedulers.io())
                                                    .subscribe { insertedProfession ->
                                                        Timber.wtf("Profession inserted with id $insertedProfession")
                                                        createRelations(insertedProfession.toInt(), thisProffesionList)
                                                    }
                                            }

                                        }

                                    }


                                }
                            }
                        }

                    }
                }
            })
        }
    }

    fun createRelations(professionId: Int, gnomesList: List<Int>) {
        gnomesList.map {
            database.gnomeProfessionDao().insertAll(GnomeProfessionsRelation(gnomeId = it, professionId = professionId))
        }
    }

    fun getGnomes() =
        database.gnomeDao().countGnomes()
}