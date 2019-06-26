package com.rulo.mobile.kavakdemo.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeProfessionsRelation

@Dao
interface GnomeProfessionsDao {
    @get:Query("SELECT * FROM rel_gnome_profession")
    val all: LiveData<List<GnomeProfessionsRelation>>

    @Query("DELETE from rel_gnome_profession")
    fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg professionRelation: GnomeProfessionsRelation)

    @Delete
    fun delete(item: GnomeProfessionsRelation)
}