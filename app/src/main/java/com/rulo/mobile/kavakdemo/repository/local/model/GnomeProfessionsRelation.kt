package com.rulo.mobile.kavakdemo.repository.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "rel_gnome_profession", foreignKeys = [ForeignKey(
        entity = Gnome::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("gnomeId"),
        onDelete = ForeignKey.CASCADE
    ),ForeignKey(
        entity = ProfessionsCatalogItem::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("professionId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class GnomeProfessionsRelation(
    @PrimaryKey(autoGenerate = true)
    val relationId: Int? = null,
    val gnomeId: Int = 0,
    val professionId: Int = 0
) : Parcelable