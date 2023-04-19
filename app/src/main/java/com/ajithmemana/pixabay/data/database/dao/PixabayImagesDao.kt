package com.ajithmemana.pixabay.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PixabayImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageItem: PixabayImageItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll (imageItems: List<PixabayImageItem>?)

    @Query("SELECT * FROM image_data")
    fun getAllImages(): Flow<List<PixabayImageItem>>

    @Query("DELETE FROM image_data")
    fun deleteAll()
}