package com.rulo.mobile.kavakdemo.repository.local.dao

import androidx.room.*
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeFriend

@Dao
interface GnomeFriendDao {
    @Query("SELECT * FROM gnomefriends where gnomeId = :gnomeId ")
    fun findFriends(gnomeId: Int): List<GnomeFriend>

    @Delete
    fun delete(gnomeFriend: GnomeFriend)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(gnomeFriend: GnomeFriend)

    @Query("DELETE FROM gnomeFriends")
    fun clearAll()

}