package com.rulo.mobile.kavakdemo.repository.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cat_professions", indices = [Index(value = ["description"], unique = true)])

data class ProfessionsCatalogItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val description: String
) : Parcelable {
    override fun toString(): String {
        return "ProfessionsCatalogItem(id=$id, description='$description')"
    }
}