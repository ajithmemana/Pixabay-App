package com.ajithmemana.pixabay.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ajithmemana
 */
@Entity(tableName = "image_data")
data class PixabayImageItem(
    @PrimaryKey val id: Long,
    val previewURL: String? = null,
    val tags: String? = null,
    val user: String? = null,
    val likes: Int? = null,
    val comments: Int? = null,
    val downloads: Int? = null,
)

