package com.rulo.mobile.kavakdemo.repository.remote.gnomes

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rulo.mobile.kavakdemo.repository.model.Gnome


class GnomesResponse {
    @SerializedName("Brastlewark")
    @Expose
    val gnomes: ArrayList<Gnome> = ArrayList()

    override fun toString(): String {
        return "GnomesResponse(gnomes=$gnomes)"
    }


}