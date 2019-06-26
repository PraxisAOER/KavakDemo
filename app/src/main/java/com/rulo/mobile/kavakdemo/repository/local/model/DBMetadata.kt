package com.rulo.mobile.kavakdemo.repository.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "gnomes_metadata")
data class DBMetadata(
    @PrimaryKey
    val id: Int? = null,
    val propertyName: String = "",
    val updateTime: Long = 0
) : Parcelable {
    enum class GNOMES_PROPERTIES {
        GNOME_LAST_UPDATE,
        FRIEND_RELATIONS_LAST_UPDATE,
        PROFESSIONS_UPDATE
    }
}