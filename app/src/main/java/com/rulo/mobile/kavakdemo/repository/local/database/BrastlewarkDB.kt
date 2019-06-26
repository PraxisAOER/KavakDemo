package com.rulo.mobile.kavakdemo.repository.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rulo.mobile.kavakdemo.repository.local.dao.GnomeDao
import com.rulo.mobile.kavakdemo.repository.local.dao.GnomeFriendDao
import com.rulo.mobile.kavakdemo.repository.local.dao.GnomeProfessionsDao
import com.rulo.mobile.kavakdemo.repository.local.dao.ProfessionsCatalogDao
import com.rulo.mobile.kavakdemo.repository.local.model.Gnome
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeFriend
import com.rulo.mobile.kavakdemo.repository.local.model.GnomeProfessionsRelation
import com.rulo.mobile.kavakdemo.repository.local.model.ProfessionsCatalogItem

@Database(entities = [Gnome::class, GnomeFriend::class, ProfessionsCatalogItem::class, GnomeProfessionsRelation::class], version = 1)
@TypeConverters(Converters::class)
abstract class BrastlewarkDB : RoomDatabase() {
    abstract fun gnomeDao(): GnomeDao
    abstract fun gnomeFriendDao(): GnomeFriendDao
    abstract fun professionsCatalogDao(): ProfessionsCatalogDao
    abstract fun gnomeProfessionDao(): GnomeProfessionsDao

    companion object {
        private var INSTANCE: BrastlewarkDB? = null

        fun getInstance(context: Context): BrastlewarkDB {
            if (INSTANCE == null) {
                synchronized(BrastlewarkDB::class) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            BrastlewarkDB::class.java,
                            this::class.java.simpleName
                        ).build()
                }
            }
            return INSTANCE!!

            fun destroyInstance() {
                INSTANCE = null
            }
        }
    }
}