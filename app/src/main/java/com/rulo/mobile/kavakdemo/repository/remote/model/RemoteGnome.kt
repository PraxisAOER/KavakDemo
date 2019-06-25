package com.rulo.mobile.kavakdemo.repository.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rulo.mobile.kavakdemo.repository.local.model.Gnome
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RemoteGnome(
    val id: Int = -1,
    val age: Int = 0,
    val friends: ArrayList<String> = ArrayList(),
    @SerializedName("hair_color")
    val hairColor: String = "",
    val height: Double = 0.0,
    val name: String = "",
    val professions: ArrayList<String> = ArrayList(),
    val thumbnail: String = "",
    val weight: Double = 0.0
) : Parcelable {
    override fun toString(): String {
        return "RemoteGnome(id=$id, age=$age, friends=$friends, hairColor='$hairColor', height=$height, name='$name', professions=$professions, thumbnail='$thumbnail', weight=$weight)"
    }

    fun toLocalGnome(): Gnome {
        return Gnome(
            id,
            age,
            hairColor,
            height,
            name,
            thumbnail,
            weight
        )
    }
}