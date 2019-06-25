package com.rulo.mobile.kavakdemo.repository.remote.model


import com.google.gson.annotations.SerializedName

data class Brastlewark(
    @SerializedName("Brastlewark")
    val brastlewark: List<RemoteGnome>
)