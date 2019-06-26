package com.rulo.mobile.kavakdemo.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rulo.mobile.kavakdemo.repository.local.model.ProfessionsCatalogItem

@Dao
interface ProfessionsCatalogDao {
    @get:Query("SELECT * FROM cat_professions")
    val all: LiveData<List<ProfessionsCatalogItem>>

    @Query("DELETE FROM cat_professions")
    fun clearAll()

    @Query("SELECT * from cat_professions WHERE description= :description")
    fun getProfessionByDescription(description: String): List<ProfessionsCatalogItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg profession: ProfessionsCatalogItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profession: ProfessionsCatalogItem): Long

    @Delete
    fun delete(item: ProfessionsCatalogItem)
}