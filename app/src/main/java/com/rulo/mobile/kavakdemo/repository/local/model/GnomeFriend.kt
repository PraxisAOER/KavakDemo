package com.rulo.mobile.kavakdemo.repository.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "gnomeFriends", foreignKeys = [ForeignKey(
        entity = Gnome::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("gnomeId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class GnomeFriend(
    @PrimaryKey(autoGenerate = true)
    val relationId: Int? = null,
    val gnomeId: Int = 0,
    val friendId: Int = 0
) : Parcelable