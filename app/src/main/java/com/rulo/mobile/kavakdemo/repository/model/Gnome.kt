package com.rulo.mobile.kavakdemo.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gnome(
    val id: Int = -1,
    val age: Int = 0,
    val friends: ArrayList<String> = ArrayList(),
    val hairColor: String = "",
    val height: Double = 0.0,
    val name: String = "",
    val professions: ArrayList<String> = ArrayList(),
    val thumbnail: String = "",
    val weight: Double = 0.0
) : Parcelable {
    override fun toString(): String {
        return "Gnome(id=$id, age=$age, friends=$friends, hairColor='$hairColor', height=$height, name='$name', professions=$professions, thumbnail='$thumbnail', weight=$weight)"
    }
}