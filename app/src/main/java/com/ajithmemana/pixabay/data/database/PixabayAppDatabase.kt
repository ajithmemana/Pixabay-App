package com.ajithmemana.pixabay.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajithmemana.pixabay.data.database.dao.PixabayImagesDao
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem

/**
 * Created by ajithmemana
 */
@Database(entities = [PixabayImageItem::class], version = 1)
abstract class PixabayAppDatabase : RoomDatabase() {

    abstract fun pixabayImagesDao(): PixabayImagesDao

    companion object {
        private var INSTANCE: PixabayAppDatabase? = null

        fun getDatabase(context: Context): PixabayAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, PixabayAppDatabase::class.java, "pixabay_app_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}

