package com.rulo.mobile.kavakdemo.repository.remote.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rulo.mobile.kavakdemo.repository.remote.model.RemoteGnome


class GnomesResponse {
    @SerializedName("Brastlewark")
    @Expose
    val remoteGnomes: ArrayList<RemoteGnome> = ArrayList()

    override fun toString(): String {
        return "GnomesResponse(remoteGnomes=$remoteGnomes)"
    }


}