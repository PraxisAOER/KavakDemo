package com.rulo.mobile.kavakdemo.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rulo.mobile.kavakdemo.repository.local.model.Gnome

@Dao
interface GnomeDao {
    @get:Query("SELECT * FROM gnomes")
    val all: LiveData<List<Gnome>>

    @Query("SELECT COUNT(*) from gnomes")
    fun countGnomes(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg gnomes: Gnome)


    @Delete
    fun delete(gnome: Gnome)
}