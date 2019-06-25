package com.rulo.mobile.kavakdemo.repository.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "gnomes")
data class Gnome(
    @PrimaryKey
    val id: Int = -1,
    val age: Int = 0,
    val hairColor: String = "",
    val height: Double = 0.0,
    val name: String = "",
    val thumbnail: String = "",
    val weight: Double = 0.0
) : Parcelable {
    override fun toString(): String {
        return "Gnome(id=$id, " +
                "age=$age, " +
                "hairColor='$hairColor', " +
                "height=$height, " +
                "name='$name', " +
                "thumbnail='$thumbnail', " +
                "weight=$weight)"
    }
}